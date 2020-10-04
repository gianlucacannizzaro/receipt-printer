package it.cannizzaro.receiptprinter.service;

import it.cannizzaro.receiptprinter.entities.business.Item;
import it.cannizzaro.receiptprinter.entities.domain.Category;

import java.math.BigDecimal;


public interface CategoryService
{
       Category findByName(String name);

}
