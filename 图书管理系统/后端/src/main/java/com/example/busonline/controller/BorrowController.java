package com.example.busonline.controller;

import com.example.busonline.RestfulResponse;
import com.example.busonline.entity.Book;
import com.example.busonline.entity.Borrow;
import com.example.busonline.entity.User;
import com.example.busonline.mapper.UserMapper;
import com.example.busonline.service.BookService;
import com.example.busonline.service.BorrowService;
import com.example.busonline.service.UserService;
import com.example.busonline.utils.BookPageUtils;
import com.example.busonline.utils.BorrowPageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/borrow")
public class BorrowController {
    @Autowired
    private BorrowService borrowService;
    private RestfulResponse restfulResponse;
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public RestfulResponse addOne(@RequestBody Borrow borrow) {
        System.out.println(borrow.toString());
        borrow.setIsback(0);
        borrow.setBacktime(null);
        if(borrow.getBid() == null|| borrow.getSid() == null || borrow.getBtime().equals("") || borrow.getEndtime().equals("")) {
            restfulResponse = new RestfulResponse(false, 200, "重要信息不能为空", null);
            return restfulResponse;
        }
        // 要添加的用户不存在
        User user = userService.getById(borrow.getSid().intValue());
        if(user == null) {
            System.out.println("add+" + user.toString());
            restfulResponse = new RestfulResponse(true, 200, "添加失败，用户不存在",null);
            return restfulResponse;
        }
        // 判断当前要借的图书是否可以借
        System.out.println(borrow.getBid());
        Book book = bookService.getById(borrow.getBid());
        if(book == null) {
            restfulResponse = new RestfulResponse(true, 200, "添加失败，当前借阅的图书不存在或者数量不够", null);
            return restfulResponse;
        }
        // 开始借书
        // 填充信息
        borrow.setMoney(0f);
        bookService.muliOne(borrow.getBid());
        int result = borrowService.addBorrow(borrow);
        if(result == 1) restfulResponse = new RestfulResponse(true, 200, "添加成功", null);
        else restfulResponse = new RestfulResponse(false, 200, "添加失败", null);
        return restfulResponse;
    }

    // 通过类别查询，如k计算机类
    @GetMapping("/findAll")
    public RestfulResponse findAll(@RequestParam(value = "pageSize", defaultValue = "10")Long pageSize,
                                   @RequestParam(value = "currentPage", defaultValue = "1") Long currentPage,
                                   @RequestParam(value = "name", defaultValue = "")String name) {
        List<Borrow> list = borrowService.findAll(pageSize*(currentPage-1), pageSize, name);
        Integer totalSize = borrowService.findAllTotal(name);
        BorrowPageUtils borrowPageUtils = new BorrowPageUtils(totalSize, list, name, 0,"", "");
        if(list != null) {
            restfulResponse = new RestfulResponse(true,200,"查询成功", borrowPageUtils);
        } else {
            restfulResponse = new RestfulResponse(true,200,"查询失败，数据不存在", null);
        }
        System.out.println("查询成功：" + name + ";查询结果" + list.toString());
        return restfulResponse;
    }

    @GetMapping("/delete")
    public RestfulResponse delete(@RequestParam("id") Long id) {
        if(id == null) {
            restfulResponse = new RestfulResponse(false, 200, "要删除的记录信息编号不难为空", null);
            return restfulResponse;
        }
        Borrow borrow = borrowService.getById(id);
        if(borrow != null) {
            // 删除
            int result = borrowService.deleteById(id);
            bookService.addBorrowOne(borrow.getBid());
            if(result == 1) {
                restfulResponse = new RestfulResponse(true, 200, "删除成功", null);
            } else {
                restfulResponse = new RestfulResponse(false, 200, "删除失败", null);
            }
        } else {
            // 要删除的图书信息不存在
            restfulResponse = new RestfulResponse(false, 200, "删除失败，要删除的图书信息不存在", null);
        }
        System.out.println("删除记录成功，编号为：" + id);
        return restfulResponse;
    }

    @GetMapping("/fuzzyFind")
    public RestfulResponse fuzzyFind(@RequestParam(value = "pageSize", defaultValue = "10")Long pageSize,
                                     @RequestParam(value = "currentPage", defaultValue = "1") Long currentPage,
                                     @RequestParam(value = "name", defaultValue = "")String name,
                                     @RequestParam(value = "isback", defaultValue = "")Integer isback,
                                     @RequestParam(value = "author", defaultValue = "")String author,
                                     @RequestParam(value = "userid", defaultValue = "")Long userid) {
        List<Borrow> list = borrowService.fuzzyFind(pageSize*(currentPage-1), pageSize, name, isback, author, userid);
        Integer totalSize = borrowService.fuzzyFindTotal(name, isback, author, userid);
        BorrowPageUtils borrowPageUtils = new BorrowPageUtils(totalSize,list,name,isback, author, "");
        if(list != null) {
            System.out.println("用户借阅记录模糊查询成功: " + list.toString());
            restfulResponse = new RestfulResponse(true,200,"查询成功", borrowPageUtils);
        } else {
            restfulResponse = new RestfulResponse(true,200,"查询失败，借阅数据不存在", null);
        }
        return restfulResponse;
    }

