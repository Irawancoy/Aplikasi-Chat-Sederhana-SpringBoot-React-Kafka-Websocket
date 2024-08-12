import React, { useState } from 'react';
import './LoginForm.css'

function LoginFormComponent({ setUsername }) {
   const [input, setInput] = useState("");  // State untuk menyimpan nilai input

   // Fungsi untuk menangani pengiriman form
   const handleInput = (e) => {
      e.preventDefault();  // Mencegah perilaku default form yang akan me-reload halaman
      if (input) {
         setUsername(input);  // Mengirimkan nilai input ke parent component melalui setUsername
      }
   };

   return (
      <div className="login-form">  {/* Kontainer untuk form login */}
         <form onSubmit={handleInput} className="login-form-container">  {/* Form dengan onSubmit untuk menangani pengiriman */}
            <input
               type="text"
               placeholder="Enter your username"  // Placeholder untuk input field
               value={input}  // Mengatur nilai input sesuai dengan state
               onChange={(e) => setInput(e.target.value)}  // Memperbarui state input saat nilai berubah
               className="login-input"  // Kelas CSS untuk styling
            />
            <button type="submit" className="login-button">Submit</button>  {/* Tombol submit untuk mengirim form */}
         </form>
      </div>
   );
}

export default LoginFormComponent;


