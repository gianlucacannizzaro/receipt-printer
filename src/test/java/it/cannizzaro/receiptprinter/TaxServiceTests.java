package it.cannizzaro.receiptprinter;

import it.cannizzaro.receiptprinter.entities.business.Item;
import it.cannizzaro.receiptprinter.entities.business.Receipt;
import it.cannizzaro.receiptprinter.entities.domain.Category;
import it.cannizzaro.receiptprinter.entities.domain.Tax;
import it.cannizzaro.receiptprinter.service.business.ReceiptService;
import it.cannizzaro.receiptprinter.service.domain.CategoryService;
import it.cannizzaro.receiptprinter.service.domain.TaxService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TaxServiceTests
{
        private Tax basicSalesTax;
        private Tax importDutyTax;

        private Category bookCategory;
        private Category foodCategory;
        private Category medicalCategory;
        private Category otherCategory;

        private Item localBook;
        private Item localFood;
        private Item localMedicine;
        private Item localOtherProduct;

        private Item importedBook;
        private Item importedFood;
        private Item importedMedicine;
        private Item importedOtherProduct;

        @Autowired
        private ReceiptService receiptService;

        @Autowired
        private TaxService taxService;

        @Autowired
        private CategoryService categoryService;

        @BeforeAll
        void contextLoads()
        {
                bookCategory = categoryService.findByName("Book");
                foodCategory = categoryService.findByName("Food");
                medicalCategory = categoryService.findByName("Medical");
                otherCategory = categoryService.findByName("Other");

                basicSalesTax = taxService.findByName("Basic sales tax");
                importDutyTax = taxService.findByName("Import duty");

                localBook = new Item("localBook", bookCategory, false);
                localFood = new Item("localFood", foodCategory, false);
                localMedicine = new Item("localMedicine", medicalCategory, false);
                localOtherProduct = new Item("localOtherProduct", otherCategory, false);

                importedBook = new Item("importedBook", bookCategory, true);
                importedFood = new Item("importedFood", foodCategory, true);
                importedMedicine = new Item("importedMedicine", medicalCategory, true);
                importedOtherProduct = new Item("importedOtherProduct", otherCategory, true);

        }

        @Test
        void test_apply_taxes()
        {
                assertFalse(basicSalesTax.isApplicableTo(localFood));
                assertFalse(basicSalesTax.isApplicableTo(importedFood));
                assertFalse(basicSalesTax.isApplicableTo(localBook));
                assertFalse(basicSalesTax.isApplicableTo(importedBook));
                assertFalse(basicSalesTax.isApplicableTo(localMedicine));
                assertFalse(basicSalesTax.isApplicableTo(importedMedicine));
                assertTrue(basicSalesTax.isApplicableTo(localOtherProduct));
                assertTrue(basicSalesTax.isApplicableTo(importedOtherProduct));

                assertFalse(importDutyTax.isApplicableTo(localFood));
                assertTrue(importDutyTax.isApplicableTo(importedFood));
                assertFalse(importDutyTax.isApplicableTo(localBook));
                assertTrue(importDutyTax.isApplicableTo(importedBook));
                assertFalse(importDutyTax.isApplicableTo(localMedicine));
                assertTrue(importDutyTax.isApplicableTo(importedMedicine));
                assertFalse(importDutyTax.isApplicableTo(localOtherProduct));
                assertTrue(importDutyTax.isApplicableTo(importedOtherProduct));
        }


}
