package imd.ufrn.interfaces;

import java.util.function.Consumer;

// offers the sendMessage method and 
// calls the callbackFunctionMessageRecieved function when a 
// new message is recieved from the server
// has to be initialized before use.
public interface ICommunicationWithServerController {

    public boolean initialize(Consumer<String> callbackFunctionMessageRecieved);

    public void sendMessage(String mensagem);
}
