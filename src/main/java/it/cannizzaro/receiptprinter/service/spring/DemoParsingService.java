package it.cannizzaro.receiptprinter.service.spring;

import it.cannizzaro.receiptprinter.entities.business.Item;
import it.cannizzaro.receiptprinter.entities.business.Receipt;
import it.cannizzaro.receiptprinter.entities.domain.Category;
import it.cannizzaro.receiptprinter.service.CategoryService;
import it.cannizzaro.receiptprinter.service.ParsingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.StringJoiner;


@Component
public class DemoParsingService implements ParsingService
{
        @Autowired
        private CategoryService categoryService;

        @Override
        public Receipt parseReceipt(File file) throws IOException
        {
                Receipt parsed = new Receipt();

                BufferedReader br = new BufferedReader(new FileReader(file));

                String line = br.readLine();

                while (!Objects.isNull(line))
                {

                        try
                        {
                                Item parsedItem = new Item();

                                parsedItem.setCategory(parseCategory(line));
                                parsedItem.setImported(parseImported(line));

                                String[] words = line.split("\\s+");

                                parsedItem.setQuantity(parseQuantity(words[0]));
                                parsedItem.setBasePrice(parseBasePrice(words[words.length - 1]));
                                parsedItem.setName(parseName(words));

                                parsed.getItems().add(parsedItem);
                        }
                        catch (Exception e)
                        {
                                continue;
                        }

                        line = br.readLine();
                }

                return parsed;
        }

        private String parseName(String[] words)
        {
                StringJoiner sj = new StringJoiner(" ");

                for (int i = 1; i < words.length - 2; i++)
                {
                        if (!"imported".equalsIgnoreCase(words[i]))

                                sj.add(words[i]);
                }

                return sj.toString();
        }

        private Integer parseQuantity(String input)
        {
                return Integer.valueOf(input);
        }

        private Category parseCategory(String input)
        {
                input = input.toLowerCase();

                for (Category c : categoryService.list())
                {
                        for (String k : c.getKeywords())
                        {
                                if (input.contains(k))
                                {
                                        return c;
                                }
                        }
                }

                return null;
        }

        private Boolean parseImported(String input)
        {
                return input.contains("imported");
        }

        private BigDecimal parseBasePrice(String input)
        {
                return new BigDecimal(input).setScale(2);
        }

}
