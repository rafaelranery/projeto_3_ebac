package br.com.rnery.domain;

import br.com.rnery.annotations.SQLColumn;
import br.com.rnery.annotations.SQLTable;

@SQLTable("tb_product_quantity")
public class ProductQuantity extends Persistent {
    @SQLColumn(SQLColumn= "id", setJavaName = "setId")
    private Long id;
    @SQLColumn(SQLColumn= "id_product", setJavaName = "setProduct")
    private Product product;
    @SQLColumn(SQLColumn= "id_sale", setJavaName = "setSale")
    private Sale sale;
    @SQLColumn(SQLColumn= "quantity", setJavaName = "setQuantity")
    private Integer quantity;
    @SQLColumn(SQLColumn= "total_value", setJavaName = "setTotalValue")
    private Double totalValue;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotal_value(Double totalValue) {
        this.totalValue = totalValue;
    }
}
