package com.example.maron.controller;


import com.example.maron.controller.form.CommentForm;
import com.example.maron.controller.form.MessageForm;
import com.example.maron.controller.form.UserForm;
import com.example.maron.service.CommentService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    HttpSession session;

    //新規コメント投稿処理
    @PostMapping("/addComment")
    public ModelAndView addMessage(@ModelAttribute("commentForm") @Valid CommentForm commentForm, BindingResult result){
        ModelAndView mav = new ModelAndView();
        List<String> errorMessages = new ArrayList<>();
        if (result.hasErrors()) {
//            mav.addObject("commentForm", commentForm);
//            //エラーメッセージが詰まったセッションを用意
            for (ObjectError error : result.getAllErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
            session.setAttribute("commentErrors", errorMessages);
            mav.setViewName("redirect:/maron/");
            return mav;
        }
        //セッションに登録されているユーザ情報から、ユーザID取得しmessageFormにセット
        UserForm loginUser = (UserForm) session.getAttribute("loginUser");
        commentForm.setUserId(loginUser.getId());
        //登録処理
        commentService.saveComment(commentForm);
        //ホーム画面へリダイレクト
        return new ModelAndView("redirect:/maron/");
    }

    //コメント削除
    @DeleteMapping("/deleteComment/{id}")
    public ModelAndView deleteMessage(@PathVariable Integer id){

        commentService.deleteComment(id);
        // ホーム画面へリダイレクト
        return new ModelAndView("redirect:/maron/");
    }
}
