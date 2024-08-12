package com.example.chat_app.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@EnableKafka  // Mengaktifkan dukungan Kafka dalam aplikasi Spring Boot
@Configuration  // Menandakan bahwa kelas ini merupakan kelas konfigurasi Spring
public class KafkaConfig {

   @Bean
   public ProducerFactory<String, String> producerFactory() {
      Map<String, Object> config = new HashMap<>();

      // Konfigurasi untuk produsen Kafka
      config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");  // Alamat server Kafka
      config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);  // Serializer untuk kunci pesan
      config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);  // Serializer untuk nilai pesan

      return new DefaultKafkaProducerFactory<>(config);  // Membuat dan mengembalikan instance DefaultKafkaProducerFactory
   }

   @Bean 
   public KafkaTemplate<String, String> kafkaTemplate() {
      return new KafkaTemplate<>(producerFactory());  // Menyediakan KafkaTemplate untuk mengirim pesan
   }

   @Bean
   public ConsumerFactory<String, String> consumerFactory() {
      Map<String, Object> config = new HashMap<>();

      // Konfigurasi untuk konsumen Kafka
      config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");  // Alamat server Kafka
      config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");  // ID grup konsumen
      config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);  // Deserializer untuk kunci pesan
      config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);  // Deserializer untuk nilai pesan

      return new DefaultKafkaConsumerFactory<>(config);  // Membuat dan mengembalikan instance DefaultKafkaConsumerFactory
   }

   @Bean
   public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
      ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
      factory.setConsumerFactory(consumerFactory());  // Mengatur ConsumerFactory untuk mendengarkan pesan
      return factory;  // Mengembalikan KafkaListenerContainerFactory yang terkonfigurasi
   }
   
}



