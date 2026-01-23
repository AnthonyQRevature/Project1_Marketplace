//CHANGED

import { useEffect, useState } from "react";
import "./Messages.css";

type MessageEntity = {
  messageId: number;
  senderId: number;
  receiverId: number;
  message: string;
  sentAt: string;
};

function makeSendEndpoint(sender: number, receiver : number)
{
  return {endpoint: `http://localhost:8080/messages/send?senderId=${sender}&receiverId=${receiver}`, method: "POST"};
}
const get_messages = (user1Id: number, user2Id: number) => ({
  endpoint: `http://localhost:8080/messages/conversation?user1Id=${user1Id}&user2Id=${user2Id}`,
  method: "GET"
});

export function MessagesTest()
{
  return (
    <Messages currentUserId={1} receiverId={2} />
  )
}

function Messages(props: { currentUserId: number; receiverId: number }) {
  const [messages, setMessages] = useState<MessageEntity[]>([]);
  const [text, setText] = useState("");

  // load messages for the current user and receiver
  useEffect(() => {
    fetch(get_messages(props.currentUserId, props.receiverId).endpoint)
      .then(res => res.json())
      .then(data => setMessages(data));
  }, [props.currentUserId, props.receiverId]);

  // send message
  const sendMessage = async () => {
    if (!text.trim()) return;

    const send = makeSendEndpoint(props.currentUserId, props.receiverId);
    await fetch(send.endpoint, {
      method: send.method,
      body: text
    });

    setText("");

    // refresh messages
    const res = await fetch(get_messages(props.currentUserId, props.receiverId).endpoint);
    setMessages(await res.json());
  };

  return (
    <div className="messages">
      <h3>Messages</h3>

      <div className="messagesList">
        {messages.map(msg => (
          <div key={msg.messageId} className="message">
            <b>{msg.senderId === props.currentUserId ? "You" : "Them"}:</b>
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
