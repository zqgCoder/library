package com.example.busonline.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Book {
    @Setter
    @Getter
    private long id;
    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private String kind;
    @Setter
    @Getter
    private String publish;
    @Setter
    @Getter
    private String author;
    @Setter
    @Getter
    private float price;
    @Setter
    @Getter
    private int num;            // 总数
    @Setter
    @Getter
    private int nnum;           // 当前可借的数目
    @Setter
    @Getter
    private String description;    // 描述信息

    public boolean isNull() {
        if(name.equals("") || kind.equals("") || publish.equals("") || author.equals("") || price < 0 || num < 0 || nnum < 0 || description.equals("")) {
            return false;
        }
        return true;
    }
}
    // id name kind publish author price num nnum description