package com.example.busonline.service.impl;

import com.example.busonline.entity.Borrow;
import com.example.busonline.mapper.BorrowMapper;
import com.example.busonline.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("BorrowService")
public class BorrowServiceImpl implements BorrowService {
    @Autowired
    private BorrowMapper borrowMapper;

    @Override
    public Integer addBorrow(Borrow borrow) {
        return borrowMapper.addBorrow(borrow);
    }

    @Override
    public Integer deleteById(Long id) {
        return borrowMapper.deleteById(id);
    }

    @Override
    public Integer updateBorrow(Borrow borrow) {
        return borrowMapper.updateBorrow(borrow);
    }

    @Override
    public List<Borrow> findAll(Long start, Long end, String name) {
        return borrowMapper.findAll(start, end, name);
    }

    @Override
    public Integer findAllTotal(String name) {
        return borrowMapper.findAllTotal(name);
    }

    @Override
    public Borrow getById(Long id) {
        return borrowMapper.getById(id);
    }

    @Override
    public List<Borrow> fuzzyFind(Long start, Long end, String name, Integer isback, String author, Long userid) {
        return borrowMapper.fuzzyFind(start, end, name, isback, author, userid);
    }

    @Override
    public Integer fuzzyFindTotal(String name, Integer isback, String author, Long sid) {
        return borrowMapper.fuzzyFindTotal(name, isback, author, sid);
    }

    @Override
    public Integer backBookBorrow(Borrow borrow) {
        return borrowMapper.backBookBorrow(borrow);
    }
}
