package it.cannizzaro.receiptprinter.service.spring;

import it.cannizzaro.receiptprinter.data.entities.Item;
import it.cannizzaro.receiptprinter.data.entities.Receipt;
import it.cannizzaro.receiptprinter.data.entities.Tax;
import it.cannizzaro.receiptprinter.service.ReceiptService;
import it.cannizzaro.receiptprinter.service.TaxService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;


@Component
public class DemoTaxService implements TaxService
{
        private List<Tax> taxes;

        @Override
        public BigDecimal computeTaxes(Item item)
        {
                return null;
        }



}
