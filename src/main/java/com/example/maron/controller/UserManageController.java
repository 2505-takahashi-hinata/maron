package com.example.maron.controller;

import com.example.maron.controller.form.UserForm;
import com.example.maron.dto.userManage;
import com.example.maron.service.BranchService;
import com.example.maron.service.DepartmentService;
import com.example.maron.service.UserService;
import jakarta.servlet.http.HttpSession;
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

    @Autowired
    HttpSession session;

    //ユーザー管理画面遷移処理
    @GetMapping("/user")
    public ModelAndView userManagement() throws ParseException {
        ModelAndView mav = new ModelAndView();
        // form用の空のentityを準備
        List<userManage> userData = userService.findAllUser();
        UserForm loginUser = (UserForm) session.getAttribute("loginUser");
        int loginUserId = loginUser.getId();
        //エラーをsessionから取得
        List<String> errors = (List<String>) session.getAttribute("errors");
        if (errors != null) {
            mav.addObject("errors", errors);
            session.removeAttribute("errors");
        }
            // 画面遷移先を指定
            mav.setViewName("/user");
            // 準備した空のFormを保管
            mav.addObject("loginUser", loginUserId);
            mav.addObject("changeStatus", changeStatus());
            mav.addObject("users", userData);
            return mav;
        }

        private Map<Short, String> changeStatus () {
            Map<Short, String> map = new LinkedHashMap<>();
            map.put((short) 0, "稼働");
            map.put((short) 1, "停止");
            return map;
        }

        //ユーザーの復活・停止ステータス
        @PutMapping("/updateStatus/{id}")
        public ModelAndView updateStatus (@PathVariable Integer
        id, @RequestParam(name = "status", required = false) Short status){
            // 編集した投稿を更新
            userService.changeStatus(id, status);
            return new ModelAndView("redirect:/user");
        }
}
