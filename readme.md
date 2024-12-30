# Chatroom Application

This repository hosts a chatroom application consisting of two interconnected components: the **Server** and the **Client**. Together, they enable real-time communication between multiple users.

## Overview
- **Server**: Manages client connections, broadcasts messages, and handles client lifecycle events.
- **Client**: Connects to the server, facilitates user input, and displays received messages.

## Features
- Multi-client support with real-time messaging.
- User-friendly commands for chat management.
- Graceful handling of client disconnections.

## Configuration
Both server and client rely on a `config.properties` file for customizable settings.

**Example `config.properties`**:
```properties
serverAddress=127.0.0.1
serverPort=8081
```

## How to Run
### Server
1. Navigate to the server project directory.
2. Compile and run the server:
   ```bash
   javac Main.java
   java Main
   ```

### Client
1. Navigate to the client project directory.
2. Compile and run the client:
   ```bash
   javac Main.java
   java Main
   ```
3. Follow the prompts to set your username and start chatting.

## Example Interaction
### Server Log:
```
Server started ...
Client connected: 127.0.0.1
Bob joined the chatroom!
Alice joined the chatroom!
```

### Client Console:
```
Welcome to chatroom!
Enter username: Alice
Bob: Hello, everyone!
Alice: Hi, Bob!
```

## Future Improvements
- **Encryption**: Secure communication with end-to-end encryption.
- **GUI**: Enhance usability with a graphical interface.
- **Private Messaging**: Allow direct messages between users.