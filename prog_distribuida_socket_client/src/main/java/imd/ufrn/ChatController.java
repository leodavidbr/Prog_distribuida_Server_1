package imd.ufrn;

import imd.ufrn.interfaces.IChatPresenter;
import imd.ufrn.interfaces.ICommunicationWithServerController;

public class ChatController implements Runnable {
    ICommunicationWithServerController serverCommunicationController;
    IChatPresenter chatPresenter;

    public ChatController() {
        serverCommunicationController = new SocketCommunicationController();
        serverCommunicationController.initialize(serverMessage -> handleMessageRecievedFromServer(serverMessage));
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
