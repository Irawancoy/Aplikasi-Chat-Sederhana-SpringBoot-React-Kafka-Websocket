import React, { useState } from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import LoginFormComponent from "./components/LoginFormComponent";
import ChatRoom from "./components/ChatRoom";
import "./App.css";

function App() {
  const [username, setUsername] = useState("");  // State untuk menyimpan nama pengguna

  return (
    <Router>  {/* Mengatur Router untuk aplikasi */}
      <Routes>  {/* Menentukan rute aplikasi */}
        <Route
          path="/"  // Rute untuk halaman login
          element={username ? <Navigate to="/chat" replace /> : <LoginFormComponent setUsername={setUsername} />}
          // Jika nama pengguna ada, arahkan ke halaman chat; jika tidak, tampilkan form login
        />
        <Route
          path="/chat"  // Rute untuk halaman chat
          element={username ? <ChatRoom username={username} /> : <Navigate to="/" replace />}
          // Jika nama pengguna ada, tampilkan halaman chat; jika tidak, arahkan kembali ke halaman login
        />
      </Routes>
    </Router>
  );
}

export default App;  


