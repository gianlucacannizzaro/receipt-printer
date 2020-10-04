package it.cannizzaro.receiptprinter;

import it.cannizzaro.receiptprinter.data.entities.Category;
import it.cannizzaro.receiptprinter.data.entities.Item;
import it.cannizzaro.receiptprinter.data.entities.Receipt;
import it.cannizzaro.receiptprinter.data.entities.Tax;
import it.cannizzaro.receiptprinter.service.ReceiptService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TaxTests
{
        private Tax basicSaleTax;
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

        @BeforeAll
        void contextLoads()
        {
                bookCategory = new Category("books");
                foodCategory = new Category("food");
                medicalCategory = new Category("medical");
                otherCategory = new Category("other");

                localBook = new Item("localBook", bookCategory, false);
                localFood = new Item("localFood", foodCategory, false);
                localMedicine = new Item("localMedicine", medicalCategory, false);
                localOtherProduct = new Item("localOtherProduct", otherCategory, false);

                importedBook = new Item("importedBook", bookCategory, true);
                importedFood = new Item("importedFood", foodCategory, true);
                importedMedicine = new Item("importedMedicine", medicalCategory, true);
                importedOtherProduct = new Item("importedOtherProduct", otherCategory, true);

                String basicSaleTaxMvel = "!exemptions.contains(item.category)";

                List<Category> exemptions = new ArrayList<>();
                exemptions.add(foodCategory);
                exemptions.add(bookCategory);
                exemptions.add(medicalCategory);
                Map<String, Object> context = new HashMap<>();
                context.put("exemptions", exemptions);

                basicSaleTax = new Tax("basic sales tax", new BigDecimal("0.1"), basicSaleTaxMvel, context);
                String importDutyMvel = "item.imported == true";
                importDutyTax = new Tax("import duty tax", new BigDecimal("0.05"), importDutyMvel, new HashMap<>());

        }

        @Test
        void test_apply_taxes()
        {
                assertFalse(basicSaleTax.isAppliableTo(localFood));
                assertFalse(basicSaleTax.isAppliableTo(importedFood));
                assertFalse(basicSaleTax.isAppliableTo(localBook));
                assertFalse(basicSaleTax.isAppliableTo(importedBook));
                assertFalse(basicSaleTax.isAppliableTo(localMedicine));
                assertFalse(basicSaleTax.isAppliableTo(importedMedicine));
                assertTrue(basicSaleTax.isAppliableTo(localOtherProduct));
                assertTrue(basicSaleTax.isAppliableTo(importedOtherProduct));

                assertFalse(importDutyTax.isAppliableTo(localFood));
                assertTrue(importDutyTax.isAppliableTo(importedFood));
                assertFalse(importDutyTax.isAppliableTo(localBook));
                assertTrue(importDutyTax.isAppliableTo(importedBook));
                assertFalse(importDutyTax.isAppliableTo(localMedicine));
                assertTrue(importDutyTax.isAppliableTo(importedMedicine));
                assertFalse(importDutyTax.isAppliableTo(localOtherProduct));
                assertTrue(importDutyTax.isAppliableTo(importedOtherProduct));
        }

        @Test
        void test_receipt_1()
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
        void test_receipt_2()
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
        void test_receipt_3()
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
