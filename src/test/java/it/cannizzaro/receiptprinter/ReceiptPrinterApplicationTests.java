package it.cannizzaro.receiptprinter;

import it.cannizzaro.receiptprinter.data.entities.Category;
import it.cannizzaro.receiptprinter.data.entities.Item;
import it.cannizzaro.receiptprinter.data.entities.Tax;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReceiptPrinterApplicationTests
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

}
