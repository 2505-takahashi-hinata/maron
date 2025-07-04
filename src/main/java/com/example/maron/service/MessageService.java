package com.example.maron.service;


import com.example.maron.controller.form.MessageForm;
import com.example.maron.controller.form.UserForm;
import com.example.maron.dto.UserMessage;
import com.example.maron.repository.MessageRepository;
import com.example.maron.repository.entity.Message;
import com.example.maron.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    //    投稿全権取得
    public List<UserMessage> findAllMessage(LocalDate start, LocalDate end, String category){
        Integer limit = 100;
        LocalDateTime startDate;
        LocalDateTime endDate;
        if(start != null){
            startDate = start.atStartOfDay();
        }else{
            startDate = LocalDateTime.of(2022,1,1,0,0,0);
        }
        if(end != null){
            endDate = end.atTime(23,59,59);
        }else{
            endDate = LocalDateTime.now();
        }
        if(!StringUtils.isEmpty(category)) {
            List<Object[]> messages = messageRepository.findByUpdatedDateBetweenAndCategoryOrderByUpdatedDateDesc(startDate, endDate, category, limit);
            return setDto(messages);
        }else {
            List<Object[]> messages = messageRepository.findByUpdatedDateBetweenOrderByUpdatedDateDesc(startDate, endDate, limit);
            return setDto(messages);
        }

    }
    //Object[]を詰めたリストから一つずつ取り出し、UserMessageDtoに詰め直してる
    public List<UserMessage> setDto(List<Object[]>messages) {
        List<UserMessage> form = new ArrayList<>();
        for (Object[] objects:messages){
            UserMessage dto = new UserMessage();
            dto.setId((int)objects[0]);
            dto.setText((String) objects[1]);
            dto.setUserId((int)objects[2]);
            dto.setTitle((String) objects[3]);
            dto.setCategory((String) objects[4]);
            dto.setName((String) objects[5]);
            dto.setAccount((String) objects[6]);
            dto.setBranchId((int)objects[7]);
            dto.setDepartmentId((int)objects[8]);
            dto.setCreatedDate((Date)objects[9]);
            dto.setUpdatedDate((Date)objects[10]);
            form.add(dto);
        }
        return form;
    }

    //投稿追加処理
    public void saveMessage(MessageForm messageForm) {
        Message saveMessage = setMessageEntity(messageForm);
        messageRepository.save(saveMessage);
    }

    //投稿削除処理
    public void deleteMessage(int id) {
        messageRepository.deleteById(id);
    }

    //Form→Entityにつめかえ
    private Message setMessageEntity(MessageForm reqMessage) {
        Message message = new Message();
        message.setId(reqMessage.getId());
        message.setTitle(reqMessage.getTitle());
        message.setText(reqMessage.getText());
        message.setCategory(reqMessage.getCategory());
        message.setUserId(reqMessage.getUserId());
        message.setUpdatedDate(reqMessage.getUpdatedDate());
        return message;
    }

}
