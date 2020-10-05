package it.cannizzaro.receiptprinter.service.business;

import it.cannizzaro.receiptprinter.entities.business.Receipt;

import java.io.File;
import java.io.IOException;


public interface ReceiptService
{
        Receipt parse(File file) throws IOException;

        void process(Receipt receipt);

        void print(Receipt receipt, File file) throws IOException;

        Receipt save(Receipt receipt);

        Receipt findById(Long id);

}
