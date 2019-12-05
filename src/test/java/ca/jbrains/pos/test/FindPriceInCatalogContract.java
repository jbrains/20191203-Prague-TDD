package ca.jbrains.pos.test;

import ca.jbrains.pos.Catalog;
import ca.jbrains.pos.Price;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public abstract class FindPriceInCatalogContract {
    @Test
    void productFound() throws Exception {
        final Price matchingPrice = Price.cents(9214);
        final Catalog catalog = createCatalogWith("::barcode::", matchingPrice);
        Assertions.assertEquals(matchingPrice, catalog.findPrice("::barcode::"));
    }

    protected abstract Catalog createCatalogWith(String barcode, Price matchingPrice);

    @Test
    void productNotFound() throws Exception {
        final Catalog catalog = createCatalogWithout("::barcode::");
        Assertions.assertEquals(null, catalog.findPrice("::barcode::"));
    }

    protected abstract Catalog createCatalogWithout(String barcodeToAvoid);
}
