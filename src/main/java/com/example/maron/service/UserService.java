package com.example.maron.service;


import com.example.maron.controller.form.UserForm;
import com.example.maron.dto.userManage;
import com.example.maron.repository.UserRepository;
import com.example.maron.repository.entity.User;
import io.micrometer.common.util.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
//        List<User> results = new ArrayList<>();
        User user = userRepository.findById(id).orElse(null);
        if (user == null ){
            return null;
        }
        List<User> results = new ArrayList<>();
        results.add(user);
        List<UserForm> users = setUserForm(results);
        return users.get(0);
    }
    //ステータス変更処理
    public void changeStatus(Integer id, Short status) {
        userRepository.updateStatusById(id, status);
    }

    public void saveUser(UserForm userForm) throws ParseException {
        //encryptメソッドでパスワード暗号化したものをuserFormにセット
        if(userForm.getPassword() != null){
            String password = encrypt(userForm.getPassword());
            userForm.setPassword(password);
        }
        User saveUser = setUserEntity(userForm);
        userRepository.save(saveUser);
    }

    //ログイン情報取得
    public UserForm loginCheck(String account, String encPassword) throws ParseException {
        //encryptメソッドにてパスワード暗号化
        String password = encrypt(encPassword);
        List<User> results =  userRepository.findByAccountAndPassword(account, password);
        //ユーザ情報０件の場合nullを返す
        if (results == null || results.isEmpty()){
            return null;
        }
        List<UserForm> reports = setUserForm(results);
        return reports.get(0);
    }

    //Form→entityに詰め変え
    private User setUserEntity(UserForm reqUser) throws ParseException {
        User user = new User();
        user.setId(reqUser.getId());
        user.setAccount(reqUser.getAccount());
        if(!StringUtils.isBlank(reqUser.getPassword())) {
            user.setPassword(reqUser.getPassword());
        } else {
            user.setPassword(editUser(reqUser.getId()).getPassword());
        }
        user.setName(reqUser.getName());
        user.setBranchId(reqUser.getBranchId());
        user.setIsStopped(reqUser.getIsStopped());
        user.setDepartmentId(reqUser.getDepartmentId());
        user.setCreatedDate(reqUser.getCreatedDate());
        user.setUpdatedDate(reqUser.getUpdatedDate());
        return user;
    }

    public boolean checkAccount(String account, Integer id) {
        if(id == null) {
            return userRepository.existsByAccount(account);
        } else {
            return  userRepository.existsByAccountAndIdNot(account, id);
        }
    }

    //パスワード暗号化のメソッド
    //SHA-256で暗号化し、バイト配列をBase64エンコーディング。暗号化された文字列をreturn
    //（単純に復号化できない｢SHA-256」=ハッシュアルゴリズムを利用。バイト配列より文字列の方が扱いやすいため、 Base64でエンコードを行う）
    public String encrypt(String Password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(Password.getBytes());
            return Base64.encodeBase64URLSafeString(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
