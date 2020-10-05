package it.cannizzaro.receiptprinter.repository.domain;

import it.cannizzaro.receiptprinter.entities.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>
{
}
