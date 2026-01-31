import { useContext } from "react";
import { Link } from "react-router";
import AuthenticationContext, { Authentication, type AuthenticationState } from "./authentication/AuthenticationContext";
import "./NavBar.css";

export default function NavBar()
{
  const [auth] = useContext<AuthenticationState>(AuthenticationContext);

  return (
    <div className="nav_bar">
      <div></div>
      <Link to={`/`} className="nav_title">Secondhand</Link>
      <MyProfile auth={auth}/>
    </div>
  )
}

function MyProfile(props : {auth: Authentication})
{
  const {auth} = props;
  if (!auth.isGuest())
  {
    return (
      <div className="right"><Link to={`/users/${auth.id}`} className="nav_profile"><p>My Profile</p></Link></div>
    );
  }
  else
  {
    return (
      <div className="right"><Link to={"/login"} className="nav_login"><p>Login</p></Link></div>
    )
  }
}