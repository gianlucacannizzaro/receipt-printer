package it.cannizzaro.receiptprinter.data.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Receipt
{
        private final UUID receiptId;
        private List<Item> items;

        public Receipt()
        {
                this.receiptId = UUID.randomUUID();
                this.items = new ArrayList<>();
        }

        public List<Item> getItems()
        {
                return items;
        }

        public void setItems(List<Item> items)
        {
                this.items = items;
        }
}
