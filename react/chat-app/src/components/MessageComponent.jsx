import React from "react";

// Komponen untuk menampilkan pesan chat
const MessageComponent = ({ message }) => {
   return (
      <div className="message">  {/* Kontainer untuk setiap pesan */}
         <p>
            <span className="sender">{message.sender}</span>: {message.message}  {/* Menampilkan pengirim dan isi pesan */}
         </p>
      </div>
   );
}

export default MessageComponent;


