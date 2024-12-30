# Server Project for AP Workshop

This repository contains the implementation of a server that facilitates real-time communication between clients using sockets. It is part of the AP Workshop 10 assignment.

---

## Features
- **Multi-client support**: Handles multiple clients concurrently using threads.
- **Message broadcasting**: Broadcasts messages sent by a client to all other connected clients.
- **User management**: Tracks and manages user actions like joining, messaging, and leaving.
- **JSON-based communication**: Uses JSON for structured messaging.
- **Graceful error handling**: Handles abrupt client disconnections and communication errors.

---

## Requirements
- Java Development Kit (JDK) 8 or higher

---

## Project Structure
- **Main Class**:
    - Starts the server and listens for client connections.
    - Manages client sockets and handles broadcasting of messages.
- **ClientHandler Class**:
    - Handles communication with an individual client.
    - Parses incoming messages and sends responses using JSON.
- **ClientEntity Class**:
    - Represents a client entity with a username and message content.

---

## Dependencies
This project uses the following libraries:
- **Jackson Databind**: For JSON parsing and serialization.
- **Java Standard Libraries**: For networking, threading, and I/O operations.

---

## Configuration
The server's configuration is stored in the `config.properties` file located in the `src` folder. It contains the following property:
- `serverPort`: The port on which the server listens for connections.

### Example `config.properties`
```properties
serverPort=12345
```

---

## How to Run
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Mil4dRezaee/AP-Workshop10
   ```
2. **Navigate to the Server Folder**:
   ```bash
   cd AP-Workshop10/Server
   ```
3. **Build the Project**:
   Use IntelliJ IDEA or any Java compiler to build the project.
4. **Run the Server**:
    - Make sure the `config.properties` file has the correct `serverPort`.
    - Start the server using IntelliJ IDEA or the terminal:
      ```bash
      java -cp out/production/Server Main
      ```
5. **Connect Clients**:
    - Clients can connect to the server using the specified port.

---

## How It Works
1. The server listens for incoming client connections on the specified port.
2. Each connected client is handled by a separate `ClientHandler` thread.
3. Clients can:
    - Send messages to the server, which are broadcast to other clients.
    - Exit the chatroom by sending a special `#exit` command.
4. Messages are transmitted in JSON format for structured communication.

---

## Example Communication
- **Client joins**: A message indicating the username has joined is broadcast.
- **Client sends a message**: The message is relayed to all other clients.
- **Client exits**: A message indicating the user has left is broadcast.

---

## Known Issues
- **Abrupt Disconnections**: If a client disconnects without notifying the server, a warning is logged.
- **No Authentication**: The server does not currently authenticate users.

---

## Future Enhancements
- Add user authentication.
- Improve error handling.
- Implement a graphical user interface for better usability.