package imd.ufrn.interfaces;

import java.util.function.Consumer;

// offers the sendMessage method and 
// calls the callbackFunctionMessageReceived function when a 
// new message is received from the server
// has to be initialized before use.
public abstract class BaseCommunicationWithClientController implements Runnable {

    protected Consumer<String> callbackFunctionMessageReceived;

    public BaseCommunicationWithClientController(Consumer<String> callbackFunctionMessageReceived) {
        this.callbackFunctionMessageReceived = callbackFunctionMessageReceived;
    }

    protected abstract boolean initialize();

    public abstract void sendMessage(String mensagem);
}
