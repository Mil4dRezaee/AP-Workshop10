import java.io.*;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {

    public static List<DataOutputStream> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        int serverPort;

        try (FileInputStream input = new FileInputStream("src/config.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            serverPort = Integer.parseInt(properties.getProperty("serverPort"));
        }

        try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
            System.out.println("Server started ...");

            while (true) {
                ClientHandler clientSocket = new ClientHandler(serverSocket.accept());
                Thread thread = new Thread(clientSocket);
                thread.start();
            }

        } finally {
            System.out.println("Closing server ...");
        }
    }

    public static synchronized void addClient(DataOutputStream client) {
        clients.add(client);
    }

    public static synchronized void removeClient(DataOutputStream client) {
        clients.remove(client);
    }

    public static synchronized void broadcast(String message, DataOutputStream sender) throws IOException {
        for (DataOutputStream client : clients) {
            if (client != sender) {
                client.writeUTF(message);
            }
        }
    }
}