import { useContext } from "react";
import { Link } from "react-router";
import AuthenticationContext, { Authentication, type AuthenticationState } from "./authentication/AuthenticationContext";
import "./NavBar.css";

export default function NavBar()
{
const [auth, setAuth] = useContext<AuthenticationState>(AuthenticationContext);

  return (
    <div className="nav_bar">
      <div></div>
      <Link to={`/`} className="title">Secondhand</Link>
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
      <div className="profile"><Link to={`/users/${auth.id}`}><p>My Profile</p></Link></div>
    );
  }
  else
  {
    return (
      <div></div>
    )
  }
}