import { useContext, useState } from "react";
import "./Upload.css";
import { Link, useNavigate, type NavigateFunction } from "react-router";
import AuthenticationContext, { type Authentication, type AuthenticationState } from "../authentication/AuthenticationContext";

const file_upload = {endpoint: "http://localhost:8080/files", method: "POST"};

function Upload()
{
  const [response, setResponse] = useState(null);

  return (
    <>
      <form action={handleSubmit(setResponse)}>
        <table>
          <thead>
            <tr><td>File to upload:</td><td><input type="file" name="file" /></td></tr>
            <tr><td></td><td><input type="submit" value="Upload" /></td></tr>
          </thead>
        </table>
      </form>
      <p>response: {JSON.stringify(response)}</p>
    </>
  );
}

function handleSubmit(setResponse : (val : any)=>void)
{
  return async (e : FormData) => 
  {
    // Read the form data
    console.log(e.get("file"));

    let response = await fetch(file_upload.endpoint, {
      method: file_upload.method,
      body: e,
      /*headers: {
        'Content-Type': 'multipart/form-data'
      }*/
    });

    setResponse(response);
  }
}

export default Upload;