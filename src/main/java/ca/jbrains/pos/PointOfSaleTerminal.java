package ca.jbrains.pos;

import java.io.InputStreamReader;
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

        new CanonicalizeLinesByRemovingWhitespace().canonicalizeLines(
                new StreamLinesFromReader(new InputStreamReader(System.in)).streamAsLines()
        ).forEach(sellOneItemController::onBarcode);
    }
}
