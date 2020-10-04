package it.cannizzaro.receiptprinter.service;

import it.cannizzaro.receiptprinter.data.entities.Item;
import it.cannizzaro.receiptprinter.data.entities.Tax;

import java.math.BigDecimal;
import java.util.List;


public interface TaxService
{
        BigDecimal computeTaxes(Item item);

        void setTaxes(List<Tax> taxes);
}
