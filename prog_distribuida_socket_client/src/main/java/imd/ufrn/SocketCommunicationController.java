package imd.ufrn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.function.Consumer;

import imd.ufrn.exceptions.NotInitializedException;
import imd.ufrn.interfaces.ICommunicationWithServerController;

// offers the sendMessage method and 
// calls the callbackFunctionMessageRecieved function when a 
// new message is recieved from the server
public class SocketCommunicationController implements Runnable, ICommunicationWithServerController {

    private Socket clientSocket;
    private BufferedReader readStream;
    private PrintWriter writeStream;

    private String host = "127.0.0.1";
    private int port = 9999;

    private Consumer<String> callbackFunctionMessageRecieved;

    public boolean isInitialized = false;

    public SocketCommunicationController() {
    }

    public SocketCommunicationController(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        if (!isInitialized) {
            throw new NotInitializedException(
                    "The Socket was not initialized calling the method initialize before calling run");
        }
        getMessageLoop();
    }

    @Override
    public void sendMessage(String mensagem) {
        if (!isInitialized) {
            throw new NotInitializedException(
                    "The Socket was not initialized calling the method initialize before calling sendMessage");
        }
        if (mensagem == null || mensagem.length() == 0) {
            return;
        }

        writeStream.println(mensagem);
        // if (mensagem.equalsIgnoreCase("::EXIT")) {
        // System.exit(0);
        // }
    }

    @Override
    public boolean initialize(Consumer<String> callbackFunctionMessageRecieved) {
        if (!initializeSocket())
            return false;
        if (!initializeReadAndWriteStream())
            return false;
        this.callbackFunctionMessageRecieved = callbackFunctionMessageRecieved;

        isInitialized = true;
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
