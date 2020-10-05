package it.cannizzaro.receiptprinter.service.domain.spring;

import it.cannizzaro.receiptprinter.entities.business.Item;
import it.cannizzaro.receiptprinter.entities.domain.Tax;
import it.cannizzaro.receiptprinter.repository.domain.TaxRepository;
import it.cannizzaro.receiptprinter.service.util.BigDecimalService;
import it.cannizzaro.receiptprinter.service.domain.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class DemoTaxService implements TaxService
{

        @Autowired
        private TaxRepository taxRepository;

        @Autowired
        private BigDecimalService bigDecimalService;

        @Override
        public BigDecimal computeTaxes(Item item)
        {
                BigDecimal taxAmount = BigDecimal.ZERO.setScale(2);

                for (Tax tax : taxRepository.findAll())
                {
                        if (tax.isApplicableTo(item))
                        {
                                BigDecimal helper = bigDecimalService.roundUpToNearestFive(item.getBasePrice().multiply(tax.getRate()));
                                taxAmount = taxAmount.add(helper);
                        }
                }

                return taxAmount;
        }

        @Override
        public Tax findByName(String name)
        {
                Tax helper = new Tax();
                helper.setName(name);
                return taxRepository.findOne(Example.of(helper)).get();
        }
}
