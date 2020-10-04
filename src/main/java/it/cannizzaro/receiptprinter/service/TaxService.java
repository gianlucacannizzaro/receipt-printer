package it.cannizzaro.receiptprinter.service;

import it.cannizzaro.receiptprinter.data.entities.Item;
import it.cannizzaro.receiptprinter.data.entities.Receipt;

import java.math.BigDecimal;


public interface TaxService
{
        BigDecimal computeTaxes(Item item);
}
