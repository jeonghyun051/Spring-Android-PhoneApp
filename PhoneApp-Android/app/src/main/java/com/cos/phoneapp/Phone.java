package com.cos.phoneapp;

public class Phone {
    private Long id;
    private String name;
    private String tel;

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }

    public Phone() {
    }

    public Phone(Long id, String name, String tel) {
        this.id = id;
        this.name = name;
        this.tel = tel;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTel() {
        return tel;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
