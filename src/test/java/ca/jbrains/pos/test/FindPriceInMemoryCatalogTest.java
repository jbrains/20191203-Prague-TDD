package ca.jbrains.pos.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

public class FindPriceInMemoryCatalogTest {
    @Test
    void productFound() throws Exception {
        final Price matchingPrice = Price.cents(9214);
        final InMemoryCatalog catalog = createCatalogWith("::barcode::", matchingPrice);
        Assertions.assertEquals(matchingPrice, catalog.findPrice("::barcode::"));
    }

    private InMemoryCatalog createCatalogWith(final String barcode, Price matchingPrice) {
        return new InMemoryCatalog(new HashMap<>() {{
            put(String.format("something other than %s", barcode), Price.cents(-1));
            put(barcode, matchingPrice);
            put(String.format("%s is not this thing", barcode), Price.cents(-2));
            put(String.format("not %s", barcode), Price.cents(-3));
        }});
    }

    @Test
    void productNotFound() throws Exception {
        final InMemoryCatalog catalog = createCatalogWithout("::barcode::");
        Assertions.assertEquals(null, catalog.findPrice("::barcode::"));
    }

    private InMemoryCatalog createCatalogWithout(String barcodeToAvoid) {
        return new InMemoryCatalog(new HashMap<>() {{
            put(String.format("something other than %s", barcodeToAvoid), Price.cents(-1));
            put(String.format("%s is not this thing", barcodeToAvoid), Price.cents(-2));
            put(String.format("not %s", barcodeToAvoid), Price.cents(-3));
        }});
    }

    public static class InMemoryCatalog implements Catalog {
        private final Map<String, Price> pricesByBarcode;

        public InMemoryCatalog(Map<String, Price> pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        public Price findPrice(String barcode) {
            return pricesByBarcode.get(barcode);
        }
    }
}
