package com.example.controller;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 @RestController
 public class SocialMediaController {
     @Autowired
     private AccountService accountService;
     
     @Autowired
     private MessageService messageService;
 
     @PostMapping("/register")
     public ResponseEntity<Account> register(@RequestBody Account account) {
         try {
             Account registeredAccount = accountService.register(account);
             if (registeredAccount == null) {
                 return ResponseEntity.status(400).build();
             }
             return ResponseEntity.ok(registeredAccount);
         } catch (RuntimeException e) {
             if (e.getMessage().equals("Username already exists")) {
                 return ResponseEntity.status(409).build();
             }
             return ResponseEntity.status(400).build();
         }
     }
 
     @PostMapping("/login")
     public ResponseEntity<Account> login(@RequestBody Account account) {
         Account loggedInAccount = accountService.login(account);
         if (loggedInAccount == null) {
             return ResponseEntity.status(401).build();
         }
         return ResponseEntity.ok(loggedInAccount);
     }
 
     @PostMapping("/messages")
     public ResponseEntity<Message> createMessage(@RequestBody Message message) {
         Message createdMessage = messageService.createMessage(message);
         if (createdMessage == null) {
             return ResponseEntity.status(400).build();
         }
         return ResponseEntity.ok(createdMessage);
     }
 
     @GetMapping("/messages")
     public ResponseEntity<List<Message>> getAllMessages() {
         return ResponseEntity.ok(messageService.getAllMessages());
     }
 
     @GetMapping("/messages/{message_id}")
     public ResponseEntity<Message> getMessageById(@PathVariable("message_id") Integer messageId) {
         return ResponseEntity.ok(messageService.getMessageById(messageId));
     }
 
     @DeleteMapping("/messages/{message_id}")
     public ResponseEntity<String> deleteMessage(@PathVariable("message_id") Integer messageId) {
         Integer rowsDeleted = messageService.deleteMessage(messageId);
         if (rowsDeleted == 0) {
             return ResponseEntity.ok().body("");
         }
         return ResponseEntity.ok().body("1");
     }
 
     @PatchMapping("/messages/{message_id}")
     public ResponseEntity<Integer> updateMessage(@PathVariable("message_id") Integer messageId, @RequestBody Message message) {
         Integer rowsUpdated = messageService.updateMessage(messageId, message.getMessageText());
         if (rowsUpdated == null || rowsUpdated == 0) {
             return ResponseEntity.status(400).build();
         }
         return ResponseEntity.ok(rowsUpdated);
     }
 
     @GetMapping("/accounts/{account_id}/messages")
     public ResponseEntity<List<Message>> getMessagesByAccount(@PathVariable("account_id") Integer accountId) {
         return ResponseEntity.ok(messageService.getMessagesByAccount(accountId));
     }
 }