import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter, Routes, Route } from "react-router"; //React Router
import './index.css';
import Homepage from './homepage/Homepage.tsx';
import Test from './Test.tsx';
import Register from './register_user/Register.tsx';
import Listings from './listings/Listings.tsx';
import Login from './login/Login.tsx'
import AuthenticationContext from './authentication/AuthenticationContext.tsx';
import { AuthenticationProvider } from './authentication/AuthenticationContext.tsx';
import Upload from './file_upload_test/Upload.tsx';
import ViewUserProfile from './user_profile/ViewUserProfile.tsx';
import NavBar from './NavBar.tsx';

AuthenticationContext;

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <AuthenticationProvider>
      <BrowserRouter>
        <NavBar />
        <Routes>
          <Route path="/" element={<Homepage />} />
          <Route path="/test" element={<Test />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/listings" element={<Listings/>} />
          <Route path="/uploads" element={<Upload/>} />
          <Route path="/users/:user_id" element={<ViewUserProfile />} />
        </Routes>
      </BrowserRouter>
    </AuthenticationProvider>
  </StrictMode>,
)
