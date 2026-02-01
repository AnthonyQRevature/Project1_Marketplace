import { StrictMode, useEffect } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter, Routes, Route } from "react-router"; //React Router
import Homepage from './homepage/Homepage.tsx';
import Test from './Test.tsx';
import Register from './register_user/Register.tsx';
//import Listings from './listings/Listings.tsx';
import Login from './login/Login.tsx'
import AuthenticationContext from './authentication/AuthenticationContext.tsx';
import { AuthenticationProvider } from './authentication/AuthenticationContext.tsx';
import Upload from './file_upload_test/Upload.tsx';
import Messages, { MessagesTest } from './listings/Messages.tsx';
import ViewUserProfile from './user_profile/ViewUserProfile.tsx';
import Report from './report/Report.tsx'
import UserReports from './report/UserReportOf.tsx'
import UsersReport from './report/UserReportFrom.tsx'
import NavBar from './NavBar.tsx';
import InboxPage from './inbox/Inbox.tsx';
import CreatePost from './createpost/CreatePost.tsx';
import ViewPost from './view_post/ViewPost.tsx';
import { OverlayScrollbarsComponent } from 'overlayscrollbars-react';

import './index.css';
import { OverlayScrollbars } from 'overlayscrollbars';
import 'overlayscrollbars/overlayscrollbars.css';
import MessagePage from './message/Messages.tsx';
import UserPermsPage from './admin_zone/UserPerms.tsx';

AuthenticationContext;

const rootElement = document.getElementById("root")!;
const root = createRoot(rootElement);
const osInstance = OverlayScrollbars(document.body, { scrollbars: { autoHide: "scroll" } });

root.render(
  <StrictMode>
    <ReactRoot />
  </StrictMode>
)

function ReactRoot()
{
  /*
  useEffect(() => {
    const osInstance = OverlayScrollbars(document.querySelector('#viewport') as HTMLElement, {paddingAbsolute:true});
  }, []);*/
  

  return (
    <AuthenticationProvider>
      <BrowserRouter>
        <NavBar />
        <div id='page-root'>
          <Routes>
            <Route path="/" element={<Homepage />} />
            <Route path="/test" element={<Test />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/listings" element={<Homepage/>} />
            <Route path="/users/:user_id" element={<ViewUserProfile />} />
            <Route path="/listings/:postId" element={<ViewPost/>}/>

            {/* User */}
            <Route path="/createpost" element={<CreatePost/>}/>
            <Route path="/inbox" element={<InboxPage />} />
            <Route path="/message/:user_id" element={<MessagePage />} />

            {/* Admin */}
            <Route path="/reports" element={<Report />} />
            <Route path="/all-users" element={<UserPermsPage />} />

            {/*I dont think we need these?*/}
            <Route path="/users/:user_id/reports/of" element={<UserReports />} />
            <Route path="/users/:user_id/reports/from" element={<UsersReport />} />
          </Routes>
        </div>
      </BrowserRouter>
    </AuthenticationProvider>
  )
}
