package com.example.maron.controller.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Getter
@Setter
public class DepartmentForm {

    private int id;
    private String name;
    private Date createdDate;
    private Date updatedDate;
}
