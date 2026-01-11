import { useState } from "react";
import "./Register.css";

const register = {endpoint: "http://localhost:8080/login", method: "PUT"};

function Register()
{
  const [response, setResponse] = useState(null);
  const formHandler = handleSubmit(setResponse);

  return (
    <>
      <form onSubmit={formHandler} className="form-example">
        <div className="form-example">
          <label htmlFor="username">Enter your name: </label>
          <input type="text" name="username" id="username" required />
        </div>
        <div className="form-example">
          <label htmlFor="password">Enter your password: </label>
          <input type="password" name="password" id="password" required />
        </div>
        <div className="form-example">
          <label htmlFor="email">Enter your email: </label>
          <input type="email" name="email" id="email" required />
        </div>
        <div className="form-example">
          <input type="submit" value="register" />
        </div>
      </form>

      <p>output: {JSON.stringify(response)}</p>
    </>
  );
}

function handleSubmit(setResponse : (val : any)=>void)
{
  return async (e : any) => 
  {
    e.preventDefault();

    // Read the form data
    const form = e.target;
    const formData = new FormData(form);
    const formJson = Object.fromEntries(formData.entries());

    const result = await fetch(register.endpoint, 
      { 
        method: register.method, 
        body: JSON.stringify(formJson),
        headers: new Headers({'content-type': 'application/json'})
      });

    console.log(result);

    if (result.ok)
      setResponse(await result.json());
    else
      setResponse(result.status);
  }
}

export default Register;