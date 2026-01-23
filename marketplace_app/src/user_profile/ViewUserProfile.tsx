import { createContext, useContext, useState } from "react";
import "./ViewUserProfile.css";
import { Link, useNavigate, useParams, type NavigateFunction } from "react-router";
import AuthenticationContext, { Authentication, type AuthenticationState } from "../authentication/AuthenticationContext";
import { EncodedImage } from "../util/EncodedImage";
import type { Endpoint } from "../util/Endpoint";
import DeleteButton from "./DeleteButton";
import useLoader from "../util/AsyncLoader";

//get user profile endpoint
function makeEndpoint(id: number) : Endpoint
{
  return {endpoint: `http://localhost:8080/users/${id}`, method: "GET"};
}
function pfp_endpoint(id: number) : Endpoint
{
  return {endpoint: `http://localhost:8080/users/${id}/media`, method: "POST"};
}

type UserProfile = {
  id : number,
  username : string,
  email : string,
  profile: 
  {
    pfp_encoded : string,
    bio : string,
    latitude : number,
    longitude : number,
    distance : number
  }
}

function ViewUserProfile()
{
  const {user_id} = useParams();
  const [auth, _] = useContext(AuthenticationContext);
  const [AsyncLoader, reset] = useLoader<UserProfile>(makeEndpoint(Number(user_id)));

  return (
    <AsyncLoader foward={{reset: reset}} then={Display} otherwise={() => <p>Loading</p>}/>
  );
}

function Display(props : {resource : UserProfile, reset: () => void})
{
  const [editMode, setEditMode] = useState<boolean>(false);
  const {user_id} = useParams();
  const [auth, _] = useContext(AuthenticationContext);
  const profile = props.resource;
  
  let owner = false;
  if (Number(user_id) == auth.id)
  {
    owner = true;
  }
  
  if (!editMode)
  {
    return (
      <>
        <EncodedImage img={profile.profile.pfp_encoded} />
        {owner? <button onClick={()=>setEditMode(true)}>edit</button> : <></>}
        <h1>{profile.username}</h1>
        <p>{profile.email}</p>
        <p>BIO:</p>
        <p>{profile.profile.bio}</p>
        <p>Location: {profile.profile.latitude}, {profile.profile.longitude}</p>
      </>
    );
  }
  else
  {
    return (
      <DisplayEdit init={profile} reset={() => {setEditMode(false); props.reset();}}/>
    );
  }
}

function DisplayEdit(props : {init : UserProfile, reset : () => void})
{
  //const [profile, setProfile] = useState(props.init);
  const profile = props.init;
  const [preview, setPreview] = useState<string | null>(null);
  const [auth, _] = useContext(AuthenticationContext);
  const {user_id} = useParams();

  function handleOnChange(e : any) {
    let file : File;
    [file] = e.target.files;
    setPreview(URL.createObjectURL(file));
  }

  //a hack
  let submitEndpoint = makeEndpoint(Number(user_id));
  submitEndpoint.method = "PATCH";

  return (
    <>
      <DeleteButton />
      <form action={submitProfile(auth.encryptedToken, submitEndpoint, pfp_endpoint(Number(user_id)), profile, props.reset)}>
        {preview == null? <EncodedImage img={profile.profile.pfp_encoded} /> : <img src={preview} />}
        <input type="file" name="pfp" onChange={handleOnChange} />
        <h1>{profile.username}</h1>
        <label htmlFor="email">Email: </label><input type="email" name="email" defaultValue={profile.email} />
        <p>BIO:</p>
        <label htmlFor="bio">Bio: </label><textarea name="bio" defaultValue={profile.profile.bio} rows={5} cols={33} />
        <label htmlFor="latitude">Latitude: </label><input type="text" name="latitude" defaultValue={profile.profile.latitude} />
        <label htmlFor="longitude">Longitude: </label><input type="text" name="longitude" defaultValue={profile.profile.longitude} />
        <button type="submit">Submit</button>
      </form>
    </>
  );
}

function submitProfile(token : string, profile_endpoint : Endpoint, pfp_endpoint : Endpoint, init : UserProfile, reset : () => void)
{
  return async (e : FormData) => 
  {
    let struct : any = {};
    struct.email = e.get("email");
    struct.profile = {};
    struct.profile.pfp_encoded = init.profile.pfp_encoded;
    struct.profile.bio = e.get("bio");
    struct.profile.latitude = e.get("latitude");
    struct.profile.longitude = e.get("longitude");

    /*
    let request = fetch(profile_endpoint.endpoint, {
      method: profile_endpoint.method,
      headers: {
        "Authorization": token,
        "Content-Type": "application/json"
      },
      body: JSON.stringify(struct)
    });*/

    let request2 = null

    if ((e.get("pfp") as File).size > 0)
    {
      let rb = new FormData();
      rb.append("file", Object(e.get("pfp")));
      request2 = fetch(pfp_endpoint.endpoint, {
        method: pfp_endpoint.method,
        headers: {
          "Authorization": token
        },
        body: rb
      });
    }

    //maybe check these
    //await request;
    request2 && await request2;

    //sets edit mode to false and re requests the profile from the server
    reset();
  }
}

export default ViewUserProfile;