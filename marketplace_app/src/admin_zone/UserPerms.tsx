import { useContext, useEffect, useState, type ChangeEvent, type ChangeEventHandler } from "react";
import { Role, type UserProfile } from "../util/DataStructure";
import AuthenticationContext from "../authentication/AuthenticationContext";
import { useNavigate } from "react-router";
import useLoader from "../util/AsyncLoader";
import { UserBrief } from "../util/UserBrief";
import type { State } from "../util/State";
import type { Endpoint } from "../util/Endpoint";

const endpoint = {
  endpoint: "http://localhost:8080/users",
  method: "GET"
}

function makePermEndpoint(user_id : number) : Endpoint
{
  return {
    endpoint: `http://localhost:8080/users/${user_id}/perms`,
    method: "PATCH"
  }
}

export default function UserPermsPage()
{
  const [auth,] = useContext(AuthenticationContext);
  const nav = useNavigate();
  const [AsyncLoader, reset] = useLoader(endpoint);
  const [mod, setMod] = useState<Record<number, number>>({});
  useEffect(() => {auth.isAdmin() || nav("/login");}, [auth]);
  
  if (!auth.isAdmin())
  {
    return <></>;
  }

  return (
    <>
      <AsyncLoader then={UserList} foward={{modState: [mod, setMod], reset: reset}}/>
    </>
  );
}

function UserList(props : {resource : UserProfile[], modState : State<Record<number, number>>, reset : ()=>void})
{
  const [auth,] = useContext(AuthenticationContext);
  const {resource, modState, reset} = props;
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

  const handleSave = async function()
  {
    let requests = [];
    for (const [key, val] of Object.entries(mod))
    {
      const endpoint = makePermEndpoint(key as unknown as number);
      requests.push(fetch(endpoint.endpoint, {
        method: endpoint.method,
        headers:{
          Authorization: auth.encryptedToken,
          "Content-Type": "application/json"
        },
        body: val.toString()
      }));
    }
    for (const r in requests)
    {
      await r;
    }
    setMod({});
    reset();
  }

  return (
    <>
      <button onClick={handleSave}>Save</button>
      {resource.map(profile => 
        <div className="horizontal" key={profile.id}>
          <UserBrief resource={profile} >
            <ProfilePerms 
              mod={mod} 
              profile={profile} 
              handleChange={handleChange(profile.id)} 
              disabled={auth.role !== Role.SUPER_USER || auth.id === profile.id}
            />
          </UserBrief>
        </div>
      )}
    </>
  );
}

function ProfilePerms(props : {
  profile : UserProfile,
  mod : Record<number, number>, 
  handleChange : ChangeEventHandler<HTMLSelectElement>, 
  disabled : boolean}
) {
  const {profile, mod, handleChange, disabled} = props;

  let role = mod[profile.id] || profile.role;

  return (
    <>
      <p className={mod[profile.id]? "" : "hidden"}>*</p>
      <select onChange={handleChange} disabled={disabled}>
        <option value="user" selected={role === Role.USER}>User</option>
        <option value="admin" selected={role === Role.ADMIN}>Admin</option>
        <option value="super" selected={role === Role.SUPER_USER}>Super User</option>
      </select>
    </>
  );
}