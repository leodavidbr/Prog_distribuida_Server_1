package imd.ufrn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Consumer;

import imd.ufrn.exceptions.NotInitializedException;
import imd.ufrn.interfaces.IInputReceiver;

public class InputReceiverTerminalImpl implements IInputReceiver, Runnable {
    private Consumer<String> callbackFunctionMessageWritten;
    private boolean isInitialized = false;

    @Override
    public void initialize(Consumer<String> callbackFunctionMessageWritten) {
        this.callbackFunctionMessageWritten = callbackFunctionMessageWritten;
        isInitialized = true;
    }

    @Override
    public void run() {
        if (!isInitialized) {
            throw new NotInitializedException(
                    "The inputReceiver was not initialized calling the initialize method before calling run");
        }

        BufferedReader terminalReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String terminalMessage;
            while (true) {
                terminalMessage = terminalReader.readLine();
                if (terminalMessage == null || terminalMessage.length() == 0) {
                    continue;
                }

                callbackFunctionMessageWritten.accept(terminalMessage);
            }
        } catch (IOException e) {
            System.out.println("Could not correctly read input from terminal");
            e.printStackTrace();
        }
    }

}
