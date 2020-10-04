package it.cannizzaro.receiptprinter.entities.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "CATEGORIES")
public class Category
{
        @Id
        @GeneratedValue
        private Long categoryId;
        @Column(name = "NAME", nullable = false)
        private String name;
        @ElementCollection(fetch = FetchType.EAGER)
        private List<String> keywords;

        public Category()
        {
        }

        public Category(String name)
        {
                this.name = name;
        }

        public Long getCategoryId()
        {
                return categoryId;
        }

        public String getName()
        {
                return name;
        }

        public void setName(String name)
        {
                this.name = name;
        }

        public List<String> getKeywords()
        {
                return keywords;
        }

        public void setKeywords(List<String> keywords)
        {
                this.keywords = keywords;
        }

        @Override
        public boolean equals(Object o)
        {
                if (this == o)
                        return true;
                if (!(o instanceof Category))
                        return false;
                Category category = (Category) o;
                return categoryId.equals(category.categoryId) && name.equals(category.name);
        }

        @Override
        public int hashCode()
        {
                return Objects.hash(categoryId, name);
        }

}
