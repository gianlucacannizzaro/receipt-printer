package it.cannizzaro.receiptprinter.service.domain;

import it.cannizzaro.receiptprinter.entities.domain.Category;

import java.util.List;


public interface CategoryService
{
        Category findByName(String name);

        List<Category> list();
}
