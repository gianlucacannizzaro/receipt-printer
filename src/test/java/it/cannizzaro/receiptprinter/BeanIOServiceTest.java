package it.cannizzaro.receiptprinter;

import it.cannizzaro.receiptprinter.entities.business.Item;
import it.cannizzaro.receiptprinter.entities.business.Receipt;
import it.cannizzaro.receiptprinter.entities.domain.Category;
import it.cannizzaro.receiptprinter.service.CategoryService;
import it.cannizzaro.receiptprinter.service.ParsingService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BeanIOServiceTest
{

        private Category bookCategory;
        private Category foodCategory;
        private Category medicalCategory;
        private Category otherCategory;

        @Autowired
        private ParsingService parsingService;

        @Autowired
        private CategoryService categoryService;

        @BeforeAll
        void contextLoads()
        {
                bookCategory = categoryService.findByName("Book");
                foodCategory = categoryService.findByName("Food");
                medicalCategory = categoryService.findByName("Medical");
                otherCategory = categoryService.findByName("Other");
        }

        @Test
        void test_parse_receipt_1() throws IOException
        {
                ClassLoader classLoader = getClass().getClassLoader();
                File file = new File(classLoader.getResource("static/receipt_1.txt").getFile());

                Receipt parsedReceipt = parsingService.parseReceipt(file);

                Item book = parsedReceipt.getItems().stream().filter(i -> "book".equals(i.getName())).findFirst().get();
                Item cd = parsedReceipt.getItems().stream().filter(i -> "music CD".equals(i.getName())).findFirst().get();
                Item chocolate = parsedReceipt.getItems().stream().filter(i -> "chocolate bar".equals(i.getName())).findFirst().get();

                assertNotNull(book);
                assertNotNull(cd);
                assertNotNull(chocolate);

                assertEquals(bookCategory, book.getCategory());
                assertEquals(otherCategory, cd.getCategory());
                assertEquals(foodCategory, chocolate.getCategory());

                assertFalse(book.getImported());
                assertFalse(cd.getImported());
                assertFalse(chocolate.getImported());

                assertEquals(new BigDecimal("12.49"), book.getBasePrice());
                assertEquals(new BigDecimal("14.99"), cd.getBasePrice());
                assertEquals(new BigDecimal("0.85"), chocolate.getBasePrice());

        }

        @Test
        void test_parse_receipt_2() throws IOException
        {
                ClassLoader classLoader = getClass().getClassLoader();
                File file = new File(classLoader.getResource("static/receipt_2.txt").getFile());
                Receipt parsedReceipt = parsingService.parseReceipt(file);

                Item chocolate = parsedReceipt.getItems().stream().filter(i -> "box of chocolates".equals(i.getName())).findFirst().get();
                Item perfume = parsedReceipt.getItems().stream().filter(i -> "bottle of perfume".equals(i.getName())).findFirst().get();

                assertNotNull(chocolate);
                assertNotNull(perfume);

                assertEquals(foodCategory, chocolate.getCategory());
                assertEquals(otherCategory, perfume.getCategory());

                assertTrue(chocolate.getImported());
                assertTrue(perfume.getImported());

                assertEquals(new BigDecimal("10.00"), chocolate.getBasePrice());
                assertEquals(new BigDecimal("47.50"), perfume.getBasePrice());

        }

        @Test
        void test_process_receipt_3() throws IOException
        {
                ClassLoader classLoader = getClass().getClassLoader();
                File file = new File(classLoader.getResource("static/receipt_3.txt").getFile());

                Receipt parsedReceipt = parsingService.parseReceipt(file);

                Item importedPerfume = parsedReceipt.getItems().stream()
                        .filter(i -> "bottle of perfume".equals(i.getName()) && Boolean.TRUE.equals(i.getImported())).findFirst().get();
                Item perfume = parsedReceipt.getItems().stream()
                        .filter(i -> "bottle of perfume".equals(i.getName()) && Boolean.FALSE.equals(i.getImported())).findFirst().get();
                Item pills = parsedReceipt.getItems().stream().filter(i -> "packet of headache pills".equals(i.getName())).findFirst().get();
                Item importedChocolate = parsedReceipt.getItems().stream().filter(i -> "box of chocolates".equals(i.getName())).findFirst().get();

                assertNotNull(importedPerfume);
                assertNotNull(perfume);
                assertNotNull(pills);
                assertNotNull(importedChocolate);

                assertTrue(importedPerfume.getImported());
                assertTrue(importedChocolate.getImported());
                assertFalse(perfume.getImported());
                assertFalse(pills.getImported());

                assertEquals(otherCategory, importedPerfume.getCategory());
                assertEquals(otherCategory, perfume.getCategory());
                assertEquals(medicalCategory, pills.getCategory());
                assertEquals(foodCategory, importedChocolate.getCategory());

                assertEquals(new BigDecimal("27.99"), importedPerfume.getBasePrice());
                assertEquals(new BigDecimal("18.99"), perfume.getBasePrice());
                assertEquals(new BigDecimal("9.75"), pills.getBasePrice());
                assertEquals(new BigDecimal("11.25"), importedChocolate.getBasePrice());

        }

}
