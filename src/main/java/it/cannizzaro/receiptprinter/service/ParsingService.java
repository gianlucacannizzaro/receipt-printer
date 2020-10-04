package it.cannizzaro.receiptprinter.service;

import it.cannizzaro.receiptprinter.entities.business.Receipt;

import java.io.File;
import java.io.IOException;


public interface ParsingService
{
        Receipt parseReceipt(File file) throws IOException;
}
