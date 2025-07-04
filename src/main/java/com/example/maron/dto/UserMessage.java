package com.example.maron.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserMessage {
    private int id;
    private String text;
    private int userId;
    private String title;
    private String category;
    private String name;
    private String account;
    private int branchId;
    private int departmentId;
    private Date createdDate;
    private Date updatedDate;
}
