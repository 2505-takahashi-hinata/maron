package com.example.maron.service;

import com.example.maron.controller.form.UserForm;
import com.example.maron.repository.UserRepository;
import com.example.maron.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository UserRepository;

    //ログイン情報取得
    public UserForm loginCheck(String account, String password) {
//        List<User> results = new ArrayList<>();
//        results.add((User) UserRepository.findByAccountAndPassword(account, password));
        List<User> results =  UserRepository.findByAccountAndPassword(account, password);
        //ユーザ情報０件の場合nullを返す
        if (results == null || results.isEmpty()){
            return null;
        }
        List<UserForm> reports = setUserForm(results);
        return reports.get(0);
    }

    //Form→entityに詰め変え
    private User setUserEntity(UserForm reqUser) {
        User user = new User();
        user.setId(reqUser.getId());
        user.setAccount(reqUser.getAccount());
        user.setPassword(reqUser.getPassword());
        user.setName(reqUser.getName());
        user.setBranchId(reqUser.getBranchId());
        user.setDepartmentId(reqUser.getDepartmentId());
        user.setIsStopped(reqUser.getIsStopped());
        user.setUpdatedDate(reqUser.getUpdatedDate());
        return user;
    }

    //entity→Formに詰め替え
    private List<UserForm> setUserForm(List<User> results) {
        List<UserForm> userForm = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            UserForm task = new UserForm();
            User result = results.get(i);
            task.setId(result.getId());
            task.setId(result.getId());
            task.setAccount(result.getAccount());
            task.setPassword(result.getPassword());
            task.setName(result.getName());
            task.setBranchId(result.getBranchId());
            task.setDepartmentId(result.getDepartmentId());
            task.setIsStopped(result.getIsStopped());
            task.setUpdatedDate(result.getUpdatedDate());
            userForm.add(task);
        }
        return userForm;
    }
}
