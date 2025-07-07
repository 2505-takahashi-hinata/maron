package com.example.maron.controller.form;

import com.example.maron.validator.Validator;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Getter
@Setter
public class MessageForm {

    private int id;
//    @NotBlank(message ="件名を入力してください")
    @Size(max = 30, message = "件名は30文字以内で入力してください")
    @Validator(message ="件名を入力してください")
    private String title;
    @Size(max = 1000, message = "本文は1000文字以内で入力してください")
    @Validator(message ="本文を入力してください")
    private String text;
//    @NotBlank(message ="カテゴリを入力してください")
    @Size(max = 10, message = "カテゴリは10文字以内で入力してください")
    @Validator(message ="カテゴリを入力してください")
    private String category;
    private int userId;
    private Date createdDate;
    private Date updatedDate;
}
