package com.example.busonline.service;

import com.example.busonline.entity.Book;

import java.util.List;

public interface BookService {
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

    Book getById(Long id);

    Integer muliOne(Long id);

    Integer addBorrowOne(Long id);
}
