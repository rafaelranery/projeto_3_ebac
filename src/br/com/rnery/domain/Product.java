package br.com.rnery.domain;

import br.com.rnery.annotations.*;

@SQLTable("tb_product")
@SQLInsertCMD({"(NAME, DESCRIPTION, PRICE, CODE)", "(?, ?, ? , ?)"})
public class Product extends Persistent {
    @SQLColumn(SQLColumn= "id", setJavaName = "setId")
    private Long id;
    @SQLColumn(SQLColumn= "name", setJavaName = "setName")
    private String name;
    @SQLColumn(SQLColumn= "description", setJavaName = "setDescription")
    private String description;
    @SQLColumn(SQLColumn= "price", setJavaName = "setPrice")
    private Double price;
    @SQLUniqueCol
    @SQLColumn(SQLColumn = "code", setJavaName = "setCode")
    private Long code;


    @SQLUnique()
    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

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
