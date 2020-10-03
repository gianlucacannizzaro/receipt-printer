package it.cannizzaro.receiptprinter.data.entities;

import java.util.UUID;


public class Category
{
        private final UUID categoryId;
        private String name;

        public Category(String name)
        {
                this.categoryId = UUID.randomUUID();
                this.name = name;
        }

        public UUID getCategoryId()
        {
                return categoryId;
        }

        public String getName()
        {
                return name;
        }

        public void setName(String name)
        {
                this.name = name;
        }

}
