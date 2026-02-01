import { useContext, useState } from "react";
import { Link, Navigate, useNavigate, type NavigateFunction } from "react-router";
import AuthenticationContext, { Authentication, type AuthenticationState } from "./authentication/AuthenticationContext";
import "./NavBar.css";

export default function NavBar()
{
  const [auth] = useContext<AuthenticationState>(AuthenticationContext);

  return (
    <div className="nav_bar">
      <div></div>
      <Link to={`/`} className="nav_title">Secondhand</Link>
      <MyProfile />
    </div>
  )
}

function MyProfile()
{
  const [auth, setAuth] = useContext(AuthenticationContext);

  if (!auth.isGuest())
  {
    const handleLogout = () => {
      setAuth(new Authentication());
      //nav("/");
    }

    return (
      <div className="right"><Link to={`/users/${auth.id}`} className="nav_profile"><p>My Profile</p></Link><p onClick={handleLogout}>Logout</p></div>
    );
  }
  else
  {
    return (
      <div className="right"><Link to={"/login"} className="nav_login"><p>Login</p></Link></div>
    )
  }
}