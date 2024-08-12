import React, { useEffect, useState, useRef } from "react";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import MessageComponent from "./MessageComponent";
import SystemMessageComponent from "./SystemMessageComponent";
import "./ChatRoom.css";

const ChatRoom = ({ username }) => {
  const [messages, setMessages] = useState([]);  // State untuk menyimpan daftar pesan
  const [input, setInput] = useState("");  // State untuk menyimpan input pesan pengguna
  const stompClientRef = useRef(null);  // Ref untuk menyimpan klien STOMP
  const [isConnected, setIsConnected] = useState(false);  // State untuk memeriksa status koneksi

  useEffect(() => {
    // Mengatur koneksi WebSocket dan STOMP
    const socket = new SockJS("http://localhost:8080/ws");
    const stompClient = Stomp.over(socket);
    stompClientRef.current = stompClient;

    // Callback saat koneksi berhasil
    const connectCallback = () => {
      setIsConnected(true);
      // Berlangganan ke topik "/topic/public" untuk menerima pesan
      stompClient.subscribe("/topic/public", (message) => {
        const receivedMessage = JSON.parse(message.body);
        const isSystemMessage =
          receivedMessage.message.includes("has joined") ||
          receivedMessage.message.includes("has left");

        // Menambahkan pesan ke daftar jika belum ada
        setMessages((prevMessages) => {
          if (
            !prevMessages.some(
              (msg) =>
                msg.message === receivedMessage.message &&
                msg.sender === receivedMessage.sender
            )
          ) {
            return [...prevMessages, { ...receivedMessage, type: isSystemMessage ? "system" : "chat" }];
          }
          return prevMessages;
        });
      });

      // Mengirim pesan bahwa pengguna telah bergabung
      stompClient.send(
        "/app/chat.sendMessage",
        {},
        JSON.stringify({ sender: username, message: `${username} has joined`, type: "system" })
      );
    };

    // Callback saat koneksi gagal
    const errorCallback = (error) => {
      console.error("WebSocket connection error:", error);
    };

    // Menghubungkan klien STOMP
    stompClient.connect({}, connectCallback, errorCallback);

    // Cleanup saat komponen dibersihkan
    return () => {
      if (stompClientRef.current && stompClientRef.current.connected) {
        // Mengirim pesan bahwa pengguna telah meninggalkan ruang obrolan
        stompClientRef.current.send(
          "/app/chat.sendMessage",
          {},
          JSON.stringify({ sender: username, message: `${username} has left`, type: "system" })
        );
        stompClientRef.current.disconnect();
      }
    };
  }, [username]);

  // Mengirim pesan saat form disubmit
  const sendMessage = (e) => {
    e.preventDefault();
    if (isConnected && input) {
      const chatMessage = {
        sender: username,
        message: input,
        type: "chat",
      };
      // Mengirim pesan chat ke topik "/app/chat.sendMessage"
      stompClientRef.current.send(
        "/app/chat.sendMessage",
        {},
        JSON.stringify(chatMessage)
      );
      setInput("");  // Mengosongkan field input
    }
  };

  // Mengirim pesan bahwa pengguna meninggalkan ruang obrolan dan mengalihkan ke halaman login
  const leaveRoom = () => {
    if (isConnected) {
      stompClientRef.current.send(
        "/app/chat.sendMessage",
        {},
        JSON.stringify({ sender: username, message: `${username} has left`, type: "system" })
      );
      stompClientRef.current.disconnect();
    }
    window.location.href = "/"; // Redirect to the login page
  };

  return (
    <div className="chat-room">
      <div className="sidebar">
        <h3>Users</h3>
        <button onClick={leaveRoom} className="leave-button">
          Leave Room
        </button>
      </div>
      <div className="main">
        <div className="messages">
          {/* Menampilkan pesan menggunakan komponen MessageComponent atau SystemMessageComponent */}
          {messages.map((message, index) =>
            message.type === "system" ? (
              <SystemMessageComponent key={index} message={message.message} />
            ) : (
              <MessageComponent key={index} message={message} />
            )
          )}
        </div>
        <form onSubmit={sendMessage} className="input-area">
          <input
            type="text"
            placeholder="Type a message..."
            value={input}
            onChange={(e) => setInput(e.target.value)}
            className="input-field"
          />
          <button type="submit" className="send-button">
            Send
          </button>
        </form>
      </div>
    </div>
  );
};

export default ChatRoom;
