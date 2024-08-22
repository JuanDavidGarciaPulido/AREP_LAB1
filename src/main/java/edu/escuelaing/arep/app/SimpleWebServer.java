package edu.escuelaing.arep.app;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.concurrent.*;

public class SimpleWebServer {
    private static final int PORT = 8080;
    public static final String WEB_ROOT = "src/main/webroot";
    private ExecutorService threadPool;
    private ServerSocket serverSocket;

    public SimpleWebServer() throws IOException {
        this.threadPool = Executors.newFixedThreadPool(10);
        this.serverSocket = new ServerSocket(PORT);
    }

    public void start() throws IOException {
        while (!serverSocket.isClosed()) {
            Socket clientSocket = serverSocket.accept();
            threadPool.submit(new ClientHandler(clientSocket));
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
        threadPool.shutdown();
    }

    public boolean isRunning() {
        return !serverSocket.isClosed();
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    public ExecutorService getThreadPool() {
        return threadPool;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public static void main(String[] args) throws IOException {
        SimpleWebServer server = new SimpleWebServer();
        server.start();
    }
}


class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedOutputStream dataOut = new BufferedOutputStream(clientSocket.getOutputStream())) {

            String requestLine = in.readLine();
            if (requestLine == null) return;
            String[] tokens = requestLine.split(" ");
            String method = tokens[0];
            String fileRequested = tokens[1];

            if (method.equals("GET")) {
                handleGetRequest(fileRequested, out, dataOut);
            } else if (method.equals("POST")) {
                handlePostRequest(fileRequested, in, out);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handlePostRequest(String fileRequested, BufferedReader in, PrintWriter out) throws IOException {
        StringBuilder payload = new StringBuilder();
        String line;

        while (!(line = in.readLine()).isEmpty()) {
        }
        while (in.ready() && (line = in.readLine()) != null) {
            payload.append(line);
        }

        String body = payload.toString();

        String fileName = "PosiblesFrutas.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(SimpleWebServer.WEB_ROOT, fileName), true))) {
            writer.write(body);
            writer.newLine();
        }

        out.println("HTTP/1.1 200 OK");
        out.println("Content-Type: text/plain");
        out.println();
        out.println("Se han añadido las nuevas frutas");
        out.flush();
    }

    public void handleGetRequest(String fileRequested, PrintWriter out, BufferedOutputStream dataOut) throws IOException {
        File file = new File(SimpleWebServer.WEB_ROOT, fileRequested);
        int fileLength = (int) file.length();
        String content = getContentType(fileRequested);
        String fileReadable = file.toString();
        if (file.exists()) {


            if (fileReadable.endsWith(".png") || fileReadable.endsWith(".jpg") || fileReadable.endsWith(".jpeg")) {
                byte[] imageData = getImageContent(fileReadable);
                String base64Image = Base64.getEncoder().encodeToString(imageData);

                String htmlResponse = "<!DOCTYPE html>\r\n"
                        + "<html>\r\n"
                        + "    <head>\r\n"
                        + "        <title>Imagen</title>\r\n"
                        + "    </head>\r\n"
                        + "    <body>\r\n"
                        + "         <center><img src=\"data:image/jpeg;base64," + base64Image + "\" alt=\"image\"></center>\r\n"
                        + "    </body>\r\n"
                        + "</html>";

                // Send HTML response with embedded base64 image
                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type: text/html");
                out.println();
                out.println(htmlResponse);
            }else {

                byte[] fileData = readFileData(file, fileLength);
                out.println("HTTP/1.1 200 OK");
                out.println("Content-type: " + content);
                out.println("Content-length: " + fileLength);
                out.println();
                out.flush();
                dataOut.write(fileData, 0, fileLength);
                dataOut.flush();
            }
        } else {
            out.println("HTTP/1.1 404 Not Found");
            out.println("Content-type: text/html");
            System.out.printf(fileReadable);
            out.println();
            out.flush();
            out.println("<html><body><h1>File is Not Found</h1></body></html>");
            out.flush();
        }
    }
    private static byte[] getImageContent(String file) throws IOException {
        Path filePath = Paths.get(file);
        return Files.readAllBytes(filePath);
    }

    private String getContentType(String fileRequested) {
        if (fileRequested.endsWith(".html")) return "text/html";
        else if (fileRequested.endsWith(".css")) return "text/css";
        else if (fileRequested.endsWith(".js")) return "application/javascript";
        else if (fileRequested.endsWith(".png")) return "image/png";
        else if (fileRequested.endsWith(".jpg")) return "image/jpeg";
        return "text/plain";
    }

    private byte[] readFileData(File file, int fileLength) throws IOException {
        FileInputStream fileIn = null;
        byte[] fileData = new byte[fileLength];
        try {
            fileIn = new FileInputStream(file);
            fileIn.read(fileData);
        } finally {
            if (fileIn != null) fileIn.close();
        }
        return fileData;
    }
}
