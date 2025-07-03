package com.example.maron.controller;

import com.example.maron.controller.form.BranchForm;
import com.example.maron.controller.form.DepartmentForm;
import com.example.maron.controller.form.UserForm;
import com.example.maron.service.BranchService;
import com.example.maron.service.DepartmentService;
import com.example.maron.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
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
    public ModelAndView saveUser(@PathVariable Integer id,
                                 @ModelAttribute("formModel") @Validated UserForm userForm, BindingResult result) {
        ModelAndView mav = new ModelAndView();
        if(result.hasErrors()) {
            mav.setViewName("/userEdit");
            return mav;
        }
        userForm.setId(id);
        return new ModelAndView("redirect:/user");
    }
}
