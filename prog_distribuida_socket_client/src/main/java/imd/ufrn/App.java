package imd.ufrn;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        Runnable chatController = new ChatController();
        Thread chatContollerThread = new Thread(chatController);
        chatContollerThread.start();
    }
}
