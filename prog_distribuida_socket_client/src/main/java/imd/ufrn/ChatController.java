package imd.ufrn;

import imd.ufrn.interfaces.IChatPresenter;
import imd.ufrn.interfaces.BaseCommunicationWithServerController;
import imd.ufrn.interfaces.BaseInputReceiver;

public class ChatController implements Runnable {
    BaseCommunicationWithServerController serverCommunicationController;
    BaseInputReceiver inputReceiver;
    IChatPresenter chatPresenter;

    public ChatController() {
        serverCommunicationController = new SocketCommunicationController(
                serverMessage -> handleMessageRecievedFromServer(serverMessage));
        inputReceiver = new InputReceiverTerminalImpl(
                message -> handleSendMessageFromClient(message));
        chatPresenter = new ChatPresenterTerminalImpl();
    }

    @Override
    public void run() {

    }

    public void handleSendMessageFromClient(String clientMessage) {
        serverCommunicationController.sendMessage(clientMessage);
        chatPresenter.showNewMessageFromClient(clientMessage);
    }

    public void handleMessageRecievedFromServer(String serverMessage) {
        chatPresenter.showNewMessageFromServer(serverMessage);
    }
}
