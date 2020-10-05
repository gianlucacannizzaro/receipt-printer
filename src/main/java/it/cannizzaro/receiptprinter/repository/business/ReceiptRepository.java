package it.cannizzaro.receiptprinter.repository.business;

import it.cannizzaro.receiptprinter.entities.business.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long>
{
}
