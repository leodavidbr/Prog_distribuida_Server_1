package imd.ufrn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Consumer;

import imd.ufrn.interfaces.BaseInputReceiver;

public class InputReceiverTerminalImpl extends BaseInputReceiver {

    public InputReceiverTerminalImpl(Consumer<String> callbackFunctionMessageWritten) {
        super(callbackFunctionMessageWritten);
    }

    @Override
    public void run() {
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
