package com.example.service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private AccountRepository accountRepository;

    public Message createMessage(Message message) {
        if (message.getMessageText() == null || 
            message.getMessageText().trim().isEmpty() ||
            message.getMessageText().length() > 255 ||
            accountRepository.findById(message.getPostedBy()).isEmpty()) {
            return null;
        }
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    public Integer deleteMessage(Integer messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return 1;
        }
        return 0;
    }

    public Integer updateMessage(Integer messageId, String messageText) {
        if (messageText == null || messageText.trim().isEmpty() || 
            messageText.length() > 255) {
            return 0;
        }
        
        Message message = messageRepository.findById(messageId).orElse(null);
        if (message == null) {
            return 0;
        }
        
        message.setMessageText(messageText);
        messageRepository.save(message);
        return 1;
    }

    public List<Message> getMessagesByAccount(Integer accountId) {
        return messageRepository.findByPostedBy(accountId);
    }
}