package it.cannizzaro.receiptprinter.service;

import java.math.BigDecimal;


public interface BigDecimalService
{
        BigDecimal roundToNearestFive(BigDecimal toRound);
}
