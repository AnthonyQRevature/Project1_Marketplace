import { useContext, useState } from "react";
import "./Login.css";
import { Link, useNavigate, type NavigateFunction } from "react-router";
import AuthenticationContext, { Authentication, type AuthenticationState } from "../authentication/AuthenticationContext";

const login = {endpoint: "http://localhost:8080/login", method: "POST"};

function Register()
{
  const [response, setResponse] = useState(null);
  const [auth, setAuth] = useContext<AuthenticationState>(AuthenticationContext);
  const navigate = useNavigate();
  const formHandler = handleSubmit((val) => {
    setResponse(val);
  }, setAuth, navigate);

  return (
    <>
      <form action={formHandler}>
        <div>
          <label htmlFor="username">Username: </label>
          <input type="text" name="username" id="username" required />
        </div>
        <div>
          <label htmlFor="password">Password: </label>
          <input type="password" name="password" id="password" required />
        </div>
        <Link to="/register"><p>Register an Account</p></Link>
        <div>
          <input type="submit" value="Login" />
        </div>
      </form>

      <p>output: {JSON.stringify(response)}</p>
      <p>current authentication: {JSON.stringify(auth)}</p>
    </>
  );
}

function handleSubmit(setResponse : (val : any)=>void, setAuth : (val : any) => void, navigate : NavigateFunction)
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

        //set auth
        setAuth(new Authentication(response));

        /*
         * navigate back to homepage once we recieve the response
         */
        navigate("/");
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