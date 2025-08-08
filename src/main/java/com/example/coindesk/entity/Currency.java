package com.example.coindesk.entity;

import javax.persistence.*;

@Entity
@Table(name = "currencies")
public class Currency {

    @Id
    @Column(length = 10)
    private String code; // e.g. "USD", "TWD"

    @Column(nullable = false)
    private String nameZh; // 中文名稱

    public Currency() {}

    public Currency(String code, String nameZh) {
        this.code = code;
        this.nameZh = nameZh;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getNameZh() { return nameZh; }
    public void setNameZh(String nameZh) { this.nameZh = nameZh; }
}