package com.example.maron.controller.form;


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

    private int id;
    @NotBlank(message ="アカウントを入力してください")
    @Pattern(regexp="^[a-z0-9]{6,20}$", message = "アカウントは半角英数字かつ6文字以上20文字以下で入力してください")
    private String account;
    @NotBlank(message ="パスワードを入力してください")
    @Pattern(regexp="^[a-z]{6,20}$", message = "パスワードは半角文字かつ6文字以上20文字以下で入力してください")
    private String password;
    @NotBlank(message ="パスワードを入力してください")
    private String anotherPassword;
    @NotBlank(message ="氏名を入力してください")
    @Size(max = 10, message = "氏名は10文字以下で入力してください")
    private String name;
    @Min(value = 1, message = "支社を選択してください")
    private int branchId;
    @Min(value = 1, message = "部署を選択してください")
    private int departmentId;
    private Short isStopped;
    private Date createdDate;
    private Date updatedDate;
}