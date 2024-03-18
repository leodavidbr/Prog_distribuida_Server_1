package imd.ufrn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.function.Consumer;

import imd.ufrn.exceptions.NotInitializedException;
import imd.ufrn.interfaces.BaseCommunicationWithServerController;

// offers the sendMessage method and 
// calls the callbackFunctionMessageRecieved function when a 
// new message is recieved from the server
public class SocketCommunicationController extends BaseCommunicationWithServerController {

    private Socket clientSocket;
    private BufferedReader readStream;
    private PrintWriter writeStream;

    private String host = "127.0.0.1";
    private int port = 9999;

    public SocketCommunicationController(Consumer<String> callbackFunctionMessageRecieved, String host, int port) {
        super(callbackFunctionMessageRecieved);
        this.host = host;
        this.port = port;
    }

    public SocketCommunicationController(Consumer<String> callbackFunctionMessageRecieved) {
        super(callbackFunctionMessageRecieved);
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
        if (!initializeSocket())
            return false;
        if (!initializeReadAndWriteStream())
            return false;

        return true;
    }

    private boolean initializeSocket() {
        try {
            clientSocket = new Socket(host, port);

        } catch (UnknownHostException e) {
            System.out.println("Wrong address. Host: " + host + " Port: " + port);
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            System.out.println("Failed to connect to the server. The server may be offline");
            e.printStackTrace();
            return false;
        }
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
                System.out.println("Message recieved from server: " + message);
                callbackFunctionMessageRecieved.accept(message);
            }
        } catch (IOException e) {
            System.out.println("Failed to read message from server");
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
