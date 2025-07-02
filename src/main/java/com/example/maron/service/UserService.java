package com.example.maron.service;

import com.example.maron.controller.form.UserForm;
import com.example.maron.repository.UserRepository;
import com.example.maron.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    /*
     * レコード全件取得処理
     */
    public List<UserForm> findAllUser() throws ParseException {
        List<User> results = userRepository.findAllUser();
        List<UserForm> users = setUserForm(results);
        return users;
    }

    private List<UserForm> setUserForm(List<User> results) {
        List<UserForm> users = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            UserForm user = new UserForm();
            User result = results.get(i);
            user.setId(result.getId());
            user.setAccount(result.getAccount());
            user.setPassword(result.getPassword());
            user.setBranchId(result.getBranchId());
            user.setIsStopped(result.getIsStopped());
            user.setDepartmentId(result.getDepartmentId());
            user.setCreatedDate(result.getCreatedDate());
            user.setUpdatedDate(result.getUpdatedDate());
            users.add(user);
        }
        return users;
    }

    public void changeStatus(Integer id, Short status) {
        userRepository.updateStatusById(id, status);
    }
}


