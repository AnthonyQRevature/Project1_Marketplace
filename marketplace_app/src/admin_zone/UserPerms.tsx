import { useContext, useEffect, useState, type ChangeEvent } from "react";
import { Role, type UserProfile } from "../util/DataStructure";
import AuthenticationContext from "../authentication/AuthenticationContext";
import { useNavigate } from "react-router";
import useLoader from "../util/AsyncLoader";
import { UserBrief } from "../util/UserBrief";
import type { State } from "../util/State";

const endpoint = {
  endpoint: "http://localhost:8080/users",
  method: "GET"
}

export default function UserPermsPage()
{
  const [auth,] = useContext(AuthenticationContext);
  const nav = useNavigate();
  const [AsyncLoader, reset] = useLoader(endpoint);
  const [mod, setMod] = useState<any>({});
  useEffect(() => {auth.isAdmin() || nav("/login");}, [auth]);
  
  if (!auth.isAdmin())
  {
    return <></>;
  }

  return (
    <>
      <AsyncLoader then={UserList} foward={{modState: [mod, setMod]}}/>
    </>
  );
}

function UserList(props : {resource : UserProfile[], modState : State<any>})
{
  const [auth,] = useContext(AuthenticationContext);
  const {resource, modState} = props;
  const [mod, setMod] = modState;
  
  //bind user id
  const handleChange = function(user_id : number)
  {
    return (e : ChangeEvent<HTMLSelectElement>) =>
    {
      let setPerm = Role.USER;
      switch(e.target.value)
      {
        case "user":
          setPerm = Role.USER;
          break;
        case "admin":
          setPerm = Role.ADMIN;
          break;
        case "super":
          setPerm = Role.SUPER_USER;
          break;
      }

      let o = {...mod};
      o[user_id] = setPerm;
      setMod(o);
    };
  }

  const handleSave = function()
  {
    
  }

  return (
    <>
      <button onClick={handleSave}>Save</button>
      {resource.map(profile => 
        <div className="horizontal" key={profile.id}>
          <UserBrief resource={profile} >
            <select onChange={handleChange(profile.id)} disabled={auth.role !== Role.SUPER_USER}>
              <option value="user" selected={profile.role === Role.USER}>User</option>
              <option value="admin" selected={profile.role === Role.ADMIN}>Admin</option>
              <option value="super" selected={profile.role === Role.SUPER_USER}>Super User</option>
            </select>
          </UserBrief>
        </div>
      )}
    </>
  );
}