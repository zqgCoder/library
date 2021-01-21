package com.example.busonline.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Getter
    @Setter
    private Integer id;
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private String gender;
    @Getter
    @Setter
    private String phone;
    @Getter
    @Setter
    private String address;
    @Getter
    @Setter
    private String description;
}
    // id username password gender phone address description