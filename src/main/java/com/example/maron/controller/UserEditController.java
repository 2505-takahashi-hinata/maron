package com.example.maron.controller;

import com.example.maron.controller.form.BranchForm;
import com.example.maron.controller.form.DepartmentForm;
import com.example.maron.controller.form.UserForm;
import com.example.maron.service.BranchService;
import com.example.maron.service.DepartmentService;
import com.example.maron.service.UserService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpSession;
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
    @Autowired
    HttpSession session;

    @GetMapping({"/userEdit/{id}","/userEdit/"})
    public ModelAndView userEdit (@PathVariable(required = false) String id) throws ParseException {
        ModelAndView mav = new ModelAndView();
        List<String> errorMessages = new ArrayList<>();
        if(StringUtils.isBlank(id) || !id.matches("^[0-9]*$")){
            errorMessages.add("不正なパラメータが入力されました");
            session.setAttribute("errors", errorMessages);
            return new ModelAndView("redirect:/user");
        }
        int intId = Integer.parseInt(id);
        UserForm userForm = userService.editUser(intId);
        if(userForm == null){
            errorMessages.add("不正なパラメーターが入力されました");
            session.setAttribute("errors", errorMessages);
            //mav.addObject("errors", errorMessages);
            return new ModelAndView("redirect:/user");
        }
        UserForm loginUser = (UserForm) session.getAttribute("loginUser");
        List<BranchForm> branchData = branchService.findAllBranch();
        List<DepartmentForm> departmentData = departmentService.findAllDepartment();
        mav.addObject("loginUser",loginUser);
        mav.addObject("user", userForm);
        mav.addObject("branch", branchData);
        mav.addObject("department", departmentData);
        mav.setViewName("/userEdit");
        return mav;
    }

    @PostMapping("/userUpdate/{id}")
    public ModelAndView userUpdate(@PathVariable Integer id,
                                   @ModelAttribute("user") @Validated({ UserForm.UserEdit.class}) UserForm userForm, BindingResult result) throws ParseException {
        ModelAndView mav = new ModelAndView();
        List<String> errorMessages = new ArrayList<>();
        userForm.setId(id);
        //エラーメッセージを表示
        if(result.hasErrors()) {
            for(FieldError error : result.getFieldErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
            mav.addObject("errors", errorMessages);
            mav.setViewName("/userEdit");
            return mav;
        }

        //パスワードの一致確認
        if(!userForm.getPassword().equals(userForm.getAnotherPassword())) {
            errorMessages.add("パスワードと確認用が一致しません");
            mav.addObject("errors", errorMessages);
            mav.setViewName("/userEdit");
            return mav;
        }

        //部署と支社の一致確認
        if(userForm.getBranchId() == 1 && userForm.getDepartmentId() >= 3 ) {
            errorMessages.add("支社と部署の組み合わせが不正です");
            mav.addObject("errors", errorMessages);
            mav.setViewName("/userEdit");
            return mav;
        } else if(userForm.getBranchId() >= 2 && userForm.getDepartmentId() <= 2) {
            errorMessages.add("支社と部署の組み合わせが不正です");
            mav.addObject("errors", errorMessages);
            mav.setViewName("/userEdit");
            return mav;
        }

        //アカウント重複用
        if(userService.checkAccount(userForm.getAccount(), userForm.getId())){
            errorMessages.add("アカウントが重複しています");
            mav.addObject("errors", errorMessages);
            mav.setViewName("/userEdit");
            return mav;
        }

        userService.saveUser(userForm);
        return new ModelAndView("redirect:/user");
    }
}
