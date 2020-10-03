package it.cannizzaro.receiptprinter.service.spring;

import it.cannizzaro.receiptprinter.data.entities.Receipt;
import it.cannizzaro.receiptprinter.service.TaxService;
import org.springframework.stereotype.Component;


@Component
public class DemoTaxService implements TaxService
{
        @Override
        public void computeTaxes(Receipt receipt)
        {

        }
}
