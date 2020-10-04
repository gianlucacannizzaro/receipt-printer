package it.cannizzaro.receiptprinter.service.spring;

import it.cannizzaro.receiptprinter.data.entities.Item;
import it.cannizzaro.receiptprinter.data.entities.Tax;
import it.cannizzaro.receiptprinter.service.BigDecimalService;
import it.cannizzaro.receiptprinter.service.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;


@Component
public class DemoTaxService implements TaxService
{
        private List<Tax> taxes;

        @Autowired
        private BigDecimalService bigDecimalService;

        @Override
        public BigDecimal computeTaxes(Item item)
        {
                BigDecimal taxAmount = BigDecimal.ZERO.setScale(2);

                for (Tax tax : taxes)
                {
                        if (tax.isAppliableTo(item))
                        {
                                BigDecimal helper = bigDecimalService.roundUpToNearestFive(item.getBasePrice().multiply(tax.getRate()));
                                taxAmount = taxAmount.add(helper);
                        }
                }

                return taxAmount;
        }

        public void setTaxes(List<Tax> taxes)
        {
                this.taxes = taxes;
        }
}
