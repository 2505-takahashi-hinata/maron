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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    HttpSession session;

    //新規コメント投稿処理
    @PostMapping("/addComment")
    public ModelAndView addMessage(@ModelAttribute("commentForm") @Valid CommentForm commentForm, BindingResult result){
        //件名・本文・カテゴリを取得、エラーメッセージあれば新規投稿画面へリダイレクト（投稿者IDもhtmlから送る？）
        ModelAndView mav = new ModelAndView();
        if (result.hasErrors()) {
            mav.addObject("commentForm", commentForm);
//            mav.addObject("errorMessage",result);
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
