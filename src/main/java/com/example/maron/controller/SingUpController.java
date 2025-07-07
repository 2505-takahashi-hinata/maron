package com.example.maron.controller;

import com.example.maron.controller.form.BranchForm;
import com.example.maron.controller.form.DepartmentForm;
import com.example.maron.controller.form.UserForm;
import com.example.maron.service.BranchService;
import com.example.maron.service.DepartmentService;
import com.example.maron.service.UserService;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SingUpController {
    @Autowired
    UserService userService;

    @Autowired
    BranchService branchService;

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/SignUp")
    public ModelAndView newContent() throws ParseException {
        ModelAndView mav = new ModelAndView();
        // form用の空のentityを準備
        UserForm userForm = new UserForm();
        List<BranchForm> branchData = branchService.findAllBranch();
        List<DepartmentForm> departmentData = departmentService.findAllDepartment();
        // 画面遷移先を指定
        mav.setViewName("/signUp");
        // 準備した空のFormを保管
        mav.addObject("user", userForm);
        mav.addObject("branch", branchData);
        mav.addObject("department", departmentData);
        return mav;
    }

    @PostMapping("/addUser")
    public ModelAndView addUser(@ModelAttribute("user") @Validated({LoginController.LoginGroup.class, UserForm.UserData.class}) UserForm userForm, BindingResult result){

        ModelAndView mav = new ModelAndView();
        List<String> errorMessages = new ArrayList<>();
        //エラーメッセージを表示
        if(result.hasErrors()) {
            for(FieldError error : result.getFieldErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
            mav.addObject("errors", errorMessages);
            mav.setViewName("/signUp");
            return mav;
        }

        //パスワードの一致確認
        if(!userForm.getPassword().equals(userForm.getAnotherPassword())) {
            errorMessages.add("パスワードと確認用が一致しません");
            mav.addObject("errors", errorMessages);
            mav.setViewName("/signUp");
            return mav;
        }

        //部署と支社の一致確認
        if(userForm.getBranchId() == 1 && userForm.getDepartmentId() >= 3 ) {
            errorMessages.add("支社と部署の組み合わせが不正です");
            mav.addObject("errors", errorMessages);
            mav.setViewName("/signUp");
            return mav;
        } else if(userForm.getBranchId() >= 2 && userForm.getDepartmentId() <= 2) {
            errorMessages.add("支社と部署の組み合わせが不正です");
            mav.addObject("errors", errorMessages);
            mav.setViewName("/signUp");
            return mav;
        }
        //アカウント重複用
        if(userService.checkAccount(userForm.getAccount(), userForm.getId())){
            errorMessages.add("アカウントが重複しています");
            mav.addObject("errors", errorMessages);
            mav.setViewName("/signUp");
            return mav;
        }
        userService.saveUser(userForm);
        return new ModelAndView("redirect:/user");
    }
}
