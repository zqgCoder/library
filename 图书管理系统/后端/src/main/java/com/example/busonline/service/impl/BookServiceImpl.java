package com.example.busonline.service.impl;

import com.example.busonline.entity.Book;
import com.example.busonline.mapper.BookMapper;
import com.example.busonline.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("BookService")
public class BookServiceImpl implements BookService {
    @Autowired
    BookMapper bookMapper;

    @Override
    public Integer addBook(Book book) {
        return bookMapper.addBook(book);
    }

    @Override
    public Integer deleteById(Long id) {
        return bookMapper.deleteById(id);
    }

    @Override
    public Integer updateBook(Book book) {
        return bookMapper.updateBook(book);
    }

    @Override
    public List<Book> findAll(Long start, Long end, String name) {
        return bookMapper.findAll(start, end, name);
    }

    @Override
    public List<Book> findByName(Long start, Long end, String name) {
        return bookMapper.findByName(start, end, name);
    }

    @Override
    public Book findById(Long id) {
        return bookMapper.findById(id);
    }

    @Override
    public Integer findTotal(String name) {
        return bookMapper.findTotal(name);
    }

    @Override
    public List<Book> fuzzyFind(Long start, Long end, String name, String kind, String author) {
        return bookMapper.fuzzyFind(start, end, name, kind, author);
    }

    @Override
    public Integer fuzzyFindTotal(String name, String kind, String author) {
        return bookMapper.fuzzyFindTotal(name, kind, author);
    }

    @Override
    public Book getById(Long id) {
        return bookMapper.getById(id);
    }

    @Override
    public Integer muliOne(Long id) {
        return bookMapper.muliOne(id);
    }

    @Override
    public Integer addBorrowOne(Long id) {
        return bookMapper.addBorrowOne(id);
    }
}
