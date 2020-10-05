package it.cannizzaro.receiptprinter.service.domain.spring;

import it.cannizzaro.receiptprinter.entities.domain.Category;
import it.cannizzaro.receiptprinter.repository.domain.CategoryRepository;
import it.cannizzaro.receiptprinter.service.domain.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DemoCategoryService implements CategoryService
{
        @Autowired
        private CategoryRepository categoryRepository;

        @Override
        public Category findByName(String name)
        {
                Category helper = new Category();
                helper.setName(name);
                return categoryRepository.findOne(Example.of(helper)).get();
        }

        @Override
        public List<Category> list()
        {
                return categoryRepository.findAll();
        }
}
