package it.cannizzaro.receiptprinter.data.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Receipt
{
        private final UUID receiptId;
        private final List<Item> items;
        private BigDecimal totalCost;
        private BigDecimal totalSalesTaxes;

        public Receipt()
        {
                this.receiptId = UUID.randomUUID();
                this.items = new ArrayList<>();
        }

        public List<Item> getItems()
        {
                return items;
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

        public UUID getReceiptId()
        {
                return receiptId;
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
