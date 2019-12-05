package ca.jbrains.pos;

import java.util.HashMap;

public class PointOfSaleTerminal {
    public static void main(String[] args) {
        final SellOneItemController sellOneItemController = new SellOneItemController(
                new InMemoryCatalog(
                        new HashMap<>() {{
                            put("12345", Price.cents(17300));
                            put("23456", Price.cents(29850));
                        }}
                ),
                new ConsoleDisplay()
        );

        // canonicalizeLines.canonicalizeLines(streamLines.streamAsLines()).forEach(handleCommand::accept)

        sellOneItemController.onBarcode("12345");
        sellOneItemController.onBarcode("23456");
        sellOneItemController.onBarcode("99999");
        sellOneItemController.onBarcode("");
    }
}
