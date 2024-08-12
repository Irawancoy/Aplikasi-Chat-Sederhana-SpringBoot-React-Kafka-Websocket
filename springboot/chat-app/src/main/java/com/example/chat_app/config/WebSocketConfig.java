package com.example.chat_app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration  // Menandakan bahwa kelas ini adalah kelas konfigurasi Spring
@EnableWebSocketMessageBroker  // Mengaktifkan kemampuan pesan WebSocket dengan broker dalam aplikasi
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

   @Override
   public void configureMessageBroker(MessageBrokerRegistry config) {
      // Mengaktifkan broker pesan sederhana dengan prefix "/topic" untuk routing pesan keluar
      config.enableSimpleBroker("/topic");  
      
      // Menetapkan prefix "/app" untuk pesan yang dikirim dari klien ke aplikasi
      config.setApplicationDestinationPrefixes("/app");  
   }

   @Override
   public void registerStompEndpoints(StompEndpointRegistry registry) {
      // Mendaftarkan endpoint WebSocket di "/ws" yang dapat diakses klien untuk koneksi
      registry.addEndpoint("/ws")
              .setAllowedOrigins("http://localhost:5173")  // Mengizinkan klien dari origin tertentu
              .withSockJS();  // Menambahkan fallback SockJS untuk klien yang tidak mendukung WebSocket
   }
   
}


