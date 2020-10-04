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
        private BigDecimal onePointZeroFive;
        private BigDecimal onePointOne;
        private BigDecimal minusOne;
        private BigDecimal minusOnePointFive;
        private BigDecimal minusOnePointOne;

        @BeforeAll
        void setUp()
        {
                one = BigDecimal.ONE.setScale(2);
                onePointZeroFive = new BigDecimal("1.05").setScale(2);
                onePointOne = new BigDecimal("1.1").setScale(2);
                minusOne = one.negate();
                minusOnePointFive = onePointZeroFive.negate();
                minusOnePointOne = onePointOne.negate();

        }

        @Test
        void test_round_big_decimal()
        {
                assertEquals(one, bigDecimalService.roundUpToNearestFive(one));
                assertEquals(onePointZeroFive, bigDecimalService.roundUpToNearestFive(new BigDecimal("1.01")));
                assertEquals(onePointZeroFive, bigDecimalService.roundUpToNearestFive(new BigDecimal("1.02")));
                assertEquals(onePointZeroFive, bigDecimalService.roundUpToNearestFive(new BigDecimal("1.024")));
                assertEquals(onePointOne, bigDecimalService.roundUpToNearestFive(new BigDecimal("1.06")));
                assertEquals(onePointOne, bigDecimalService.roundUpToNearestFive(new BigDecimal("1.062")));
                assertEquals(onePointOne, bigDecimalService.roundUpToNearestFive(new BigDecimal("1.069")));

                assertEquals(minusOne, bigDecimalService.roundUpToNearestFive(minusOne));
                assertEquals(minusOne, bigDecimalService.roundUpToNearestFive(new BigDecimal("-1.02")));
                assertEquals(minusOne, bigDecimalService.roundUpToNearestFive(new BigDecimal("-1.024")));
                assertEquals(minusOne, bigDecimalService.roundUpToNearestFive(new BigDecimal("-1.0245")));
                assertEquals(minusOne, bigDecimalService.roundUpToNearestFive(new BigDecimal("-1.025")));
                assertEquals(minusOne, bigDecimalService.roundUpToNearestFive(new BigDecimal("-1.026")));
                assertEquals(minusOnePointFive, bigDecimalService.roundUpToNearestFive(new BigDecimal("-1.051")));
                assertEquals(minusOnePointOne, bigDecimalService.roundUpToNearestFive(new BigDecimal("-1.101")));

        }

}
