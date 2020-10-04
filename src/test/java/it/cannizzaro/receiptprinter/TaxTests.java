package it.cannizzaro.receiptprinter;

import it.cannizzaro.receiptprinter.entities.business.Item;
import it.cannizzaro.receiptprinter.entities.business.Receipt;
import it.cannizzaro.receiptprinter.entities.domain.Category;
import it.cannizzaro.receiptprinter.entities.domain.Tax;
import it.cannizzaro.receiptprinter.service.CategoryService;
import it.cannizzaro.receiptprinter.service.ReceiptService;
import it.cannizzaro.receiptprinter.service.TaxService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TaxTests
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

        @Test
        void test_process_receipt_1()
        {
                Item book = new Item("book", bookCategory, false, new BigDecimal("12.49"));
                Item cd = new Item("cd", otherCategory, false, new BigDecimal("14.99"));
                Item chocolate = new Item("chocolate", foodCategory, false, new BigDecimal("0.85"));

                Receipt receipt = new Receipt();
                receipt.add(book);
                receipt.add(cd);
                receipt.add(chocolate);

                receiptService.process(receipt);

                assertEquals(new BigDecimal("12.49"), book.getTaxedPrice());
                assertEquals(new BigDecimal("16.49"), cd.getTaxedPrice());
                assertEquals(new BigDecimal("0.85"), chocolate.getTaxedPrice());
                assertEquals(new BigDecimal("1.50"), receipt.getTotalSalesTaxes());
                assertEquals(new BigDecimal("29.83"), receipt.getTotalCost());

        }

        @Test
        void test_process_receipt_2()
        {
                Item chocolate = new Item("chocolate", foodCategory, true, new BigDecimal("10.00"));
                Item perfume = new Item("perfume", otherCategory, true, new BigDecimal("47.50"));

                Receipt receipt = new Receipt();
                receipt.add(chocolate);
                receipt.add(perfume);

                receiptService.process(receipt);

                assertEquals(new BigDecimal("10.50"), chocolate.getTaxedPrice());
                assertEquals(new BigDecimal("54.65"), perfume.getTaxedPrice());
                assertEquals(new BigDecimal("7.65"), receipt.getTotalSalesTaxes());
                assertEquals(new BigDecimal("65.15"), receipt.getTotalCost());
        }

        @Test
        void test_process_receipt_3()
        {
                Item importedPerfume = new Item("importedPerfume", otherCategory, true, new BigDecimal("27.99"));
                Item perfume = new Item("perfume", otherCategory, false, new BigDecimal("18.99"));
                Item pills = new Item("pills", medicalCategory, false, new BigDecimal("9.75"));
                Item importedChocolate = new Item("importedChocolate", foodCategory, true, new BigDecimal("11.25"));

                Receipt receipt = new Receipt();
                receipt.add(importedPerfume);
                receipt.add(perfume);
                receipt.add(pills);
                receipt.add(importedChocolate);

                receiptService.process(receipt);

                assertEquals(new BigDecimal("32.19"), importedPerfume.getTaxedPrice());
                assertEquals(new BigDecimal("20.89"), perfume.getTaxedPrice());
                assertEquals(new BigDecimal("9.75"), pills.getTaxedPrice());
                assertEquals(new BigDecimal("11.85"), importedChocolate.getTaxedPrice());
                assertEquals(new BigDecimal("6.70"), receipt.getTotalSalesTaxes());
                assertEquals(new BigDecimal("74.68"), receipt.getTotalCost());
        }

}
