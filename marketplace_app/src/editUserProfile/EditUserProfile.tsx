import { createContext, useContext, useState } from "react";
import "./EditUserProfile.css";
import { Link, useNavigate, type NavigateFunction } from "react-router";
import AuthenticationContext, { type Authentication, type AuthenticationState } from "../authentication/AuthenticationContext";
import AsyncLoader from "../util/AsyncLoader";

//const userProfile = {endpoint: "http://localhost:8080/login", method: "POST"};
function makeEndpoint(id: number)
{
  return {endpoint: `http://localhost:8080/users/${id}`, method: "GET"};
}

type state<T> = [T, (x : T) => void];
type UserProfile = {
  id : number,
  username : string,
  email : string,
  profile: 
  {
    encoded_pfp : state<string>,
    bio : state<string>,
    distance : number
  }
}
type UserProfileState = {
  username : string,
  encoded_pfp : state<string>,
  bio : state<string>
}
const userProfileContext = createContext<UserProfileState>({username: "", encoded_pfp: ["", (_x:string)=>{}], bio: ["", (_x:string)=>{}]});

function editUserProfile()
{
  const [auth, _] = useContext(AuthenticationContext);

  return (
    <AsyncLoader />
  );
}

function edit_user_profile_page(initialProfile : UserProfile)
{
  

  return (
    <>
      <h1>username</h1>

    </>
  )
}

function handleSubmit(setResponse : (val : any)=>void, _navigate : NavigateFunction)
{
  return async (e : FormData) => 
  {
    // Read the form data
    const formJson = Object.fromEntries(e.entries());
    try
    {
      const result = await fetch(login.endpoint, 
        { 
          method: login.method, 
          body: JSON.stringify(formJson),
          headers: new Headers({'content-type': 'application/json'})
        }
      );

      console.log(result);

      if (result.ok)
      {
        const response = await result.json();
        setResponse(response);

        /*
         * navigate back to homepage once we recieve the response
         */
        //navigate("/");
      }
      else
      {
        setResponse(`Error: ${result.status}`);
      }
    }
    catch (e)
    {
      setResponse("Server error");
      console.log(e);
    }
    return;
  }
}

export default Register;