package br.com.rnery.domain;

import br.com.rnery.annotations.SQLColumn;
import br.com.rnery.annotations.SQLTable;

import java.sql.Date;

@SQLTable("tb_sale")
public class Sale extends Persistent {
    @SQLColumn(SQLColumn= "id", setJavaName = "setId")
    private Long id;
    @SQLColumn(SQLColumn= "code", setJavaName = "setCode")
    private String code;
    @SQLColumn(SQLColumn= "id_client", setJavaName = "setClient")
    private Client client;
    @SQLColumn(SQLColumn= "total_value", setJavaName = "setTotalValue")
    private Double totalValue;
    @SQLColumn(SQLColumn= "sale_date", setJavaName = "setSaleDate")
    private Date saleDate;
    @SQLColumn(SQLColumn= "sale_status", setJavaName = "setSaleStatus")
    private String saleStatus;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }
}
