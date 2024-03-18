package imd.ufrn.interfaces;

import java.util.function.Consumer;

public abstract class BaseInputReceiver implements Runnable {
    protected Consumer<String> callbackFunctionMessageWritten;

    public BaseInputReceiver(Consumer<String> callbackFunctionMessageWritten) {
        this.callbackFunctionMessageWritten = callbackFunctionMessageWritten;
    }
}
