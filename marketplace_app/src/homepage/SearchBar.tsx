import { useNavigate, type NavigateFunction } from "react-router";
import type { UserProfile } from "../util/DataStructure";

function makeEndpoint(username : String)
{
  return {endpoint:`http://localhost:8080/users/by-username/${username}`, method:"GET"}
}

export default function SearchBar()
{
  return (
    <form action={findProfile(useNavigate())}>
    <p className="search-label">find user</p>
    <input type="text" name="username" />
    <button type="submit" >Submit</button>
    </form>
  )
}

function findProfile(nav : NavigateFunction)
{
  return async (formData : FormData) => {
    const uname = formData.get("username") as string;
    const endpoint = makeEndpoint(uname);
    try
    {
      const result = await fetch(endpoint.endpoint, {
        method: endpoint.method
      });

      if (result.ok)
      {
        let acct : UserProfile = await result.json();
        nav(`/users/${acct.id}`);
        return;
      }
      else
      {
        //unsuccessful
      }
    }
    catch (e)
    {
      //unsuccessful
    }
    
    alert(`unable to find user with the username '${uname}'`);
    return;
  }
}