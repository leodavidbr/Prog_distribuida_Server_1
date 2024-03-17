package imd.ufrn.interfaces;

import java.util.function.Consumer;

public interface IInputReceiver {
    public void initialize(Consumer<String> callbackFunctionMessageWritten);
}
