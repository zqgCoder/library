package com.example.busonline.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Permission {
    @Getter
    @Setter
    private String path;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String label;
    @Getter
    @Setter
    private String icon;
    @Getter
    @Setter
    private String url;
}
