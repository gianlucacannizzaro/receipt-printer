package it.cannizzaro.receiptprinter.service.spring;

import it.cannizzaro.receiptprinter.data.entities.Receipt;
import it.cannizzaro.receiptprinter.service.ReceiptService;
import org.springframework.stereotype.Component;


@Component
public class DemoReceiptService implements ReceiptService
{
        @Override
        public void process(Receipt receipt)
        {

        }
}
