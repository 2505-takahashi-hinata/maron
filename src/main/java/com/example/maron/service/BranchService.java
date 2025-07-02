package com.example.maron.service;

import com.example.maron.controller.form.BranchForm;
import com.example.maron.controller.form.DepartmentForm;
import com.example.maron.repository.BranchRepository;
import com.example.maron.repository.UserRepository;
import com.example.maron.repository.entity.Branch;
import com.example.maron.repository.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BranchService {
    @Autowired
    BranchRepository branchRepository;

    /*
     * レコード全件取得処理
     */
    public List<BranchForm> findAllBranch() throws ParseException {
        List<Branch> results = branchRepository.findAll();
        return setBranchForm(results);
    }

    private List<BranchForm> setBranchForm(List<Branch> results) {
        List<BranchForm> branches = new ArrayList<>();
        for (Branch value : results) {
            BranchForm branch = new BranchForm();
            branch.setId(value.getId());
            branch.setName(value.getName());
            branch.setCreatedDate(value.getCreatedDate());
            branch.setUpdatedDate(value.getUpdatedDate());
            branches.add(branch);
        }
        return branches;
    }
}