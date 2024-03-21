package imd.ufrn;

import imd.ufrn.interfaces.IChatPresenter;

public class ChatPresenterTerminalImpl implements IChatPresenter {

    public ChatPresenterTerminalImpl() {
        showInitialMessage();
    }

    synchronized private void showInitialMessage() {
        System.out.println("Welcome to OQueBot");
        System.out.println("Write your message and the bot will answer you: ");
        System.out.println("");
    }

    @Override
    synchronized public void showNewMessageFromClient(String message) {
        // System.out.println("client said: " + message);
    }

    @Override
    synchronized public void showNewMessageFromServer(String message) {
        System.out.println("server said: " + message);
    }
}
