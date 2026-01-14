import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter, Routes, Route } from "react-router"; //React Router
import './index.css';
import Homepage from './homepage/Homepage.tsx';
import Test from './Test.tsx';
import Register from './register_user/Register.tsx';
import Listings from './listings/Listings.tsx';
import Login from './login/Login.tsx'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Homepage />} />
        <Route path="/test" element={<Test />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/listings" element={<Listings/>} />
      </Routes>
    </BrowserRouter>
  </StrictMode>,
)
