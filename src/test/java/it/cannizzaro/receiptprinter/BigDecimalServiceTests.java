package it.cannizzaro.receiptprinter;

import it.cannizzaro.receiptprinter.service.BigDecimalService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BigDecimalServiceTests
{
        @Autowired
        private BigDecimalService bigDecimalService;

        private BigDecimal one;
        private BigDecimal onePointFive;
        private BigDecimal minusOne;
        private BigDecimal minusOnePointFive;

        @BeforeAll
        void setUp()
        {
                one = BigDecimal.ONE.setScale(2);
                onePointFive = new BigDecimal("1.05").setScale(2);
                minusOne = one.negate();
                minusOnePointFive = onePointFive.negate();
        }

        @Test
        void test_round_big_decimal()
        {
                assertEquals(one, bigDecimalService.roundToNearestFive(one));
                assertEquals(one, bigDecimalService.roundToNearestFive(new BigDecimal("1.01")));
                assertEquals(one, bigDecimalService.roundToNearestFive(new BigDecimal("1.02")));
                assertEquals(one, bigDecimalService.roundToNearestFive(new BigDecimal("1.024")));
                assertEquals(onePointFive, bigDecimalService.roundToNearestFive(new BigDecimal("1.025")));
                assertEquals(onePointFive, bigDecimalService.roundToNearestFive(new BigDecimal("1.026")));
                assertEquals(onePointFive, bigDecimalService.roundToNearestFive(new BigDecimal("1.049")));

                assertEquals(minusOne, bigDecimalService.roundToNearestFive(minusOne));
                assertEquals(minusOne, bigDecimalService.roundToNearestFive(new BigDecimal("-1.01")));
                assertEquals(minusOne, bigDecimalService.roundToNearestFive(new BigDecimal("-1.02")));
                assertEquals(minusOne, bigDecimalService.roundToNearestFive(new BigDecimal("-1.024")));
                assertEquals(minusOne, bigDecimalService.roundToNearestFive(new BigDecimal("-1.0245")));
                assertEquals(minusOnePointFive, bigDecimalService.roundToNearestFive(new BigDecimal("-1.025")));
                assertEquals(minusOnePointFive, bigDecimalService.roundToNearestFive(new BigDecimal("-1.026")));
                assertEquals(minusOnePointFive, bigDecimalService.roundToNearestFive(new BigDecimal("-1.049")));

        }

}
