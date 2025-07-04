package com.example.maron.service;

public class CommentService {
import com.example.maron.dto.UserComment;
import com.example.maron.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;
    //コメント全権取得
    public List<UserComment> findAllComment(){
        Integer limit = 100;
        List<Object[]> comments = commentRepository.findAllOrderByUpdatedDateAsc(limit);
        return setDto(comments);
    }
    public List<UserComment> setDto(List<Object[]>comments) {
        List<UserComment> form = new ArrayList<>();
        for (Object[] objects:comments){
            UserComment dto = new UserComment();
            dto.setId((int)objects[0]);
            dto.setUserId((int)objects[1]);
            dto.setMessageId((int)objects[2]);
            dto.setText((String) objects[3]);
            dto.setCreatedDate((Date)objects[4]);
            dto.setUpdatedDate((Date)objects[5]);
            dto.setAccount((String) objects[6]);
            dto.setName((String) objects[7]);
            dto.setBranchId((int)objects[8]);
            dto.setDepartmentId((int)objects[9]);
            form.add(dto);
        }
        return form;
    }
}
