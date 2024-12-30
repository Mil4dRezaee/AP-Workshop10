import java.io.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws IOException {

        String serverAddress;
        int serverPort;

        try (FileInputStream input = new FileInputStream("src/config.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            serverAddress = properties.getProperty("serverAddress");
            serverPort = Integer.parseInt(properties.getProperty("serverPort"));
        }

        new ChatClient(serverAddress, serverPort).start();

    }
}