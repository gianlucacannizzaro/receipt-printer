package it.cannizzaro.receiptprinter.service.business.spring;

import it.cannizzaro.receiptprinter.entities.business.Item;
import it.cannizzaro.receiptprinter.entities.business.Receipt;
import it.cannizzaro.receiptprinter.entities.domain.Category;
import it.cannizzaro.receiptprinter.repository.business.ReceiptRepository;
import it.cannizzaro.receiptprinter.service.business.ReceiptService;
import it.cannizzaro.receiptprinter.service.domain.CategoryService;
import it.cannizzaro.receiptprinter.service.domain.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.StringJoiner;


@Component
public class DemoReceiptService implements ReceiptService
{
        @Autowired
        private TaxService taxService;

        @Autowired
        private CategoryService categoryService;

        @Autowired
        private ReceiptRepository receiptRepository;

        @Override
        public Receipt parse(File file) throws IOException
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

        @Override
        public void process(Receipt receipt)
        {
                BigDecimal totalSalesTaxes = BigDecimal.ZERO.setScale(2);
                BigDecimal totalCost = BigDecimal.ZERO.setScale(2);

                for (Item item : receipt.getItems())
                {
                        BigDecimal taxAmount = taxService.computeTaxes(item);
                        item.setTaxedPrice(item.getBasePrice().add(taxAmount));
                        totalSalesTaxes = totalSalesTaxes.add(taxAmount.multiply(BigDecimal.valueOf(item.getQuantity())));
                        totalCost = totalCost.add(item.getTaxedPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                }

                receipt.setTotalSalesTaxes(totalSalesTaxes);
                receipt.setTotalCost(totalCost);
        }

        @Override
        public void print(Receipt receipt, File file) throws IOException
        {
                FileOutputStream fos = new FileOutputStream(file);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

                for (Item i : receipt.getItems())
                {
                        StringJoiner sj = new StringJoiner(" ");
                        sj.add(i.getQuantity().toString()).add(i.getName()).add(":").add(i.getTaxedPrice().toPlainString());
                        bw.write(sj.toString());
                        bw.newLine();
                }

                bw.write(new StringJoiner(" ").add("Sales Taxes").add(":").add(receipt.getTotalSalesTaxes().toPlainString()).toString());
                bw.newLine();
                bw.write(new StringJoiner(" ").add("Total").add(":").add(receipt.getTotalCost().toPlainString()).toString());
                bw.newLine();

                bw.close();
        }

        @Override
        public Receipt save(Receipt receipt)
        {
                return receiptRepository.save(receipt);
        }

        @Override
        public Receipt findById(Long id)
        {
                return receiptRepository.getOne(id);
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
