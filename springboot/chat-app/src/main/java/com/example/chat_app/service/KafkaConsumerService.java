package com.example.chat_app.service;

import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import com.example.chat_app.model.ChatModel;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service // Menandakan bahwa ini adalah kelas service dalam konteks Spring
public class KafkaConsumerService {

   @Autowired
   private SimpMessagingTemplate template; // Digunakan untuk mengirim pesan ke klien melalui WebSocket

   @Autowired
   private ObjectMapper objectMapper; // Digunakan untuk mengonversi string JSON ke objek Java

   @KafkaListener(topics = "chat", groupId = "group_id")
   public void consume(String message) {
      try {
         // Mengonversi pesan yang diterima dari Kafka menjadi objek ChatModel
         ChatModel chatModel = objectMapper.readValue(message, ChatModel.class);

         // Mengirim objek ChatModel ke endpoint WebSocket "/topic/public"
         template.convertAndSend("/topic/public", chatModel);
      } catch (Exception e) {
         e.printStackTrace(); // Menangani pengecualian jika ada kesalahan selama proses konversi atau pengiriman
      }
   }
}



