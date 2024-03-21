package imd.ufrn;

import imd.ufrn.interfaces.IChatPresenter;
import imd.ufrn.interfaces.BaseCommunicationWithServerController;
import imd.ufrn.interfaces.BaseInputReceiver;

// controller could be a thread (runnable) that saves the values sent by the callback and unlocks a concurrent lock
// that was locking the execution of the specific handle method (I feel that it would need a little bit more complexity than described)
// this way, all the logic is done in the ChatController thread without stoping the other threads for 
// the execution of the processing
// but that is a lot of unnecessary complexity for this project.
public class ChatController {
    BaseCommunicationWithServerController serverCommunicationController; // Maybe there's no need to save this
    BaseInputReceiver inputReceiver; // Maybe there's no need to save this
    Thread serverCommunicationControllerThread;
    Thread inputRecieverThread;
    IChatPresenter chatPresenter;

    public ChatController() {
        serverCommunicationController = new SocketCommunicationController(
                serverMessage -> handleMessageRecievedFromServer(serverMessage));
        inputReceiver = new InputReceiverTerminalImpl(
                message -> handleSendMessageFromClient(message));
        chatPresenter = new ChatPresenterTerminalImpl();

        serverCommunicationControllerThread = new Thread(serverCommunicationController);
        inputRecieverThread = new Thread(inputReceiver);
        serverCommunicationControllerThread.start();
        inputRecieverThread.start();
    }

    public void handleSendMessageFromClient(String clientMessage) {
        serverCommunicationController.sendMessage(clientMessage);
        chatPresenter.showNewMessageFromClient(clientMessage);
    }

    public void handleMessageRecievedFromServer(String serverMessage) {
        chatPresenter.showNewMessageFromServer(serverMessage);
    }
}
