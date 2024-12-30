import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class ChatClient {

    Map<String, String> receivedMessage = new HashMap<>();
    Map<String, String> sendMessage = new HashMap<>();

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BACK_RED = "\u001B[40m";

    private final String serverAddress;
    private final int serverPort;
    private Socket clientSocket;
    private DataOutputStream output;
    private DataInputStream input;
    private final Scanner scanner;

    public ChatClient(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        try {

            clientSocket = new Socket(serverAddress, serverPort);
            output = new DataOutputStream(clientSocket.getOutputStream());
            input = new DataInputStream(clientSocket.getInputStream());

            //get username
            System.out.println(ANSI_BACK_RED + "Welcome to chatroom!" + ANSI_RESET);
            System.out.print("Enter username: ");
            ClientEntity.getInstance().setUsername(scanner.nextLine());
            ClientEntity.getInstance().setContent("created username!");
            output.writeUTF(sendMessage());

            //read inputs from server
            Thread readThread = new Thread(this::readMessages);
            readThread.start();

            //send message to server
            writeMessages();

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    private void readMessages() {
        try {
            while (true) {
                String in = input.readUTF();
                parseJSONMessage(in);
                System.out.println("REC: " + receivedMessage.get("username") + ": " + receivedMessage.get("content"));
            }
        } catch (IOException e) {
            System.err.println("Error reading message: " + e.getMessage());
        }
    }

    private void writeMessages() {
        try {
            while (true) {
                ClientEntity.getInstance().setContent(scanner.nextLine());
                output.writeUTF(sendMessage());
                if (ClientEntity.getInstance().getContent().equals("#exit")) {
                    System.exit(0);
                }
//                System.out.println("SENT: " + ClientEntity.getInstance().getContent());
            }
        } catch (IOException e) {
            System.err.println("Error writing message: " + e.getMessage());
        }
    }

    public String sendMessage() {
        sendMessage.put("username", ClientEntity.getInstance().getUsername());
        sendMessage.put("content", ClientEntity.getInstance().getContent());

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(sendMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void parseJSONMessage(String json) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            ClientEntity client = objectMapper.readValue(json, ClientEntity.class);
            receivedMessage.put("username", client.getUsername());
            receivedMessage.put("content", client.getContent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeResources() {
        try {
            if (output != null) output.close();
            if (input != null) input.close();
            if (clientSocket != null && !clientSocket.isClosed()) clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }
}