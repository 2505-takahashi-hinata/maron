package com.example.maron.repository;

import com.example.maron.repository.entity.Department;
import com.example.maron.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
