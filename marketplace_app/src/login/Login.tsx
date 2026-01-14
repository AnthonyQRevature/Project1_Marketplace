import { useState } from "react";
import "./Login.css";
import { Link, useNavigate, type NavigateFunction } from "react-router";

const login = {endpoint: "http://localhost:8080/login", method: "POST"};

function Register()
{
  const [response, setResponse] = useState(null);
  const navigate = useNavigate();
  const formHandler = handleSubmit(setResponse, navigate);

  return (
    <>
      <form onSubmit={formHandler}>
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
    </>
  );
}

function handleSubmit(setResponse : (val : any)=>void, navigate : NavigateFunction)
{
  return async (e : any) => 
  {
    e.preventDefault();

    // Read the form data
    const form = e.target;
    const formData = new FormData(form);
    const formJson = Object.fromEntries(formData.entries());
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
        setResponse(await result.json());

        
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