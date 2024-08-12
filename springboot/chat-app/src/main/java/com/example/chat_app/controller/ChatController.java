package com.example.chat_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import com.example.chat_app.model.ChatModel;
import com.example.chat_app.service.KafkaProducerService;

@Controller  // Menandakan bahwa kelas ini adalah kelas controller dalam konteks Spring
public class ChatController {

   @Autowired
   private KafkaProducerService kafkaProducer;  // Menginjeksi KafkaProducerService untuk mengirimkan pesan ke Kafka

   @MessageMapping("/chat.sendMessage")  // Menentukan bahwa metode ini menangani pesan dari endpoint WebSocket "/chat.sendMessage"
   @SendTo("/topic/public")  // Mengarahkan pesan yang diproses oleh metode ini ke topik WebSocket "/topic/public"
   public ChatModel sendMessage(ChatModel chatModel) {
      kafkaProducer.sendMessage(chatModel);  // Mengirimkan pesan ke Kafka melalui KafkaProducerService
      return chatModel;  // Mengembalikan objek ChatModel yang sama agar dapat dikirim ke klien yang berlangganan topik "/topic/public"
   }
}




