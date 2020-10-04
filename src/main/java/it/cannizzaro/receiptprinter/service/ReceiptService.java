package it.cannizzaro.receiptprinter.service;

import it.cannizzaro.receiptprinter.entities.business.Receipt;


public interface ReceiptService
{
        void process(Receipt receipt);
}
