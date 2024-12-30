
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

public class ClientHandler implements Runnable {
    Map<String, String> message = new HashMap<>();

    public static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BACK_RED = "\u001B[40m";
    private static final String ANSI_BLUE = "\u001B[34m";

    Socket socket;
    DataOutputStream output;
    DataInput input;
    String request;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            output = new DataOutputStream(socket.getOutputStream());
            input = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Main.addClient(output);
    }

    @Override
    public void run() {
        try {
            while (true) {
                try {
                    request = input.readUTF();
                    parseJSONMessage(request);
                    System.out.println("Server received: " + request);

                    ClientEntity.getInstance().setUsername(message.get("username"));
                    ClientEntity.getInstance().setContent(message.get("content"));

                    if (ClientEntity.getInstance().getContent().equals("created username!")) {
                        Main.broadcast(sendJoinedMessage(), output);
                    } else if (ClientEntity.getInstance().getContent().equals("#exit")) {
                        Main.broadcast(sendLeftMessage(), output);
                        System.out.println(ANSI_BACK_RED + message.get("username") + " left the chatroom!" + ANSI_RESET);
                        break;
                    } else {
                        Main.broadcast(request, output);
                    }
                } catch (SocketException e) {
                    // Handle abrupt disconnection
                    System.out.println(ANSI_BACK_RED + "Client disconnected abruptly!" + ANSI_RESET);
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error in communication: " + e.getMessage());
        } finally {
            try {
                Main.removeClient(output);
                if (!socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException ex) {
                System.err.println("Error closing socket: " + ex.getMessage());
            }
        }
    }


    public String sendJoinedMessage() {
        message.put("username", ANSI_BLUE + ClientEntity.getInstance().getUsername());
        message.put("content", /*ANSI_BLUE + message.get("username") + */" Joined chatroom!" + ANSI_RESET);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public String sendLeftMessage() {
        message.put("username", ANSI_BACK_RED + ClientEntity.getInstance().getUsername());
        message.put("content", /*ANSI_BACK_RED + message.get("username") + */" left the chatroom!" + ANSI_RESET);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void parseJSONMessage(String json) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            ClientEntity client = objectMapper.readValue(json, ClientEntity.class);
            message.put("username", client.getUsername());
            message.put("content", client.getContent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}