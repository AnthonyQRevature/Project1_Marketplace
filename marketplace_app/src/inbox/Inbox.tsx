import { useContext, useEffect } from "react";
import AuthenticationContext from "../authentication/AuthenticationContext";
import useLoader from "../util/AsyncLoader";
import { Link, useNavigate } from "react-router";
import type { Conversations, ProfileBrief } from "../util/DataStructure";
import { EncodedImage } from "../util/EncodedImage";

import "./Inbox.css"

function makeEndpoint(id : number)
{
  return {
    endpoint: `http://localhost:8080/messages/${id}`,
    method: "GET"
  }
}

export default function InboxPage()
{
  const [auth, _] = useContext(AuthenticationContext);
  const [AsyncLoader, reset] = useLoader(makeEndpoint(Number(auth.id)));
  const nav = useNavigate();

  if (auth.isGuest())
  {
    useEffect(() => {nav("/login")}, []);
    return <></>;
  }
  
  return (
    <>
      <h1>Inbox</h1>
      <AsyncLoader then={InboxConversations} otherwise={() => <p>Loading...</p>}/>
    </>
  )
}

function InboxConversations(props : {resource : Conversations})
{
  const {resource} = props;

  return (
    <>
      {resource.map(conv => <Conversation key={conv.id} brief={conv}/>)}
    </>
  )
}

function Conversation(props : {brief : ProfileBrief})
{
  const {brief} = props;

  return (
    <div className="conversation">
      <EncodedImage img={brief.pfp_encoded} />
      <Link to={`/message/${brief.id}`}><h2>{brief.username}</h2></Link>
    </div>
  )
}

