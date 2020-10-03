package it.cannizzaro.receiptprinter.service;

import it.cannizzaro.receiptprinter.data.entities.Receipt;


public interface TaxService
{
        void computeTaxes(Receipt receipt);
}
