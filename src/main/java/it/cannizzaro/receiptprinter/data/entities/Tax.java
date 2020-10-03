package it.cannizzaro.receiptprinter.data.entities;

import org.mvel2.MVEL;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;


public class Tax
{
        private final UUID taxId;
        private final Map<String, Object> applyExpressionContext;
        private String name;
        private BigDecimal rate;
        private String applyExpression;

        public Tax(String name, BigDecimal rate, String applyExpression, Map<String, Object> applyExpressionContext)
        {
                this.taxId = UUID.randomUUID();
                this.name = name;
                this.rate = rate;
                this.applyExpression = applyExpression;
                this.applyExpressionContext = applyExpressionContext;
        }

        public Boolean isAppliableTo(Item item)
        {
                applyExpressionContext.put("item", item);
                return MVEL.evalToBoolean(applyExpression, applyExpressionContext);
        }

        public UUID getTaxId()
        {
                return taxId;
        }

        public Map<String, Object> getApplyExpressionContext()
        {
                return applyExpressionContext;
        }

        public String getName()
        {
                return name;
        }

        public void setName(String name)
        {
                this.name = name;
        }

        public BigDecimal getRate()
        {
                return rate;
        }

        public void setRate(BigDecimal rate)
        {
                this.rate = rate;
        }

        public String getApplyExpression()
        {
                return applyExpression;
        }

        public void setApplyExpression(String applyExpression)
        {
                this.applyExpression = applyExpression;
        }
}
