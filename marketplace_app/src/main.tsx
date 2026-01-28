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
import MessagePage from './message/Messages.tsx';
import ViewUserProfilePage from './user_profile/ViewUserProfile.tsx';
import Report from './report/Report.tsx'
import UserReports from './report/UserReportOf.tsx'
import UsersReport from './report/UserReportFrom.tsx'
import NavBar from './NavBar.tsx';
import InboxPage from './inbox/Inbox.tsx';

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
          <Route path="/users/:user_id" element={<ViewUserProfilePage />} />
          <Route path="/inbox" element={<InboxPage />} />
          <Route path="/message/:user_id" element={<MessagePage />} />
          <Route path="/reports" element={<Report />} />
          <Route path="/users/:user_id/reports/of" element={<UserReports />} />
          <Route path="/users/:user_id/reports/from" element={<UsersReport />} />
        </Routes>
      </BrowserRouter>
    </AuthenticationProvider>
  </StrictMode>,
)