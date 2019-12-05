package ca.jbrains.pos.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Collections;
import java.util.Map;

public class FindPriceInMemoryCatalogTest {
    @Test
    void productFound() throws Exception {
        final Price matchingPrice = Price.cents(9214);
        final InMemoryCatalog catalog = new InMemoryCatalog(Collections.singletonMap("::barcode::", matchingPrice));
        Assertions.assertEquals(matchingPrice, catalog.findPrice("::barcode::"));
    }

    @Test
    void productNotFound() throws Exception {
        final InMemoryCatalog catalog = new InMemoryCatalog(Collections.emptyMap());
        Assertions.assertEquals(null, catalog.findPrice("::barcode::"));
    }

    @Test
    void productFoundAmongMany() throws Exception {
        final Price matchingPrice = Price.cents(9214);
        final InMemoryCatalog catalog = new InMemoryCatalog(new HashMap<>() {{
            put("something other than ::barcode::", Price.cents(-1));
            put("::barcode::", matchingPrice);
            put("::barcode:: is not this thing", Price.cents(-2));
            put("not ::barcode::", Price.cents(-3));
        }});
        Assertions.assertEquals(matchingPrice, catalog.findPrice("::barcode::"));
    }

    public static class InMemoryCatalog {
        private final Map<String, Price> pricesByBarcode;

        public InMemoryCatalog(Map<String, Price> pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        public Price findPrice(String barcode) {
            return pricesByBarcode.get(barcode);
        }
    }
}
