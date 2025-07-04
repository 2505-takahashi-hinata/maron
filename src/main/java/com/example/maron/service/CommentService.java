package com.example.maron.service;

import com.example.maron.controller.form.CommentForm;
import com.example.maron.controller.form.MessageForm;
import com.example.maron.dto.UserComment;
import com.example.maron.repository.CommentRepository;
import com.example.maron.repository.entity.Comment;
import com.example.maron.repository.entity.Message;
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

    //コメント追加処理
    public void saveComment(CommentForm commentForm) {
        Comment comment = setCommentEntity(commentForm);
        commentRepository.save(comment);
    }

    //Form→Entityにつめかえ
    private Comment setCommentEntity(CommentForm commentForm) {
        Comment comment = new Comment();
        comment.setId(commentForm.getId());
        comment.setText(commentForm.getText());
        comment.setUserId(commentForm.getUserId());
        comment.setMessageId(commentForm.getMessageId());
        return comment;
    }

    //投稿削除処理
    public void deleteComment(int id) {
        commentRepository.deleteById(id);
    }
}
