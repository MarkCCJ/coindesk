package com.example.coindesk.dto;

public class CurrencyDTO {
    private String code;
    private String nameZh;

    public CurrencyDTO() {}
    public CurrencyDTO(String code, String nameZh){
        this.code = code;
        this.nameZh = nameZh;
    }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getNameZh() { return nameZh; }
    public void setNameZh(String nameZh) { this.nameZh = nameZh; }
}