    // 还书
    @GetMapping("/backbook")
    public RestfulResponse backbook(@RequestParam(value = "bid", defaultValue = "")Long bid,
                                    @RequestParam(value = "sid", defaultValue = "")Long sid,
                                    @RequestParam(value = "id", defaultValue = "")Long id,
                                    @RequestParam(value = "name", defaultValue = "")String name,
                                    @RequestParam(value = "isback", defaultValue = "")Integer isback,
                                    @RequestParam(value = "author", defaultValue = "")String author)  {
        System.out.println("----------------进入到borrow/backbook-------------");
        if(bid == 0 || sid == 0 || id == 0) {
            restfulResponse = new RestfulResponse(false,200,"还书失败，部分信息为空", null);
            return restfulResponse;
        }
        // 要还书的用户不存在
        User user = userService.getById(sid.intValue());
        if(user == null) {
            System.out.println("backBook+ 用户不存在");
            restfulResponse = new RestfulResponse(false, 200, "还书失败，用户不存在",null);
            return restfulResponse;
        }
        // 判断当前要还的图书是否存在
        Book book = bookService.getById(bid);
        if(book == null) {
            restfulResponse = new RestfulResponse(false, 200, "还书失败，当前图书不存在", null);
            return restfulResponse;
        }
        // 判断当前借书记录是否存在
        Borrow borrow = borrowService.getById(id);
        if(borrow == null) {
            restfulResponse = new RestfulResponse(false, 200, "还书失败，当前图书记录不存在", null);
            return restfulResponse;
        }
        // 开始还书
        Date date1 = new Date();
        String strDateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        borrow.setBacktime(java.sql.Date.valueOf(sdf.format(date1)));
        borrow.setIsback(1);
        bookService.addBorrowOne(bid);  // 当前还书的可借数量+1
        int result = borrowService.backBookBorrow(borrow);
        // 把之前的数据填充上  fuzzyFindTotal(String name, Integer isback, String author, Integer userid);
        List<Borrow> list = borrowService.fuzzyFind(0L, 10L, name, isback, author, sid);
        Integer totalSize = borrowService.fuzzyFindTotal(name, isback, author, sid);
        BorrowPageUtils borrowPageUtils = new BorrowPageUtils(totalSize,list,name,isback,author, "");
        if(result == 1) restfulResponse = new RestfulResponse(true, 200, "还书成功", null);
        else restfulResponse = new RestfulResponse(false, 200, "还书失败", null);
        restfulResponse.setData(borrowPageUtils);
        return restfulResponse;
    }


    // 借书
    @GetMapping("/borrowbook")
    public RestfulResponse borrowbook(@RequestParam(value = "bid", defaultValue = "")Long bid,
                                      @RequestParam(value = "sid", defaultValue = "")Integer sid,
                                      @RequestParam(value = "name", defaultValue = "")String name,
                                      @RequestParam(value = "kind", defaultValue = "")String kind,
                                      @RequestParam(value = "author", defaultValue = "")String author) {
        if(bid == null || sid == null) {
            restfulResponse = new RestfulResponse(false,200,"借书失败，部分信息为空", null);
            return restfulResponse;
        }
        // 判断用户是否存在
        User user = userService.getById(sid);
        if(user == null) {
            restfulResponse = new RestfulResponse(false,200,"借书失败，用户不存在", null);
            return restfulResponse;
        }
        // 判断图书是否存在,并且可以借书
        Book book = bookService.getById(bid);
        if(book == null || book.getNnum() < 1) {
            restfulResponse = new RestfulResponse(false,200,"借书失败，图书不存在，或者数量不够", null);
            return restfulResponse;
        }
        // 开始借书
        Borrow borrow = new Borrow();
        borrow.setBid(bid);
        borrow.setSid(Long.valueOf(sid));
        Date date1 = new Date();
        String strDateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        borrow.setBtime(java.sql.Date.valueOf(sdf.format(date1)));
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);//设置起时间
        cal.add(Calendar.MONDAY, 1);
        Date dd = cal.getTime();
        borrow.setEndtime(new java.sql.Date(dd.getTime()));
        borrow.setMoney(0f);
        bookService.muliOne(borrow.getBid());
        int result = borrowService.addBorrow(borrow);
        if(result == 1) restfulResponse = new RestfulResponse(true, 200, "借书成功", null);
        else restfulResponse = new RestfulResponse(false, 200, "借书失败", null);
        // 把之前的数据填充上
        List<Book> list = bookService.fuzzyFind(0L, 10L, name, kind, author);
        Integer totalSize = bookService.fuzzyFindTotal(name, kind, author);
        BookPageUtils bookPageUtils = new BookPageUtils(totalSize,list,name,kind,author);
        restfulResponse.setData(bookPageUtils);
        return restfulResponse;
    }

    // 管理员查阅借书记录
    @GetMapping("/AdminFuzzyFind")
    public RestfulResponse AdminFuzzyFind(@RequestParam(value = "pageSize", defaultValue = "10")Long pageSize,
                                          @RequestParam(value = "currentPage", defaultValue = "1")Long currentPage,
                                          @RequestParam(value = "name", defaultValue = "")String name,
                                          @RequestParam(value = "isback", defaultValue = "")Integer isback,
                                          @RequestParam(value = "author", defaultValue = "")String author,
                                          @RequestParam(value = "sid", defaultValue = "0")Long sid) {

        System.out.println("---------------borrow/AdminFuzzyFind-----------");
        List<Borrow> list = borrowService.fuzzyFind(pageSize*(currentPage-1), pageSize, name, isback, author, sid);
        Integer totalSize = borrowService.fuzzyFindTotal(name, isback, author, sid);
        BorrowPageUtils borrowPageUtils = new BorrowPageUtils(totalSize,list,name,isback, author, "");
        if(list != null) {
            System.out.println("用户借阅记录模糊查询成功: " + list.toString());
            restfulResponse = new RestfulResponse(true,200,"查询成功", borrowPageUtils);
        } else {
            restfulResponse = new RestfulResponse(true,200,"查询失败，借阅数据不存在", null);
        }
        return restfulResponse;
    }
}
