package it.cannizzaro.receiptprinter.service.util;

import java.math.BigDecimal;


public interface BigDecimalService
{
        BigDecimal roundUpToNearestFive(BigDecimal toRound);
}
