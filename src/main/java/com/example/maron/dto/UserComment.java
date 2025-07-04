package com.example.maron.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserComment {
    private int id;
    private int userId;
    private int messageId;
    private String text;
    private Date createdDate;
    private Date updatedDate;
    private String account;
    private String name;
    private int branchId;
    private int departmentId;

}
