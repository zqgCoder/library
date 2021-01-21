package com.example.busonline.entity;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Admin {
    @Setter
    @Getter
    private Integer id;
    @Setter
    @Getter
    private String password;
    @Setter
    @Getter
    private String username;
}
