package imd.ufrn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Consumer;

import imd.ufrn.exceptions.NotInitializedException;
import imd.ufrn.interfaces.BaseCommunicationWithClientController;

public class SocketCommunicationController extends BaseCommunicationWithClientController {
    private Socket clientSocket;
    private BufferedReader readStream;
    private PrintWriter writeStream;

    public SocketCommunicationController(Consumer<String> callbackFunctionMessageReceived, Socket clientSocket) {
        super(callbackFunctionMessageReceived);
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        // TODO: Verify if initialize() should instead be on the constructor
        boolean isInitializedCorrectly = initialize();
        if (!isInitializedCorrectly) {
            throw new NotInitializedException("Socket could not initialize correctly");
        }
        getMessageLoop();
    }

    @Override
    public void sendMessage(String mensagem) {
        if (mensagem == null || mensagem.length() == 0) {
            return;
        }

        writeStream.println(mensagem);
        // if (mensagem.equalsIgnoreCase("::EXIT")) {
        // System.exit(0);
        // }
    }

    @Override
    protected boolean initialize() {
        if (!initializeReadAndWriteStream())
            return false;

        return true;
    }

    private boolean initializeReadAndWriteStream() {
        try {
            readStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Failed to initialize readStream");
            e.printStackTrace();
            return false;
        }
        try {
            writeStream = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Problem initializing writeStream");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void getMessageLoop() {
        String message;
        try {
            while (true) {
                message = getMessage();
                System.out.println("Message received from client: " + message);
                callbackFunctionMessageReceived.accept(message);
            }
        } catch (IOException e) {
            System.out.println("Failed to read message from client");
            e.printStackTrace();
            return;
        }

    }

    private String getMessage() throws IOException {
        String message = "";
        message = readStream.readLine();

        return message;
    }

}
