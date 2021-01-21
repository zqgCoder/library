package com.example.busonline.utils;

import com.example.busonline.entity.User;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserPageUtils {
    @Getter
    @Setter
    private Integer totalData;     //一共有多少条用户记录
    @Getter
    @Setter
    private List<User> userList;
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String gender;
}
