package com.zbbmeta.controller;

import com.zbbmeta.entity.Student;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;


/**
 * @author springboot葵花宝典
 * @description: TODO
 */
@RestController
@RequestMapping("/student")
//@Validated //开启校验功能
public class StudentController {

    //简单数据类型校验
    @RequestMapping("/delete")
    public String delete(@NotBlank(message = "id不能为空") String id){
        System.out.println("delete..." + id);
        return "OK";
    }

    //对象属性校验
    @RequestMapping("/save")
    public String save(@Validated Student student){
        System.out.println("save..." + student);
        return "OK";
    }
}
