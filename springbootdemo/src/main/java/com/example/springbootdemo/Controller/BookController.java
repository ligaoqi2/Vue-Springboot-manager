package com.example.springbootdemo.Controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootdemo.common.Result;
import com.example.springbootdemo.entity.Book;
import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.mapper.BookMapper;
import com.example.springbootdemo.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/book")
public class BookController {

    @Resource
    BookMapper bookMapper;

    // 个人信息
    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(bookMapper.selectById(id));
    }

    // 新增
    @PostMapping
    public Result<?> save(@RequestBody Book book) {
        bookMapper.insert(book);
        return Result.success();
    }

    // 删除
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        bookMapper.deleteById(id);
        return Result.success();
    }

    // 更新
    @PutMapping
    public Result<?> update(@RequestBody Book book) {
        bookMapper.updateById(book);
        return Result.success();
    }

    // 分页查询
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Book> wrapper = Wrappers.<Book>lambdaQuery().orderByAsc(Book::getId);
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Book::getName, search);
        }

        Page<Book> userPage = bookMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(userPage);
    }

}