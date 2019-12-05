package ca.jbrains.pos.test;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.function.Consumer;

public class RoutePointOfSaleCommandsTest {
    // CONTRACT We can assume that all commands are canonicalized,
    // which means that they are not empty and are trimmed.

    @Test
    void quit() throws Exception {
        final Consumer<String> quitCommand = Mockito.mock(Consumer.class, "quit command");
        final Consumer<String> handleBarcodeCommand = Mockito.mock(Consumer.class, "handle barcode command");

        routePointOfSaleCommand("q", quitCommand, handleBarcodeCommand);

        Mockito.verify(quitCommand).accept(Mockito.anyString());
    }

    @Test
    void handleBarcode() throws Exception {
        final Consumer<String> quitCommand = Mockito.mock(Consumer.class, "quit command");
        final Consumer<String> handleBarcodeCommand = Mockito.mock(Consumer.class, "handle barcode command");

        routePointOfSaleCommand("::any other command::", quitCommand, handleBarcodeCommand);

        Mockito.verify(handleBarcodeCommand).accept("::any other command::");

    }

    private void routePointOfSaleCommand(String command, Consumer<String> quitCommand, Consumer<String> handleBarcodeCommand) {
        if ("q".equals(command))
            quitCommand.accept(command);
        else
            handleBarcodeCommand.accept(command);
    }
}
