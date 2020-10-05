package it.cannizzaro.receiptprinter;

import it.cannizzaro.receiptprinter.entities.business.Item;
import it.cannizzaro.receiptprinter.entities.business.Receipt;
import it.cannizzaro.receiptprinter.entities.domain.Category;
import it.cannizzaro.receiptprinter.service.business.ReceiptService;
import it.cannizzaro.receiptprinter.service.domain.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReceiptServiceTest
{

        private Category bookCategory;
        private Category foodCategory;
        private Category medicalCategory;
        private Category otherCategory;

        private Item book;
        private Item cd;
        private Item chocolate;
        private Item importedChocolate;
        private Item importedPerfume;
        private Item anotherImportedPerfume;
        private Item perfume;
        private Item pills;
        private Item anotherImportedChocolate;

        private Receipt receiptOne;
        private Receipt receiptTwo;
        private Receipt receiptThree;

        @Autowired
        private ReceiptService parsingService;

        @Autowired
        private CategoryService categoryService;

        @BeforeEach
        void contextLoads()
        {
                bookCategory = categoryService.findByName("Book");
                foodCategory = categoryService.findByName("Food");
                medicalCategory = categoryService.findByName("Medical");
                otherCategory = categoryService.findByName("Other");

                book = new Item("book", bookCategory, false, new BigDecimal("12.49"));
                cd = new Item("cd", otherCategory, false, new BigDecimal("14.99"));
                chocolate = new Item("chocolate", foodCategory, false, new BigDecimal("0.85"));
                importedChocolate = new Item("imported box of chocolates", foodCategory, true, new BigDecimal("10.00"));
                importedPerfume = new Item("imported bottle of perfume", otherCategory, true, new BigDecimal("47.50"));

                anotherImportedPerfume = new Item("imported bottle of perfume", otherCategory, true, new BigDecimal("27.99"));
                perfume = new Item("bottle of perfume", otherCategory, false, new BigDecimal("18.99"));
                pills = new Item("packet of headache pills", medicalCategory, false, new BigDecimal("9.75"));
                anotherImportedChocolate = new Item("imported box of chocolates", foodCategory, true, new BigDecimal("11.25"));

                receiptOne = new Receipt();
                receiptOne.add(book);
                receiptOne.add(cd);
                receiptOne.add(chocolate);

                receiptTwo = new Receipt();
                receiptTwo.add(importedChocolate);
                receiptTwo.add(importedPerfume);

                receiptThree = new Receipt();
                receiptThree.add(anotherImportedPerfume);
                receiptThree.add(perfume);
                receiptThree.add(pills);
                receiptThree.add(anotherImportedChocolate);

        }

        @Test
        void test_parse_receipt_1() throws IOException
        {
                ClassLoader classLoader = getClass().getClassLoader();
                File file = new File(classLoader.getResource("receipts/in/receipt_1.txt").getFile());

                Receipt parsedReceipt = parsingService.parse(file);

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
                File file = new File(classLoader.getResource("receipts/in/receipt_2.txt").getFile());
                Receipt parsedReceipt = parsingService.parse(file);

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
        void test_parse_receipt_3() throws IOException
        {
                ClassLoader classLoader = getClass().getClassLoader();
                File file = new File(classLoader.getResource("receipts/in/receipt_3.txt").getFile());

                Receipt parsedReceipt = parsingService.parse(file);

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

        @Test
        void test_process_receipt_1()
        {
                parsingService.process(receiptOne);
                assertEquals(new BigDecimal("12.49"), book.getTaxedPrice());
                assertEquals(new BigDecimal("16.49"), cd.getTaxedPrice());
                assertEquals(new BigDecimal("0.85"), chocolate.getTaxedPrice());
                assertEquals(new BigDecimal("1.50"), receiptOne.getTotalSalesTaxes());
                assertEquals(new BigDecimal("29.83"), receiptOne.getTotalCost());

        }

        @Test
        void test_process_receipt_2()
        {

                parsingService.process(receiptTwo);

                assertEquals(new BigDecimal("10.50"), importedChocolate.getTaxedPrice());
                assertEquals(new BigDecimal("54.65"), importedPerfume.getTaxedPrice());
                assertEquals(new BigDecimal("7.65"), receiptTwo.getTotalSalesTaxes());
                assertEquals(new BigDecimal("65.15"), receiptTwo.getTotalCost());
        }

        @Test
        void test_process_receipt_3()
        {
                parsingService.process(receiptThree);

                assertEquals(new BigDecimal("32.19"), anotherImportedPerfume.getTaxedPrice());
                assertEquals(new BigDecimal("20.89"), perfume.getTaxedPrice());
                assertEquals(new BigDecimal("9.75"), pills.getTaxedPrice());
                assertEquals(new BigDecimal("11.85"), anotherImportedChocolate.getTaxedPrice());
                assertEquals(new BigDecimal("6.70"), receiptThree.getTotalSalesTaxes());
                assertEquals(new BigDecimal("74.68"), receiptThree.getTotalCost());
        }

        @Test
        void test_print_receipt_1() throws IOException
        {
                book.setTaxedPrice(new BigDecimal("12.49"));
                cd.setTaxedPrice(new BigDecimal("16.49"));
                chocolate.setTaxedPrice(new BigDecimal("0.85"));
                receiptOne.setTotalSalesTaxes(new BigDecimal("1.50"));
                receiptOne.setTotalCost(new BigDecimal("29.83"));

                ClassLoader classLoader = getClass().getClassLoader();
                File file = new File(classLoader.getResource("receipts/out").getFile() + "/receipt_1.txt");
                file.createNewFile();
                parsingService.print(receiptOne, file);

                List<String> lines = new LinkedList<>();

                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();

                while (!Objects.isNull(line))
                {
                        try
                        {
                                lines.add(line);
                        }
                        catch (Exception e)
                        {
                                continue;
                        }

                        line = br.readLine();
                }

                assertEquals(5, lines.size());
                assertEquals("1 book : 12.49", lines.get(0));
                assertEquals("1 cd : 16.49", lines.get(1));
                assertEquals("1 chocolate : 0.85", lines.get(2));
                assertEquals("Sales Taxes : 1.50", lines.get(3));
                assertEquals("Total : 29.83", lines.get(4));

        }

        @Test
        void test_print_receipt_2() throws IOException
        {
                importedChocolate.setTaxedPrice(new BigDecimal("10.50"));
                importedPerfume.setTaxedPrice(new BigDecimal("54.65"));
                receiptTwo.setTotalSalesTaxes(new BigDecimal("7.65"));
                receiptTwo.setTotalCost(new BigDecimal("65.15"));

                ClassLoader classLoader = getClass().getClassLoader();
                File file = new File(classLoader.getResource("receipts/out").getFile() + "/receipt_2.txt");
                file.createNewFile();
                parsingService.print(receiptTwo, file);

                List<String> lines = new LinkedList<>();

                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();

                while (!Objects.isNull(line))
                {
                        try
                        {
                                lines.add(line);
                        }
                        catch (Exception e)
                        {
                                continue;
                        }

                        line = br.readLine();
                }

                assertEquals(4, lines.size());
                assertEquals("1 imported box of chocolates : 10.50", lines.get(0));
                assertEquals("1 imported bottle of perfume : 54.65", lines.get(1));
                assertEquals("Sales Taxes : 7.65", lines.get(2));
                assertEquals("Total : 65.15", lines.get(3));

        }

        @Test
        void test_print_receipt_3() throws IOException
        {
                anotherImportedPerfume.setTaxedPrice(new BigDecimal("32.19"));
                perfume.setTaxedPrice(new BigDecimal("20.89"));
                pills.setTaxedPrice(new BigDecimal("9.75"));
                anotherImportedChocolate.setTaxedPrice(new BigDecimal("11.85"));
                receiptThree.setTotalSalesTaxes(new BigDecimal("6.70"));
                receiptThree.setTotalCost(new BigDecimal("74.68"));

                ClassLoader classLoader = getClass().getClassLoader();
                File file = new File(classLoader.getResource("receipts/out").getFile() + "/receipt_3.txt");
                file.createNewFile();
                parsingService.print(receiptThree, file);

                List<String> lines = new LinkedList<>();

                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();

                while (!Objects.isNull(line))
                {
                        try
                        {
                                lines.add(line);
                        }
                        catch (Exception e)
                        {
                                continue;
                        }

                        line = br.readLine();
                }

                assertEquals(6, lines.size());
                assertEquals("1 imported bottle of perfume : 32.19", lines.get(0));
                assertEquals("1 bottle of perfume : 20.89", lines.get(1));
                assertEquals("1 packet of headache pills : 9.75", lines.get(2));
                assertEquals("1 imported box of chocolates : 11.85", lines.get(3));
                assertEquals("Sales Taxes : 6.70", lines.get(4));
                assertEquals("Total : 74.68", lines.get(5));

        }

}
