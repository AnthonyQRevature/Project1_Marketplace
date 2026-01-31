import { useContext } from "react";
import AuthenticationContext from "../authentication/AuthenticationContext";
import { Link } from "react-router";
import "./AdminBar.css";

export default function AdminBar()
{
  const [auth, _] = useContext(AuthenticationContext);
  //UNDECIDED: either dont render or render but hide it
  return (
    auth.isAdmin()? <_AdminBar /> : <></>
  );
}
function _AdminBar()
{
  return (
    <p className="admin-bar">
      <Link to='/reports'><span className='danger'>view reports</span></Link>
      <Link to='/all-users'><span className='danger'>view all users</span></Link>
    </p>
  );
}