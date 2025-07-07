package com.example.maron.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class userManage {
    private int id;
    private String account;
    private String name;
    private int branchId;
    private int departmentId;
    private short isStopped;
    private String branchName;
    private String departmentName;
}
