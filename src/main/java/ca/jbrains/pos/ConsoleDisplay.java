package ca.jbrains.pos;

import java.util.Locale;

public class ConsoleDisplay implements Display {
    @Override
    public void displayPrice(Price price) {
        System.out.println(String.format(Locale.forLanguageTag("cs"), "%.2f Kč", price.koruna()));
    }

    @Override
    public void displayProductNotFoundMessage(String missingBarcode) {
        System.out.println(String.format("Product not found: %s", missingBarcode));
    }

    @Override
    public void displayScannedEmptyBarcodeMessage() {
        System.out.println("Scanning error: empty barcode");
    }
}
