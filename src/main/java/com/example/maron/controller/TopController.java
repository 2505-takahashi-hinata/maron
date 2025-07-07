package com.example.maron.controller;

import com.example.maron.controller.form.CommentForm;
import com.example.maron.controller.form.MessageForm;
import com.example.maron.controller.form.UserForm;
import com.example.maron.dto.UserComment;
import com.example.maron.dto.UserMessage;
import com.example.maron.repository.entity.User;
import com.example.maron.service.CommentService;
import com.example.maron.service.MessageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;


@Controller
public class TopController {
    @Autowired
    HttpSession session;
    @Autowired
    MessageService messageService;
    @Autowired
    CommentService commentService;

    @GetMapping("/maron/")
    public ModelAndView top(@RequestParam(name = "start", required = false) LocalDate start,
                            @RequestParam(name = "end", required = false) LocalDate end,
                            @RequestParam(name = "category", required = false) String category) {
        ModelAndView mav = new ModelAndView();
        boolean userManageButton = false;
        UserForm loginUser = (UserForm)session.getAttribute("loginUser");
        if(loginUser.getDepartmentId() == 1){
            userManageButton = true;
        }

        List<String>errors =(List<String>)session.getAttribute("commentErrors");
        CommentForm errorId =(CommentForm)session.getAttribute("commentForm");
        if(errors != null){
            mav.addObject("commentErrorId",errorId);
            mav.addObject("commentErrors",errors);
            session.removeAttribute("commentErrors");
        }

        List<UserMessage> messages = messageService.findAllMessage(start, end, category);
        List<UserComment> comments = commentService.findAllComment();

        mav.setViewName("/top");
        mav.addObject("loginUser",loginUser);
        mav.addObject("messages",messages);
        mav.addObject("comments",comments);
        mav.addObject("start",start);
        mav.addObject("end",end);
        mav.addObject("category",category);
        mav.addObject("userManageButton",userManageButton);
        return mav;
    }

}
