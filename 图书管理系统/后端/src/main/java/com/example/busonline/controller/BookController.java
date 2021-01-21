package com.example.busonline.controller;

import com.example.busonline.RestfulResponse;
import com.example.busonline.entity.Book;
import com.example.busonline.service.BookService;
import com.example.busonline.utils.BookPageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;
    private RestfulResponse restfulResponse;

    @PostMapping("/add")
    public RestfulResponse addOne(@RequestBody Book book) {
        if(!book.isNull()) {
            restfulResponse = new RestfulResponse(false, 200, "重要信息不能为空", null);
            return restfulResponse;
        }
        System.out.println(book.toString());
        restfulResponse = new RestfulResponse(true, 200, "添加成功",null);
        int result = bookService.addBook(book);
        if(result == 1) restfulResponse = new RestfulResponse(true, 200, "添加成功", null);
        else restfulResponse = new RestfulResponse(false, 200, "添加失败", null);
        return restfulResponse;
    }

    @GetMapping("/delete")
    public RestfulResponse delete(@RequestParam("id") Long id) {
        if(id == null) {
            restfulResponse = new RestfulResponse(false, 200, "要删除的图书信息编号不难为空", null);
            return restfulResponse;
        }
        Book book = bookService.findById(id);
        if(book != null) {
            int result = bookService.deleteById(id);
            if(result == 1) {
                restfulResponse = new RestfulResponse(true, 200, "删除成功", null);
            } else {
                restfulResponse = new RestfulResponse(false, 200, "删除失败", null);
            }
        } else {
            // 要删除的图书信息不存在
            restfulResponse = new RestfulResponse(false, 200, "删除失败，要删除的图书信息不存在", null);
        }
        System.out.println("删除图书成功，编号为：" + id);
        return restfulResponse;
    }

    @PostMapping("/update")
    public RestfulResponse update(@RequestBody Book book) {
        // 信息不能为空
        if(book.getId() == 0 || !book.isNull()) {
            restfulResponse = new RestfulResponse(false, 200, "部分信息为空，修改失败", null);
            return restfulResponse;
        }
        // 要修改的主键不存在
        if(bookService.findById(book.getId()) == null) {
            restfulResponse = new RestfulResponse(false, 200, "要修改的主键不存在，修改失败", null);
            return restfulResponse;
        }
        // 开始修改
        int result = bookService.updateBook(book);
        if(result == 1) {
            restfulResponse = new RestfulResponse(true, 200, "修改成功", null);
        } else {
            restfulResponse = new RestfulResponse(false, 200, "修改失败", null);
        }
        System.out.println("修改成功" + book.toString());
        return restfulResponse;
    }

    // 通过类别查询，如k计算机类
    @GetMapping("/findAll")
    public RestfulResponse findAll(@RequestParam(value = "pageSize", defaultValue = "10")Long pageSize,
                                   @RequestParam(value = "currentPage", defaultValue = "1") Long currentPage,
                                   @RequestParam(value = "name", defaultValue = "")String name) {
        List<Book> list = bookService.findAll(pageSize*(currentPage-1), pageSize, name);
        Integer totalSize = bookService.findTotal(name);
        BookPageUtils bookPageUtils = new BookPageUtils(totalSize, list, name, "", "");
        if(list != null) {
            System.out.println(list.toString());
            restfulResponse = new RestfulResponse(true,200,"查询成功", bookPageUtils);
        } else {
            restfulResponse = new RestfulResponse(true,200,"查询失败，图书数据不存在", null);
        }
        System.out.println("查询成功，查询名字：" + name + ";;;;;查询结果" + list.toString());
        return restfulResponse;
    }

    // 此处返回的是list集合，和findAll方法返回值不太一样
    @GetMapping("/findName")
    public RestfulResponse findState(@RequestParam(value = "pageSize", defaultValue = "10") Long pageSize,
                                     @RequestParam(value = "currentPage", defaultValue = "1") Long currentPage,
                                     @RequestParam(value = "kind") String name) {
        if(name.equals("")) {
            restfulResponse = new RestfulResponse(false,200,"请输入想要查询的图书名", null);
            return restfulResponse;
        }
        List<Book> list = bookService.findByName(pageSize*(currentPage-1), pageSize, name);
        Integer totalSize = bookService.findTotal(name);
        BookPageUtils busInfoPageUtils = new BookPageUtils(totalSize,list,name, "", "");
        if(list != null) {
            restfulResponse = new RestfulResponse(true,200,"查询成功", busInfoPageUtils);
        } else {
            restfulResponse = new RestfulResponse(true,200,"查询失败，图书不存在", null);
        }
        return restfulResponse;
    }

    // 通过Id查询
    @PostMapping("/findone")
    public RestfulResponse findById(@RequestParam("id") Long id) {
        if(id == 0) {
            restfulResponse = new RestfulResponse(false,200,"要查询的图书不存在", null);
            return restfulResponse;
        }
        Book book = bookService.findById(id);
        if(book != null) {
            restfulResponse = new RestfulResponse(true,200,"查询成功", book);
        } else {
            restfulResponse = new RestfulResponse(false,200,"要查询的图书不存在", null);
        }
        return restfulResponse;
    }

    @GetMapping("/fuzzyFind")
    public RestfulResponse fuzzyFind(@RequestParam(value = "pageSize", defaultValue = "10")Long pageSize,
                                    @RequestParam(value = "currentPage", defaultValue = "1") Long currentPage,
                                    @RequestParam(value = "name", defaultValue = "")String name,
                                     @RequestParam(value = "kind", defaultValue = "")String kind,
                                     @RequestParam(value = "author", defaultValue = "")String author) {
        List<Book> list = bookService.fuzzyFind(pageSize*(currentPage-1), pageSize, name, kind, author);
        Integer totalSize = bookService.fuzzyFindTotal(name, kind, author);
        BookPageUtils bookPageUtils = new BookPageUtils(totalSize, list, name, kind, author);
        if(list != null) {
            System.out.println("模糊查询成功: " + list.toString());
            restfulResponse = new RestfulResponse(true,200,"查询成功", bookPageUtils);
        } else {
            restfulResponse = new RestfulResponse(true,200,"查询失败，图书数据不存在", null);
        }
        return restfulResponse;
    }
}
