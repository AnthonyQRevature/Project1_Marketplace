//CHANGED

import { useContext, useEffect, useState } from "react";
import "./Messages.css";
import AuthenticationContext from "../authentication/AuthenticationContext";
import { Link, useNavigate, useParams } from "react-router";
import { Role, type Message_t, type Messages, type ProfileBrief, type UserProfile } from "../util/DataStructure";
import { EncodedImage } from "../util/EncodedImage";

import refresh from "../assets/refresh.png";
import useLoader, { useLoaderses } from "../util/AsyncLoader";
import ProfileSocialButtons from "../user_profile/SocialButtons";
import type { Endpoint } from '../util/Endpoint';
import flag_icon from '../assets/flag_lmao.png';
import { useAuthGuard } from "../util/AuthGuard";

type MessageEntity = {
  messageId: number;
  senderId: number;
  receiverId: number;
  message: string;
  sentAt: string;
};

function makeProfileEndpoint(user_id : number)
{
  return {
    endpoint: `http://localhost:8080/users/${user_id}`,
    method: "GET"
  }
}
function makeSendEndpoint(sender: number, receiver : number)
{
  return {endpoint: `http://localhost:8080/messages/send?senderId=${sender}&receiverId=${receiver}`, method: "POST"};
}
const get_messages = (user1Id: number, user2Id: number) => ({
  endpoint: `http://localhost:8080/messages/conversation?user1Id=${user1Id}&user2Id=${user2Id}`,
  method: "GET"
});
function makeReportEndpoint(target : number) : Endpoint
{
  return {
    endpoint: `http://localhost:8080/users/${target}/reports`,
    method: "POST"
  };
}
/*
export function MessagesTest()
{
  return (
    <Messages currentUserId={1} receiverId={2} />
  )
}
*/

export default function MessagePage()
{
  const [auth, _] = useContext(AuthenticationContext)
  const {user_id} = useParams();
  const guard = useAuthGuard(Role.USER);
  
  //ideally we would seperate get_messages into it's own loader
  const [AsyncLoader, reset] = useLoaderses([
    makeProfileEndpoint(auth.id),
    makeProfileEndpoint(Number(user_id)),
    get_messages(auth.id, Number(user_id))
  ]);

  if (guard())
  {
    return <></>;
  }

  return (<AsyncLoader then={Messages} foward={{reset: reset}} />)
}

function Messages(props: { resources: [UserProfile, UserProfile, Messages], reset: () => void}) {
  const [my_profile, their_profile, messages] = props.resources;
  const reset = props.reset;
  const [text, setText] = useState(""); // isnt this a bit wierd? this will rerender the component whenever the user types in the message box
  const [auth, _] = useContext(AuthenticationContext);

  // send message
  const sendMessage = async () => {
    if (!text.trim()) return;

    const send = makeSendEndpoint(my_profile.id, their_profile.id);
    await fetch(send.endpoint, {
      method: send.method,
      body: text
    });

    setText("");

    // refresh messages
    reset();
  };

  return (
    <>
      <div className="messages">
        <h3>Messages</h3>

        <div className="messagesList">
          {messages.map(msg => <Message key={msg.messageId} message={msg} my_profile={my_profile} their_profile={their_profile} />)}
        </div>

        <input
          value={text}
          onChange={e => setText(e.target.value)}
          placeholder="Type a message..."
        />

        <div className="buttons">
          <button onClick={sendMessage}>Send</button>
          <img className="icon link" src={refresh} onClick={reset}/>
        </div>
      </div>
      <Attribution />
    </>
  );
}

function Message(props : {message: Message_t, my_profile : UserProfile, their_profile : UserProfile})
{
  const {message, my_profile, their_profile} = props;
  const [auth, _] = useContext(AuthenticationContext);

  const report = async (message_report:Message_t) =>
  {

    let reporter:Number = my_profile.id;
    let reported:Number = their_profile.id;

    const reason = prompt("Reason");

    if(reason != null)
    {
      const endpoint = makeReportEndpoint(Number(reported));
      const response = await fetch(endpoint.endpoint, {
        method: endpoint.method,
        body: JSON.stringify({
          reporter_id: reporter,
          reported_id: reported,
          reason: reason,
          message_id:Number(message_report.messageId)
        }),
        headers: {
          "Content-Type": "application/json",
          Authorization: auth.encryptedToken
        }
      });

      if (response.ok)
      {
        alert("your response has been successfully submitted");
      }
    }
  }
  
  if (message.senderId === my_profile.id)
  {
    //my message
    return (
      <div className="message-box">
        <Link to={`/users/${my_profile.id}`}><EncodedImage img={my_profile.profile.pfp_encoded} /></Link>
        <div className="message">
          <b>{my_profile.username}</b>
          <span>{message.message}</span>
        </div>
      </div>
    );
  }
  else
  {
    return (
      <div className="message-box reverse">
        <Link to={`/users/${their_profile.id}`}><EncodedImage img={their_profile.profile.pfp_encoded} /></Link>
        <div className="message reverse">
          <span onClick={() => report(message)} className="MessageIndividual">{message.message}</span>
          <b>{their_profile.username}</b>
        </div>
      </div>
    );
  }
}

function Attribution()
{
  return <a href="https://www.flaticon.com/free-icons/refresh" title="refresh icons">Refresh icons created by Arkinasi - Flaticon</a>;
}

/*import { useEffect, useState } from "react";
import "./Messages.css";

type MessageEntity = {
  message_id: number;
  sender_id: number;
  receiver_id: number;
  post_id: number;
  message: string;
  sent_at: string;
};

const get_messages = (postId: number) => ({
  endpoint: `http://localhost:8080/messages/post/${postId}`,
  method: "GET"
});

function Messages(props: { postId: number; currentUserId: number; receiverId: number }) {
  const [messages, setMessages] = useState<MessageEntity[]>([]);
  const [text, setText] = useState("");

  // load messages for a post
  useEffect(() => {
    fetch(get_messages(props.postId).endpoint)
      .then(res => res.json())
      .then(data => setMessages(data));
  }, [props.postId]);

  // send message
  const sendMessage = async () => {
    if (!text.trim()) return;

    await fetch("http://localhost:8080/messages/send", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: new URLSearchParams({
        senderId: props.currentUserId.toString(),
        receiverId: props.receiverId.toString(),
        postId: props.postId.toString(),
        message: text
      })
    });

    setText("");

    // refresh messages
    const res = await fetch(get_messages(props.postId).endpoint);
    setMessages(await res.json());
  };

  return (
    <div className="messages">
      <h3>Messages</h3>

      <div className="messagesList">
        {messages.map(msg => (
          <div key={msg.message_id} className="message">
            <b>{msg.sender_id === props.currentUserId ? "You" : "Them"}:</b>{" "}
            <span>{msg.message}</span>
          </div>
        ))}
      </div>

      <input
        value={text}
        onChange={e => setText(e.target.value)}
        placeholder="Type a message..."
      />

      <button onClick={sendMessage}>Send</button>
    </div>
  );
}

export default Messages;
*/
