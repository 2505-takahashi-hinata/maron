package com.example.maron.controller;

import com.example.maron.controller.form.UserForm;
import com.example.maron.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

@Controller
public class LoginController {
    @Autowired
    UserService UserService;
    @Autowired
    HttpSession session;

    //ログイン画面表示
    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        UserForm userForm = new UserForm();
         //空のUserFormを保管
        mav.addObject("userForm", userForm);
        //sessionから取得したエラーメッセージ保管
        String message = (String) session.getAttribute("message");
        mav.addObject("message", message);
        // セッションからエラーメッセージを削除
        session.removeAttribute("message");
        mav.setViewName("/login");
        return mav;
    }
    public interface LoginGroup {}
    //ログイン処理
    @PostMapping("/login")
    public ModelAndView loginCheck(@ModelAttribute("userForm") @Validated(LoginGroup.class) UserForm userForm, BindingResult result) throws ParseException {
        //入力チェック　エラーの場合はログイン画面へフォワード
        ModelAndView mav = new ModelAndView();
        if (result.hasErrors()) {
            mav.addObject("userForm", userForm);
            mav.setViewName("/login");
            return mav;
        }
//        //encryptメソッドでパスワード暗号化されたものを用意
        String password = userForm.getPassword();
//        String encPassword = encrypt(userForm.getPassword());
        String account = userForm.getAccount();
//        //データベースからユーザ情報取得
        userForm = UserService.loginCheck(account, password);
//        userForm = UserService.loginCheck(account, encPassword);
        //ユーザ情報チェック　情報ない場合・停止中の場合はログイン画面へフォワード
        if ((userForm == null)|| (userForm.getIsStopped() == 1 )) {
            mav.addObject("message", "ログインに失敗しました");
            mav.addObject("formModel", userForm);
            mav.setViewName("/login");
            return mav;
        }
        //ログインOKの場合はログイン情報をセッションに追加し、トップ画面へリダイレクト
        session.setAttribute("loginUser", userForm);
        return new ModelAndView("redirect:/maron/");
    }


    //パスワード暗号化のメソッド
    //SHA-256で暗号化し、バイト配列をBase64エンコーディング。暗号化された文字列をreturn
    //（単純に復号化できない｢SHA-256」=ハッシュアルゴリズムを利用。バイト配列より文字列の方が扱いやすいため、 Base64でエンコードを行う）
    public String encrypt(String Password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(Password.getBytes());
            return Base64.encodeBase64URLSafeString(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    //ログアウト処理
    @GetMapping("/logout")
    public ModelAndView logout() {
        //セッション内の情報（ログインユーザー情報）を破棄
        session.invalidate();
        return new ModelAndView("redirect:/login");
    }

}
