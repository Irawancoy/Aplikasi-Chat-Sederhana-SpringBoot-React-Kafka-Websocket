import React from "react";
import "./SystemMessage.css"; 

// Komponen untuk menampilkan pesan sistem (misalnya, "user has joined" atau "user has left")
const SystemMessageComponent = ({ message }) => {
  return (
    <div className="system-message">  {/* Kontainer untuk pesan sistem */}
      <p>{message}</p>  {/* Menampilkan isi pesan sistem */}
    </div>
  );
};

export default SystemMessageComponent;  // Mengekspor komponen untuk digunakan di bagian lain aplikasi
