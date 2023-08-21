package br.com.rnery.domain;

public class Client extends Persistent {
    private Long id;
    private String name;
    private Long cpf;
    private Long tel;
    private String address;
    private String address_num;
    private String city;
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

    public String getAddress_num() {
        return address_num;
    }

    public void setAddress_num(String address_num) {
        this.address_num = address_num;
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
