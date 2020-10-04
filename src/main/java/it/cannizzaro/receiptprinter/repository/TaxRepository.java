package it.cannizzaro.receiptprinter.repository;

import it.cannizzaro.receiptprinter.entities.domain.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TaxRepository extends JpaRepository<Tax, Long>
{
}
