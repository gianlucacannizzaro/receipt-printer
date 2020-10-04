package it.cannizzaro.receiptprinter.entities.domain;

import it.cannizzaro.receiptprinter.entities.business.Item;
import org.mvel2.MVEL;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Entity
@Table(name = "TAXES")
public class Tax
{
        @Transient
        private final Map<String, Object> applyExpressionContext = new HashMap<>();
        @Id
        @GeneratedValue
        private Long taxId;
        @Column(name = "NAME", nullable = false)
        private String name;
        @Column(name = "RATE", nullable = false)
        private BigDecimal rate;
        @Column(name = "APPLY_EXPRESSION", nullable = false)
        private String applyExpression;
        @ManyToMany(fetch = FetchType.EAGER)
        private List<Category> subjectedCategories;

        public Tax()
        {
        }

        public Tax(String name, BigDecimal rate, String applyExpression, List<Category> subjectedCategories)
        {
                this.name = name;
                this.rate = rate;
                this.applyExpression = applyExpression;
                this.subjectedCategories = subjectedCategories;
        }

        public Boolean isApplicableTo(Item item)
        {
                applyExpressionContext.put("item", item);
                applyExpressionContext.put("categories", subjectedCategories);
                return MVEL.evalToBoolean(applyExpression, applyExpressionContext);
        }

        public Long getTaxId()
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

        public List<Category> getSubjectedCategories()
        {
                return subjectedCategories;
        }

        public void setSubjectedCategories(List<Category> subjectedCategories)
        {
                this.subjectedCategories = subjectedCategories;
        }
}
