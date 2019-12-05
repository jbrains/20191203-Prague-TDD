package ca.jbrains.pos.test;

import java.util.HashMap;
import java.util.Map;

public class FindPriceInMemoryCatalogTest extends FindPriceInCatalogContract {
    @Override
    protected Catalog createCatalogWith(final String barcode, Price matchingPrice) {
        return new InMemoryCatalog(new HashMap<>() {{
            put(String.format("something other than %s", barcode), Price.cents(-1));
            put(barcode, matchingPrice);
            put(String.format("%s is not this thing", barcode), Price.cents(-2));
            put(String.format("not %s", barcode), Price.cents(-3));
        }});
    }

    @Override
    protected Catalog createCatalogWithout(String barcodeToAvoid) {
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
