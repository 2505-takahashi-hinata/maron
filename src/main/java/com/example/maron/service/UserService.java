package com.example.maron.service;

import com.example.maron.controller.form.BranchForm;
import com.example.maron.controller.form.DepartmentForm;
import com.example.maron.controller.form.UserForm;
import com.example.maron.repository.UserRepository;
import com.example.maron.repository.entity.Branch;
import com.example.maron.repository.entity.Department;
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
    @Autowired
    BranchService branchRepository;
    @Autowired
    DepartmentService departmentRepository;

    /*
     * レコード全件取得処理
     */
    public List<UserForm> findAllUser() throws ParseException {
        List<User> results = userRepository.findAll();
        return setUserForm(results);
    }

    private List<UserForm> setUserForm(List<User> results) throws ParseException {
        List<UserForm> users = new ArrayList<>();
        for (User value : results) {
            UserForm user = new UserForm();
            user.setId(value.getId());
            user.setAccount(value.getAccount());
            user.setPassword(value.getPassword());
            user.setName(value.getName());
            user.setBranchId(value.getBranchId());
            user.setIsStopped(value.getIsStopped());
            user.setDepartmentId(value.getDepartmentId());
            user.setCreatedDate(value.getCreatedDate());
            user.setUpdatedDate(value.getUpdatedDate());
            users.add(user);
        }
        return users;
    }
    //ステータス変更処理
    public void changeStatus(Integer id, Short status) {
        userRepository.updateStatusById(id, status);
    }


    public void saveUser(UserForm userForm) {
        User saveUser = setUserEntity(userForm);
        userRepository.save(saveUser);
    }

    //ログイン情報取得
    public UserForm loginCheck(String account, String password) throws ParseException {
//        List<User> results = new ArrayList<>();
//        results.add((User) UserRepository.findByAccountAndPassword(account, password));
        List<User> results =  userRepository.findByAccountAndPassword(account, password);
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
        user.setIsStopped(reqUser.getIsStopped());
        user.setDepartmentId(reqUser.getDepartmentId());
        user.setCreatedDate(reqUser.getCreatedDate());
        user.setUpdatedDate(reqUser.getUpdatedDate());
        return user;
    }
    //ユーザー編集画面遷移のためのID取得
    public UserForm editUser(Integer id) throws ParseException {
        List<User> results = new ArrayList<>();
        results.add((User) userRepository.findById(id).orElse(null));
        List<UserForm> users = setUserForm(results);
        return users.get(0);
    }

}
