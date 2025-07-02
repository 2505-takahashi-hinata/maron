package com.example.maron.controller.form;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Getter
@Setter
public class MessageForm {

    private int id;
    private String title;
    private String text;
    private String category;
    private int userId;
    private Date createdDate;
    private Date updatedDate;
}
