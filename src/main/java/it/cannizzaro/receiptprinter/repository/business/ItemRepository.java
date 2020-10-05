package it.cannizzaro.receiptprinter.repository.business;

import it.cannizzaro.receiptprinter.entities.business.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ItemRepository extends JpaRepository<Item, Long>
{
}
