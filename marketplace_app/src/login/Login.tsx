import { useContext, useState } from "react";
import "./Login.css";
import { Link, useNavigate, type NavigateFunction } from "react-router";
import AuthenticationContext, { Authentication, type AuthenticationState } from "../authentication/AuthenticationContext";
import logo from '../assets/Logo.png'
import logError from "../util/logError";

const login = {endpoint: "http://localhost:8080/login", method: "POST"};

function Register()
{
  const [auth, setAuth] = useContext<AuthenticationState>(AuthenticationContext);
  const navigate = useNavigate();
  const formHandler = handleSubmit(setAuth, navigate);

  return (
    <>
      <div className="columns" >
        <form action={formHandler}>
          <div className = "loginBox">
            <div className ="logUserInput">
              <label htmlFor="username">Username: </label>
              <input type="text" name="username" id="username" required />
            </div>
            <div className="logPassInput">
              <label htmlFor="password">Password: </label>
              <input type="password" name="password" id="password" required />
            </div>
            <div className="logRegLink">
              <Link to="/register"><p className="rounded-button">Register an Account</p></Link>
            </div>
            <div className="logSubmit">
              <input type="submit" className="rounded-button" value="Login" />
            </div>
          </div>
        </form>
      <Message />
      </div>
    </>
  );
}

function handleSubmit(setAuth : (val : any) => void, navigate : NavigateFunction)
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
        //setResponse(response);

        //set auth
        setAuth(new Authentication(response));

        /*
         * navigate back to homepage once we recieve the response
         */
        navigate("/");
      }
      else
      {
        //setResponse(`Error: ${result.status}`);
      }
    }
    catch (e)
    {
      //setResponse("Server error");
      logError(e);
    }
    return;
  }
}

function Message()
{
  return (
    <div className="right">
      <p className="message">
        In 2018, 11.3 million tons of textiles ended up in landfills. While this includes furniture,
        carpets, and footwear, the majority of these textiles are discarded clothing.
      </p>
    </div>
  );
}

export default Register;