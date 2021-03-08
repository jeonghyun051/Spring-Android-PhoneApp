package com.cos.phoneapp;


public class PhoneSaveReqDto {

    private String name;
    private String tel;

    @Override
    public String toString() {
        return "PhoneSaveReqDto{" +
                "name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }

    public PhoneSaveReqDto() {
    }

    public PhoneSaveReqDto(String name, String tel) {
        this.name = name;
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public String getTel() {
        return tel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
