import { useContext } from 'react';
import flag_icon from '../assets/flag_lmao.png'
import AuthenticationContext, { Authentication } from '../authentication/AuthenticationContext';
import useLoader from '../util/AsyncLoader';
import type { Endpoint } from '../util/Endpoint';
import "./buttons.css"
import type { Blocks } from '../util/DataStructure';
import { useParams } from 'react-router';

function makeBlockGetter(id : number) : Endpoint 
{
  return {
    endpoint: `http://localhost:8080/users/${id}/blocks`,
    method: "GET"
  }
}
function makeBlockSetter(id : number) : Endpoint 
{
  return {
    endpoint: `http://localhost:8080/users/${id}/blocks`,
    method: "POST"
  }
}
function makeBlockDeleter(id : number) : Endpoint 
{
  return {
    endpoint: `http://localhost:8080/users/${id}/blocks`,
    method: "DELETE"
  }
}
function makeReportEndpoint(target : number) : Endpoint
{
  return {
    endpoint: `http://localhost:8080/users/${target}/reports`,
    method: "POST"
  };
}

export default function ProfileSocialButtons()
{
  const [auth, _] = useContext(AuthenticationContext);
  const blockEndpoint = makeBlockGetter(auth.id);
  const [AsyncLoader, reset] = useLoader(blockEndpoint);

  if (auth.isGuest())
  {
    return <></>;
  }
  else
  {
    return (
      <div className='button-grid'>
        <AsyncLoader then={BlockButton} foward={{reset: reset}}/>
        <ReportButton />
      </div>
    );
  }
}

function BlockButton(props : {resource: Blocks, reset: () => void})
{
  const {resource, reset} = props;
  const [auth, _] = useContext(AuthenticationContext);
  const {user_id} = useParams();
  const blocked = resource.find((e) => e.blocked == Number(user_id)) != null;

  if (!blocked)
    return (
      <button className='block' onClick={() => block(auth.id, Number(user_id), auth, reset)}>Block</button>
    );
  else
    return (
      <button className='block' onClick={() => unblock(auth.id, Number(user_id), auth, reset)}>Unblock</button>
    );
}
async function block(from : number, to : number, auth : Authentication, reset : () => void)
{
  const endpoint = makeBlockSetter(from);
  const response = await fetch(endpoint.endpoint, {
    method: endpoint.method,
    headers: {
      "Content-Type": "application/json",
      Authorization: auth.encryptedToken
    },
    body: JSON.stringify({
      id_blocked: to
    })
  });

  reset();
}
async function unblock(from : number, to : number, auth : Authentication, reset : () => void)
{
  const endpoint = makeBlockDeleter(from);
  const response = await fetch(endpoint.endpoint, {
    method: endpoint.method,
    headers: {
      "Content-Type": "application/json",
      Authorization: auth.encryptedToken
    },
    body: JSON.stringify({
      id_blocked: to
    })
  });

  reset();
}

function ReportButton()
{
  const [auth, _] = useContext(AuthenticationContext);
  const {user_id} = useParams();

  const report = async () =>
  {
    const reason = prompt("Reason");

    console.log(reason);

    const endpoint = makeReportEndpoint(Number(user_id));
    const response = await fetch(endpoint.endpoint, {
      method: endpoint.method,
      body: JSON.stringify({
        reporter_id: auth.id, 
        reported_id: Number(user_id),
        reason: reason
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

  return (
    <img className='icon report' onClick={report} src={flag_icon} />
  )
}