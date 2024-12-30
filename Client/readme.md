# Client Project for AP Workshop

This repository contains a simple chat client application implemented in Java. The client connects to a server and allows users to send and receive messages in a chatroom.

---

## Features
- Connect to a server using a configurable IP address and port.
- Set a username for the chat session.
- Send and receive messages in real-time.
- Gracefully handle disconnections and errors.

---

## Requirements
- Java Development Kit (JDK) 8 or higher
- A running chat server (see the server project in the same repository).

---

## Configuration
The client uses a `config.properties` file to configure the server's address and port. Update the file with your server's details:

```properties
serverAddress=127.0.0.1
serverPort=8081
```

---

## How It Works
1. The client connects to the server specified in `config.properties`.
2. The user is prompted to enter a username.
3. The user can send messages that are broadcast to all connected clients.
4. The user can type `#exit` to leave the chatroom.

---

## Code Overview

### Main Class
The `Main` class initializes the `ChatClient` and starts the application.

### ChatClient Class
The `ChatClient` class handles the following responsibilities:
- Establishing a connection with the server.
- Managing input/output streams for communication.
- Reading messages from the server in a separate thread.
- Sending messages to the server based on user input.

### ClientEntity Class
The `ClientEntity` class is a singleton that represents the client's username and message content.

---

## Usage
1. Clone the repository:
   ```bash
   git clone https://github.com/Mil4dRezaee/AP-Workshop10
   ```

2. Navigate to the project directory:
   ```bash
   cd Client
   ```

3. Update the `config.properties` file with your server's address and port.

4. Compile and run the client:
   ```bash
   javac Main.java
   java Main
   ```

5. Enter your username and start chatting!

---

## Example
```
Welcome to chatroom!
Enter username: Alice
REC: Bob: Hello, everyone!
Alice: Hi, Bob!
REC: Bob: How are you?
Alice: I'm good, thanks! #exit
```

## Error Handling
- The client will handle abrupt disconnections and attempt to close resources gracefully.
- If the server is not running or the connection fails, an error message will be displayed.

---

## Future Enhancements
- Add GUI support for a better user experience.
- Implement encryption for secure communication.
- Allow customization of message formats.