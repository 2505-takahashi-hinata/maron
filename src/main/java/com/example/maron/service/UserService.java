package com.example.maron.service;


import com.example.maron.controller.form.UserForm;
import com.example.maron.dto.userManage;
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
    @Autowired
    BranchService branchRepository;
    @Autowired
    DepartmentService departmentRepository;

    /*
     * レコード全件取得処理
     */
    public List<userManage> findAllUser() throws ParseException {
        List<Object[]> users = userRepository.findAllUser();
        return setDtoForm(users);
    }
    //ユーザー管理画面取得用
    private List<userManage> setDtoForm(List<Object[]> users) throws ParseException {
        List<userManage> forms = new ArrayList<>();
        for (Object[] objects : users) {
            userManage user = new userManage();
            user.setId((int)objects[0]);
            user.setAccount((String)objects[1]);
            user.setName((String)objects[2]);
            user.setBranchId((int)objects[3]);
            user.setDepartmentId((int)objects[4]);
            user.setIsStopped((short)objects[5]);
            user.setBranchName((String)objects[6]);
            user.setDepartmentName((String)objects[7]);
            forms.add(user);
        }
        return forms;
    }
    //ユーザー編集画面取得用
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

    //ユーザー編集画面遷移のためのID取得
    public UserForm editUser(Integer id) throws ParseException {
        List<User> results = new ArrayList<>();
        results.add((User) userRepository.findById(id).orElse(null));
        List<UserForm> users = setUserForm(results);
        return users.get(0);
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
}
