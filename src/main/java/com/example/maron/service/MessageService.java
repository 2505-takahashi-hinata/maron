package com.example.maron.service;

import com.example.maron.controller.form.MessageForm;
import com.example.maron.controller.form.UserForm;
import com.example.maron.repository.MessageRepository;
import com.example.maron.repository.entity.Message;
import com.example.maron.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

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
