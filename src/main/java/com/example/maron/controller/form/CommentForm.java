package com.example.maron.controller.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Getter
@Setter
public class CommentForm {

    private int id;
    private String text;
    private int userId;
    private int messageId;
    private Date createdDate;
    private Date updatedDate;
}
