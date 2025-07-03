package com.example.maron.service;


import com.example.maron.controller.form.MessageForm;
import com.example.maron.dto.UserMessage;
import com.example.maron.repository.MessageRepository;
import com.example.maron.repository.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

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
            List<UserMessage> messages = messageRepository.findByUpdatedDateBetweenAndCategoryOrderByUpdatedDateDesc(startDate, endDate, category, limit);
            return messages;
        }else {
            List<UserMessage> messages = messageRepository.findByUpdatedDateBetweenOrderByUpdatedDateDesc(startDate, endDate, limit);
            return messages;
        }

    }

//    public List<MessageForm> setMessageForm(List<Message>messages) {
//        List<MessageForm> form = new ArrayList<>();
//    }


}
