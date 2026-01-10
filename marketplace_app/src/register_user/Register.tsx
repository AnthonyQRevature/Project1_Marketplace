import { useState } from "react";
import "./Register.css";

function Register()
{
  const [response, setResponse] = useState(null);
  const formHandler = handleSubmit(setResponse);

  return (
    <>
      <form method="put" onSubmit={formHandler} className="form-example">
        <div className="form-example">
          <label htmlFor="name">Enter your name: </label>
          <input type="text" name="name" id="name" required />
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

    const result = await fetch('http://localhost:8080/echo', { method: 'POST', body: JSON.stringify(formJson) });

    setResponse(await result.json());
  }
}

export default Register;