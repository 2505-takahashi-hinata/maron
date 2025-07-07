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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserEditController {
    @Autowired
    UserService userService;

    @Autowired
    BranchService branchService;

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/userEdit/{id}")
    public ModelAndView userEdit (@PathVariable Integer id) throws ParseException {
        ModelAndView mav = new ModelAndView();
        UserForm userForm = userService.editUser(id);
        List<BranchForm> branchData = branchService.findAllBranch();
        List<DepartmentForm> departmentData = departmentService.findAllDepartment();
        mav.addObject("user", userForm);
        mav.addObject("branch", branchData);
        mav.addObject("department", departmentData);
        mav.setViewName("/userEdit");
        return mav;
    }

    @PutMapping("/userUpdate/{id}")
    public ModelAndView userUpdate(@PathVariable Integer id,
                                   @ModelAttribute("user") @Validated({LoginController.LoginGroup.class, Default.class}) UserForm userForm, BindingResult result) {
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
        userForm.setId(id);
        userService.saveUser(userForm);
        return new ModelAndView("redirect:/user");
    }
}
