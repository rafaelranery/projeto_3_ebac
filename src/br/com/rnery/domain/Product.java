package br.com.rnery.domain;

import br.com.rnery.annotations.SQLColumn;
import br.com.rnery.annotations.SQLTable;

@SQLTable("tb_product")
public class Product extends Persistent {
    @SQLColumn(SQLColumn= "id", setJavaName = "setId")
    private Long id;
    @SQLColumn(SQLColumn= "name", setJavaName = "setName")
    private String name;
    @SQLColumn(SQLColumn= "description", setJavaName = "setDescription")
    private String description;
    @SQLColumn(SQLColumn= "price", setJavaName = "setPrice")
    private Double price;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
