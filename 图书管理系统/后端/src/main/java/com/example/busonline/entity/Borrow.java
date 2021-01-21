package com.example.busonline.entity;


import lombok.*;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Borrow {
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private Long sid;
    @Getter
    @Setter
    private Long bid;
    @Getter
    @Setter
    private Date btime;
    @Getter
    @Setter
    private Date endtime;
    @Getter
    @Setter
    private Float money;
    @Getter
    @Setter
    private Integer isback;     // 是否归还，1归还，
    @Getter
    @Setter
    private Date backtime;     // 归还时间
    // 下面的字段为book的字段
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
    // 下面为user的字段
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String gender;

    public boolean isNull() {
        if(id == 0 || sid == 0 || bid == 0 || btime.equals("") || endtime.equals("") || money == 0) {
            return false;
        }
        return true;
    }
}
    // id sid bid btime endtime money username gender