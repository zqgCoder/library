package com.example.busonline.utils;

import com.example.busonline.entity.Borrow;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BorrowPageUtils {
    @Getter
    @Setter
    private Integer totalData;
    @Getter
    @Setter
    private List<Borrow> borrowList;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private Integer isback;
    @Getter
    @Setter
    private String author;
    @Getter
    @Setter
    private String stat;      // 返回状态码，用于判断是否正确执行操作
}
    // totalData borrowList name isback author stat