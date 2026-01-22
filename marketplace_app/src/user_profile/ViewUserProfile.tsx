import { createContext, useContext, useState } from "react";
import "./ViewUserProfile.css";
import { Link, useNavigate, useParams, type NavigateFunction } from "react-router";
import AuthenticationContext, { type Authentication, type AuthenticationState } from "../authentication/AuthenticationContext";
import AsyncLoader from "../util/AsyncLoader";
import { EncodedImage } from "../util/EncodedImage";

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
    encoded_pfp : string,
    bio : string,
    latitude : number,
    longitude : number,
    distance : number
  }
}
type UserProfileState = {
  username : string,
  encoded_pfp : state<string>,
  bio : state<string>
}
const userProfileContext = createContext<UserProfileState>({username: "", encoded_pfp: ["", (_x:string)=>{}], bio: ["", (_x:string)=>{}]});

function ViewUserProfile()
{
  const {user_id} = useParams();
  const [auth, _] = useContext(AuthenticationContext);

  const TMP_USR_PF : UserProfile = {
    id: 3,
    username: "SlickSalesman94",
    email: "my@email",
    profile: {
      encoded_pfp:"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",
      bio:"i am a very interesting person",
      latitude: 33.039172,
      longitude: -96.696956,
      distance:0
    }
  };
  
  return (
    <>
      {edit_user_profile_page(TMP_USR_PF)}
    </>
  )
  /*return (
    <AsyncLoader endpoint={makeEndpoint(Number(user_id))} then={edit_user_profile_page} otherwise={<p>Loading</p>}/>
  );*/
}

function edit_user_profile_page(initialProfile : UserProfile)
{
  console.log(initialProfile);
  return (
    <>
      <EncodedImage img={initialProfile.profile.encoded_pfp} />
      <h1>{initialProfile.username}</h1>
      <p>{initialProfile.email}</p>
      <p>BIO:</p>
      <p>{initialProfile.profile.bio}</p>
      <p>Location: {initialProfile.profile.latitude}, {initialProfile.profile.longitude}</p>
    </>
  )
}

export default ViewUserProfile;