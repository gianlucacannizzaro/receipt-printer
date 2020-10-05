package it.cannizzaro.receiptprinter.entities.business;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "RECEIPTS")
public class Receipt
{
        @OneToMany(cascade = CascadeType.ALL)
        private final List<Item> items = new ArrayList<>();
        @Id
        @GeneratedValue
        private Long receiptId;
        @Column(name = "TOTAL_COST", nullable = false)
        private BigDecimal totalCost;
        @Column(name = "TOTAL_SALES_TAXES", nullable = false)
        private BigDecimal totalSalesTaxes;

        public Receipt()
        {

        }

        public Long getReceiptId()
        {
                return receiptId;
        }

        public void setReceiptId(Long receiptId)
        {
                this.receiptId = receiptId;
        }

        public void add(Item item)
        {
                this.items.add(item);
        }

        public void remove(Item item)
        {
                this.items.remove(item);
        }

        public void contains(Item item)
        {
                this.items.contains(item);
        }

        public List<Item> getItems()
        {
                return items;
        }

        public BigDecimal getTotalCost()
        {
                return totalCost;
        }

        public void setTotalCost(BigDecimal totalCost)
        {
                this.totalCost = totalCost;
        }

        public BigDecimal getTotalSalesTaxes()
        {
                return totalSalesTaxes;
        }

        public void setTotalSalesTaxes(BigDecimal totalSalesTaxes)
        {
                this.totalSalesTaxes = totalSalesTaxes;
        }
}
