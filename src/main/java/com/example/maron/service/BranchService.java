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
        List<BranchForm> Branches = setBranchForm(results);
        return Branches;
    }

    private List<BranchForm> setBranchForm(List<Branch> results) {
        List<BranchForm> branches = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            BranchForm branch = new BranchForm();
            Branch result = results.get(i);
            branch.setId(result.getId());
            branch.setName(result.getName());
            branch.setCreatedDate(result.getCreatedDate());
            branch.setUpdatedDate(result.getUpdatedDate());
            branches.add(branch);
        }
        return branches;
    }
}