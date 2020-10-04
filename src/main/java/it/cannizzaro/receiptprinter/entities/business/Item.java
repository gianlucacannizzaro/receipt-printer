package it.cannizzaro.receiptprinter.entities.business;

import it.cannizzaro.receiptprinter.entities.domain.Category;

import java.math.BigDecimal;
import java.util.UUID;


public class Item
{
        private final UUID itemId;
        private String name;
        private Category category;
        private Integer quantity;
        private Boolean imported;
        private BigDecimal basePrice;
        private BigDecimal taxedPrice;

        public Item()
        {
                this.itemId = UUID.randomUUID();
        }

        public Item(String name, Category category, Boolean imported)
        {
                this();
                this.name = name;
                this.category = category;
                this.quantity = 1;
                this.imported = imported;
        }

        public Item(String name, Category category, Boolean imported, BigDecimal basePrice)
        {
                this(name, category, imported);
                this.basePrice = basePrice;
        }

        public UUID getItemId()
        {
                return itemId;
        }

        public String getName()
        {
                return name;
        }

        public void setName(String name)
        {
                this.name = name;
        }

        public Category getCategory()
        {
                return category;
        }

        public void setCategory(Category category)
        {
                this.category = category;
        }

        public BigDecimal getBasePrice()
        {
                return basePrice;
        }

        public void setBasePrice(BigDecimal basePrice)
        {
                this.basePrice = basePrice;
        }

        public BigDecimal getTaxedPrice()
        {
                return taxedPrice;
        }

        public void setTaxedPrice(BigDecimal taxedPrice)
        {
                this.taxedPrice = taxedPrice;
        }

        public Integer getQuantity()
        {
                return quantity;
        }

        public void setQuantity(Integer quantity)
        {
                this.quantity = quantity;
        }

        public Boolean getImported()
        {
                return imported;
        }

        public void setImported(Boolean imported)
        {
                this.imported = imported;
        }
}
