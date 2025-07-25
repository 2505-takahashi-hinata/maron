package com.example.maron.controller.form;


import com.example.maron.controller.LoginController;
import com.example.maron.validator.PasswordValidator;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Getter
@Setter
public class UserForm {
    public static interface UserData{}
    public static interface UserEdit{}
    private int id;
    @NotBlank(message ="アカウントを入力してください", groups = {LoginController.LoginGroup.class, UserData.class, UserEdit.class})
    @Pattern(regexp="^[a-z0-9]{6,20}$", message = "アカウントは半角英数字かつ6文字以上20文字以下で入力してください", groups = {UserData.class, UserEdit.class})
    private String account;
    @NotBlank(message ="パスワードを入力してください", groups = {LoginController.LoginGroup.class, UserData.class})
    @Pattern(regexp="^[a-z]{6,20}$", message = "パスワードは半角文字かつ6文字以上20文字以下で入力してください", groups = {UserData.class})
    //ユーザ編集用カスタムバリデーション（入力されている場合は文字数チェック）
    @PasswordValidator(message = "パスワードは半角文字かつ6文字以上20文字以下で入力してください", groups = {UserEdit.class})
    private String password;
    private String anotherPassword;
    @NotBlank(message ="氏名を入力してください", groups = {UserData.class, UserEdit.class})
    @Size(max = 10, message = "氏名は10文字以下で入力してください", groups = {UserData.class, UserEdit.class})
    private String name;
    @Min(value = 1, message = "支社を選択してください", groups = {UserData.class,UserEdit.class})
    private int branchId;
    @Min(value = 1, message = "部署を選択してください", groups = {UserData.class,UserEdit.class})
    private int departmentId;
    private Short isStopped;
    private Date createdDate;
    private Date updatedDate;
}