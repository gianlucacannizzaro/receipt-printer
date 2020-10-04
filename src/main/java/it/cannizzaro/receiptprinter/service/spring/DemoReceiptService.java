package it.cannizzaro.receiptprinter.service.spring;

import it.cannizzaro.receiptprinter.data.entities.Item;
import it.cannizzaro.receiptprinter.data.entities.Receipt;
import it.cannizzaro.receiptprinter.service.ReceiptService;
import it.cannizzaro.receiptprinter.service.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class DemoReceiptService implements ReceiptService
{
        @Autowired
        private TaxService taxService;

        @Override
        public void process(Receipt receipt)
        {
                BigDecimal totalSalesTaxes = BigDecimal.ZERO.setScale(2);
                BigDecimal totalCost = BigDecimal.ZERO.setScale(2);

                for (Item item : receipt.getItems())
                {
                        BigDecimal taxAmount = taxService.computeTaxes(item);
                        item.setTaxedPrice(item.getBasePrice().add(taxAmount));
                        totalSalesTaxes = totalSalesTaxes.add(taxAmount);
                        totalCost = totalCost.add(item.getTaxedPrice());
                }

                receipt.setTotalSalesTaxes(totalSalesTaxes);
                receipt.setTotalCost(totalCost);
        }
}
