# AREP LAB1: Multi-Request Web Server in Java: Handling Static Files and Asynchronous REST Communication

Web server that supports multiple concurrent requests. The server reads files from the local disk and returns all requested files, including HTML pages, JavaScript files, CSS, and images. Also an application web is included with JavaScript, CSS, and images to test the server. The application has asynchronous communication with REST services on the backend, without using web frameworks like Spark or Spring, relying solely on Java and network handling libraries. 

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

Before install and run the project you will need:

1. **Java** (version 21)


2. **Maven**
    - Download Maven from [here](http://maven.apache.org/download.html)
    - Follow the installation instructions [here](http://maven.apache.org/download.html#Installation)

3. **Git**
    - Install Git by following the instructions [here](http://git-scm.com/book/en/v2/Getting-Started-Installing-Git)

### Installing

1. Clone the repository and navigate into the project directory:
    ```
    git clone https://github.com/JuanDavidGarciaPulido/AREP_LAB1.git

    cd AREP_LAB1
    ```

2. Compile and run the server:
    ```
   javac -d bin src/main/java/edu/escuelaing/arep/app/SimpleWebServer.java
   java -cp bin edu.escuelaing.arep.app.SimpleWebServer
    ```

3. **Access the Web Server**:
   - Open a web browser and navigate to `http://localhost:8080/index.html`.
   - The server will serve files from the `src/main/webroot` directory (manzanas.png fresas.png limones.png).
   - The names of the fruits you add will be saved in a TXT file.

![image](https://github.com/user-attachments/assets/4229c4fe-32e5-43f8-ad0b-df3a61624c02)


![image](https://github.com/user-attachments/assets/50d224bf-589e-4f41-8ff8-797dc4c616a7)


## Configuration
- **Web Root Directory**: The server serves files from the `src/main/webroot/` directory by default.
- **Port**: The server listens on port `8080` by default.
- **Thread Pool**: The server uses a thread pool with a fixed size of 10 threads to handle concurrent requests.

## Project Structure
```
SimpleWebServer/
│
├───src/
│   ├───main/
│   │   ├───java/
│   │   │   └───edu/
│   │   │       └───escuelaing/
│   │   │           └───arep/
│   │   │               │
│   │   │               └───app/
│   │   │                       └───SimpleWebServer.java
│   │   └───webroot/
│   │       ├───index.html
│   │       ├───styles.css
│   │       ├───app.js
│   │       ├───manzanas.png
│   │       └───... (other resources)
│   └───test/
│       └───java/
│           └───edu/
│               └───escuelaing/
│                   └───arep/
│                       └───app/
```
## Running the tests
![image](https://github.com/user-attachments/assets/8ceecffd-e590-49fb-bfb2-e469005ed340)

### Test Descriptions

#### 1. `testServerStartsOnCorrectPort()`
Verifies that the server is configured to listen on the correct port (8080).

#### 2. `testServerIsRunning()`
Checks if the server is running and can correctly transition to a stopped state.

#### 3. `testThreadPoolIsInitialized()`
Ensures that the server’s thread pool is properly initialized and is not shut down.

#### 4. `testServerSocketIsClosedAfterStop()`
Confirms that the server's ServerSocket is closed after the server is stopped.

## Built with
* [Maven](https://maven.apache.org/) - Dependency management
* [java](https://www.java.com/es/) - Programming language

## Author
This project was developed by Juan David García Pulido.

## Date

Wednesday, August 21 - 2024

## License

This project is licensed under the GNU license; See the [LICENSE.txt](LICENSE.txt) file for details.


