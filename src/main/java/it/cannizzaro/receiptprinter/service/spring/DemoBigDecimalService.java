package it.cannizzaro.receiptprinter.service.spring;

import it.cannizzaro.receiptprinter.service.BigDecimalService;
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
                this.helper = new BigDecimal("2");
        }

        @Override
        public BigDecimal roundToNearestFive(BigDecimal toRound)
        {
                toRound = toRound.multiply(helper);
                toRound = toRound.setScale(1, RoundingMode.HALF_UP);
                toRound = toRound.divide(helper, 2, RoundingMode.HALF_UP);
                return toRound;
        }
}
