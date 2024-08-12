package com.example.chat_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.chat_app.model.ChatModel;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service // Menandakan bahwa ini adalah kelas service dalam konteks Spring
public class KafkaProducerService {

   @Autowired
   private KafkaTemplate<String, String> kafkaTemplate; // Digunakan untuk mengirim pesan ke topik Kafka

   @Autowired
   private ObjectMapper objectMapper; // Digunakan untuk mengonversi objek Java menjadi string JSON

   public void sendMessage(ChatModel chatModel) {
      try {
         // Mengonversi objek ChatModel menjadi string JSON
         String message = objectMapper.writeValueAsString(chatModel);

         // Mengirimkan pesan JSON ke topik "chat" di Kafka
         kafkaTemplate.send("chat", message);
      } catch (Exception e) {
         // Menangani pengecualian jika terjadi kesalahan selama konversi atau pengiriman
         e.printStackTrace();
      }
   }

}





