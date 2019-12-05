package ca.jbrains.pos.test;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.function.Consumer;

public class RoutePointOfSaleCommandsTest {
    @Test
    void quit() throws Exception {
        final Consumer<String> quitCommand = Mockito.mock(Consumer.class, "quit command");
        final Consumer<String> handleBarcodeCommand = Mockito.mock(Consumer.class, "handle barcode command");

        routePointOfSaleCommand("q", quitCommand, handleBarcodeCommand);

        Mockito.verify(quitCommand).accept(Mockito.anyString());
    }

    private void routePointOfSaleCommand(String command, Consumer<String> quitCommand, Consumer<String> handleBarcodeCommand) {
        quitCommand.accept(command);
    }
}
