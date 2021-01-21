package com.example.busonline.utils;

import com.example.busonline.entity.Book;
import com.example.busonline.entity.Borrow;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookPageUtils {
    @Getter
    @Setter
    private Integer totalData;
    @Getter
    @Setter
    private List<Book> bookList;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String kind;
    @Getter
    @Setter
    private String author;
}
