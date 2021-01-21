package com.example.busonline.mapper;

import com.example.busonline.entity.Book;
import com.example.busonline.entity.Borrow;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BorrowMapper {
    // 添加
    Integer addBorrow(Borrow borrow);

    // 删除
    Integer deleteById(Long id);

    // 修改
    Integer updateBorrow(Borrow borrow);

    // 查询所有，只含有图书名
    List<Borrow> findAll(Long start, Long end, String name);

    // 查询findAll的总记录数
    Integer findAllTotal(String name);

    //
    Borrow getById(Long id);

    // 模糊查询，通过图书名，种类，作者查询
    List<Borrow> fuzzyFind(Long start, Long end, String name, Integer isback, String author, Long sid);

    Integer fuzzyFindTotal(String name, Integer isback, String author, Long sid);

    // 还书
    Integer backBookBorrow(Borrow borrow);
}
