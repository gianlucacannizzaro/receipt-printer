package it.cannizzaro.receiptprinter.service;

import it.cannizzaro.receiptprinter.data.entities.Receipt;


public interface ReceiptService
{
        void process(Receipt receipt);
}
