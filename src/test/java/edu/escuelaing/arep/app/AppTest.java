package edu.escuelaing.arep.app;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AppTest {
    private SimpleWebServer server;

    @BeforeEach
    public void setUp() throws IOException {
        server = new SimpleWebServer();
    }

    @AfterEach
    public void tearDown() throws IOException {
        server.stop();
    }

    @Test
    public void testServerStartsOnCorrectPort() {
        assertEquals(8080, server.getPort());
    }

    @Test
    public void testServerIsRunning() throws IOException {
        assertTrue(server.isRunning());
        server.stop();
        assertFalse(server.isRunning());
    }

    @Test
    public void testThreadPoolIsInitialized() {
        ExecutorService threadPool = server.getThreadPool();
        assertNotNull(threadPool);
        assertFalse(threadPool.isShutdown());
    }

    @Test
    public void testServerSocketIsClosedAfterStop() throws IOException {
        server.stop();
        ServerSocket socket = server.getServerSocket();
        assertTrue(socket.isClosed());
    }


}

