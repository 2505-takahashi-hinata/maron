package com.example.maron.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @OneToOne
    @JoinColumn()
    private Branch branch;

    @OneToOne
    @JoinColumn
    private Department department;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String account;

    @Column
    private String password;

    @Column
    private String name;

    @Column(name = "branch_id", insertable = false,updatable = false)
    private int branchId;

    @Column(name = "department_id",insertable = false,updatable = false)
    private int departmentId;

    @Column(name = "is_stopped")
    private Short isStopped;

    @Column(name = "created_date", insertable = false, updatable = false)
    //タイムスタンプ型に直す　DBとentityの型を合わせるため
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "updated_date", insertable = false, updatable = true)
    @UpdateTimestamp
    private Date updatedDate;
}
