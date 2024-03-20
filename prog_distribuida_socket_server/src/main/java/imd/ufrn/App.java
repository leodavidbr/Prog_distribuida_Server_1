package imd.ufrn;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class App {
    private static ServerSocket serverSocket;
    public static final int PORT = 9999;

    public static void main(String[] args) throws IOException {
        serverSocket = new ServerSocket(PORT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            Runnable chatController = new ChatController(clientSocket);
            Thread chatContollerThread = new Thread(chatController);
            chatContollerThread.start();
        }
    }
}
