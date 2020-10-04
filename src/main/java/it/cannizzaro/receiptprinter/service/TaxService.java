package it.cannizzaro.receiptprinter.service;

import it.cannizzaro.receiptprinter.entities.business.Item;
import it.cannizzaro.receiptprinter.entities.domain.Tax;

import java.math.BigDecimal;


public interface TaxService
{
        BigDecimal computeTaxes(Item item);

        Tax findByName(String name);

}
