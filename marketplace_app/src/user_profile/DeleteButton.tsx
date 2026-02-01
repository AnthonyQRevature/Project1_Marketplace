import { useContext, useState } from 'react'
import './Buttons.css'
import { useNavigate, useParams, type NavigateFunction } from 'react-router';
import type { Endpoint } from '../util/Endpoint';
import AuthenticationContext, { Authentication } from '../authentication/AuthenticationContext';
import logError from '../util/logError';
import type { State } from '../util/State';

function makeEndpoint(user_id : number) : Endpoint 
{ 
  return {endpoint: `http://localhost:8080/users/${user_id}`, method: "DELETE"}; 
}

export default function DeleteButton()
{
  const [confirm, setConfirm] = useState<boolean>(false);
  const {user_id} = useParams();
  const authState = useContext(AuthenticationContext);
  const nav = useNavigate();
  const endpoint = makeEndpoint(Number(user_id));
  
  if (!confirm)
  {
    return (
      <div className='button-grid'>
        <button className='delete danger' onClick={() => setConfirm(true)}>Delete</button>
        <button className='danger hidden'></button>
      </div>
    );
  }
  else
  {
    return (
      <div className='delete-grid'>
        <button className='reset danger' onClick={() => setConfirm(false)}>Nevermind</button>
        <button className='confirm danger' onClick={deleteAccont(endpoint, authState, nav)}>Confirm</button>
      </div>
    )
  }
}

function deleteAccont(endpoint : Endpoint, authState : State<Authentication>, nav : NavigateFunction)
{
  const [auth, setAuth] = authState;

  return async () =>
  {
    const promise = fetch(endpoint.endpoint, {
      method: endpoint.method,
      headers: {
        Authorization: auth.encryptedToken
      }
    });

    const response = await promise;

    if (response.ok)
    {
      //set auth to guest
      setAuth(new Authentication());
      nav("/");
    }
    else
    {
      logError("delete request failed");
    }
  }
}