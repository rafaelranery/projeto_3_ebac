package br.com.rnery.domain;

import br.com.rnery.annotations.*;

@SQLTable("tb_client")
@SQLInsertCMD({"(NAME, CPF, TEL, ADDRESS, ADDRESS_NUM, CITY, STATE)", "(?, ?, ?, ?, ?, ?, ?)"})
public class Client extends Persistent {
    @SQLColumn(SQLColumn = "id", setJavaName = "setId")
    private Long id;
    @SQLColumn(SQLColumn= "name", setJavaName = "setName")
    private String name;
    @SQLUniqueCol
    @SQLColumn(SQLColumn= "cpf", setJavaName = "setCpf")
    private Long cpf;
    @SQLColumn(SQLColumn= "tel", setJavaName = "setTel")
    private Long tel;
    @SQLColumn(SQLColumn= "address", setJavaName = "setAddress")
    private String address;
    @SQLColumn(SQLColumn= "address_num", setJavaName = "setAddressNum")
    private Long addressNum;
    @SQLColumn(SQLColumn= "city", setJavaName = "setCity")
    private String city;
    @SQLColumn(SQLColumn= "state", setJavaName = "setState")
    private String state;

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


    @SQLUnique()
    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public Long getTel() {
        return tel;
    }

    public void setTel(Long tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getAddressNum() {
        return addressNum;
    }

    public void setAddressNum(Long addressNum) {
        this.addressNum = addressNum;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
