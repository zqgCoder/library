package com.example.busonline.mapper;

import com.example.busonline.entity.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {
    // 添加
    Integer addBook(Book book);

    // 删除
    Integer deleteById(Long id);

    // 修改
    Integer updateBook(Book book);

    // 通过类别查询，如k计算机类
    List<Book> findAll(Long start, Long end, String name);
    // 通过书名模糊查询
    List<Book> findByName(Long start, Long end, String name);
    // 通过id查询
    Book findById(Long id);

    // 查询总记录数
    Integer findTotal(String name);

    // 模糊查询，通过图书名，种类，作者查询
    List<Book> fuzzyFind(Long start, Long end, String name, String kind, String author);

    // 专门配置fuzzyFind()函数用来查询记录数
    Integer fuzzyFindTotal(String name, String kind, String author);

    // 通过id查询图书，用来判断图书是否可以借阅
    Book getById(Long id);

    // 借书用，减一
    Integer muliOne(Long id);

    // 还书用， 加一
    Integer addBorrowOne(Long id);
}
