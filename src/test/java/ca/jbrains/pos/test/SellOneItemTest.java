package ca.jbrains.pos.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class SellOneItemTest {
    @Test
    void productFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display, new HashMap<>() {{
            put("12345", "234,90 Kč");
            put("23456", "478,52 Kč");
        }});

        sale.onBarcode("12345");

        Assertions.assertEquals("234,90 Kč", display.getText());
    }

    @Test
    void anotherProductFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display, new HashMap<>() {{
            put("12345", "234,90 Kč");
            put("23456", "478,52 Kč");
        }});

        sale.onBarcode("23456");

        Assertions.assertEquals("478,52 Kč", display.getText());
    }

    @Test
    void productNotFound() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display, new HashMap<>() {{
            put("12345", "234,90 Kč");
            put("23456", "478,52 Kč");
        }});

        sale.onBarcode("99999");

        Assertions.assertEquals("Product not found: 99999", display.getText());
    }

    @Test
    void emptyBarcode() throws Exception {
        final Display display = new Display();
        final Sale sale = new Sale(display, new HashMap<>());

        sale.onBarcode("");

        Assertions.assertEquals("Scanning error: empty barcode", display.getText());
    }

    public static class Sale {
        private Display display;
        private Map<String, String> pricesByBarcode;

        public Sale(Display display, final Map<String, String> pricesByBarcode) {
            this.display = display;
            this.pricesByBarcode = pricesByBarcode;
        }

        public void onBarcode(String barcode) {
            if ("".equals(barcode))
                display.setText("Scanning error: empty barcode");
            else {
                final String priceAsText = pricesByBarcode.get(barcode);
                if (priceAsText == null) {
                    display.setText(String.format("Product not found: %s", barcode));
                }
                else {
                    display.setText(priceAsText);
                }
            }
        }
    }

    public static class Display {
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
