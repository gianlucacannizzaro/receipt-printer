package it.cannizzaro.receiptprinter.service.spring;

import it.cannizzaro.receiptprinter.entities.domain.Category;
import it.cannizzaro.receiptprinter.entities.business.Item;
import it.cannizzaro.receiptprinter.entities.business.Receipt;
import it.cannizzaro.receiptprinter.service.ParsingService;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;


@Component
public class DemoParsingService implements ParsingService
{
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
                                parsedItem.setName(parseName(line));
                                parsedItem.setQuantity(parseQuantity(line));
                                parsedItem.setCategory(parseCategory(line));
                                parsedItem.setImported(parseImported(line));
                                parsedItem.setBasePrice(parseBasePrice(line));
                                parsed.getItems().add(parsedItem);
                        }
                        catch (Exception e)
                        {
                                continue;
                        }

                        line = br.readLine();
                }

                return null;
        }

        private String parseName(String input)
        {
                return null;
        }

        private Integer parseQuantity(String input)
        {
                return null;
        }

        private Category parseCategory(String input)
        {
                return null;
        }

        private Boolean parseImported(String input)
        {
                return null;
        }

        private BigDecimal parseBasePrice(String input)
        {
                return null;
        }

}
