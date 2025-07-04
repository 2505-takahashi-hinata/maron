package com.example.maron.controller;

import com.example.maron.controller.form.MessageForm;
import com.example.maron.controller.form.UserForm;
import com.example.maron.service.MessageService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MessageController {
    @Autowired
    MessageService messageService;
    @Autowired
    HttpSession session;

    //新規投稿画面表示
    @GetMapping("/newMessage")
    public ModelAndView newMessage() {
        ModelAndView mav = new ModelAndView();
        MessageForm messageForm = new MessageForm();
        //空のUserFormを保管
        mav.addObject("messageForm", messageForm);
        mav.setViewName("/newMessage");
        return mav;
    }

    //新規投稿処理
    @PostMapping("/addMessage")
    public ModelAndView addMessage(@ModelAttribute("messageForm") @Valid MessageForm messageForm, BindingResult result){
        //件名・本文・カテゴリを取得、エラーメッセージあれば新規投稿画面へリダイレクト（投稿者IDもhtmlから送る？）
        ModelAndView mav = new ModelAndView();
        if (result.hasErrors()) {
            mav.addObject("formModel", messageForm);
            mav.setViewName("/newMessage");
            return mav;
        }
        //セッションに登録されているユーザ情報から、ユーザID取得しmessageFormにセット
        UserForm loginUser = (UserForm) session.getAttribute("loginUser");
        messageForm.setUserId(loginUser.getId());
        //登録処理
        messageService.saveMessage(messageForm);
        //ホーム画面へリダイレクト
        return new ModelAndView("redirect:/maron/");
    }

    //投稿削除
    @DeleteMapping("/deleteMessage/{id}")
    public ModelAndView deleteMessage(@PathVariable Integer id, @RequestParam Integer messageUserId) {
        //セッション内のログインユーザID取得
        UserForm loginUser = (UserForm) session.getAttribute("loginUser");
        Integer userId = loginUser.getId();
        //ログインユーザIDと投稿のユーザIDが一致するかチェック
        if (!(userId.equals(messageUserId))) {
            session.setAttribute("message", "無効なアクセスです");
            return new ModelAndView("redirect:/");
        }
        messageService.deleteMessage(id);
        // セッションからエラーメッセージのみを削除
        session.removeAttribute("message");
        // ホーム画面へリダイレクト
        return new ModelAndView("redirect:/");
    }
}
