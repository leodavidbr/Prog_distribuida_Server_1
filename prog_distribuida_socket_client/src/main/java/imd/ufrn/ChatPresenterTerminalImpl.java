package imd.ufrn;

import imd.ufrn.interfaces.IChatPresenter;

public class ChatPresenterTerminalImpl implements IChatPresenter {

    public ChatPresenterTerminalImpl() {
        showInitialMessage();
    }

    private void showInitialMessage() {
        System.out.println("Welcome to OQueBot");
        System.out.println("Write your message and the bot will answer you");
    }

    @Override
    public void showNewMessageFromClient(String message) {
        System.out.println("client said: " + message);
    }

    @Override
    public void showNewMessageFromServer(String message) {
        System.out.println("server said: " + message);
    }
}
