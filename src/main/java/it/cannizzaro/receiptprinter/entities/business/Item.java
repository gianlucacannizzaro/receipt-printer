package it.cannizzaro.receiptprinter.entities.business;

import it.cannizzaro.receiptprinter.entities.domain.Category;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name = "ITEMS")
public class Item
{
        @Id
        @GeneratedValue
        private Long itemId;
        @Column(name = "NAME", nullable = false)
        private String name;
        @OneToOne
        private Category category;
        @Column(name = "QUANTITY", nullable = false)
        private Integer quantity;
        @Column(name = "IMPORTED", nullable = false)
        private Boolean imported;
        @Column(name = "BASE_PRICE", nullable = false)
        private BigDecimal basePrice;
        @Column(name = "TAXED_PRICE", nullable = false)
        private BigDecimal taxedPrice;

        public Item()
        {

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

        public Long getItemId()
        {
                return itemId;
        }

        public void setItemId(Long itemId)
        {
                this.itemId = itemId;
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
}
