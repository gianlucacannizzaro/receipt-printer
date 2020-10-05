package it.cannizzaro.receiptprinter.service.util.spring;

import it.cannizzaro.receiptprinter.service.util.BigDecimalService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;


@Component
public class DemoBigDecimalService implements BigDecimalService
{
        private BigDecimal helper;

        @PostConstruct
        private void init()
        {
                this.helper = new BigDecimal("20");
        }

        @Override
        public BigDecimal roundUpToNearestFive(BigDecimal toRound)
        {
                toRound = toRound.multiply(helper).setScale(0, RoundingMode.CEILING);
                toRound = toRound.divide(helper, 2, RoundingMode.HALF_UP);
                return toRound;
        }
}
