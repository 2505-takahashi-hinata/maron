package com.example.maron.controller;

import com.example.maron.controller.form.UserForm;
import com.example.maron.service.BranchService;
import com.example.maron.service.DepartmentService;
import com.example.maron.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserManageController {
    @Autowired
    UserService userService;

    @Autowired
    BranchService branchService;

    @Autowired
    DepartmentService departmentService;

    //ユーザー管理画面遷移処理
    @GetMapping("/userManage")
    public ModelAndView userManagement() throws ParseException {
        ModelAndView mav = new ModelAndView();
        // form用の空のentityを準備
        List<UserForm> userData = userService.findAllUser();
        // 画面遷移先を指定
        mav.setViewName("/userManage");
        // 準備した空のFormを保管
        mav.addObject("changeStatus", changeStatus());
        mav.addObject("users", userData);
        return mav;
    }

    private Map<Short, String> changeStatus() {
        Map<Short, String> map = new LinkedHashMap<>();
        map.put((short) 1, "有効");
        map.put((short) 2, "無効");
        return map;
    }

    //ユーザーの復活・停止ステータス
    @PutMapping("/changeStatus/{id}")
    public ModelAndView changeStatus (@PathVariable Integer id, @RequestParam(name = "status", required = false) Short status) {
        // 編集した投稿を更新
        userService.changeStatus(id, status);
        return new ModelAndView("redirect:/");
    }
}
