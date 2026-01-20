import { useState } from "react";
import "./Upload.css";
import logError from "../util/logError";

const file_upload = {endpoint: "http://localhost:8080/users/3/listings/1/media", method: "POST"};
const token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJuZXdfdXNlciIsImp0aSI6IjMiLCJpYXQiOjE3Njg5MzgxNzEsImV4cCI6MTc2ODk4MTM3MX0.9B9XeC8k3nyYqw0HezVo7I89Yu3XcxSvTOcMmzSMo7A";

function Upload()
{
  const [response, setResponse] = useState<any>(null);
  const [preview, setPreview] = useState<string>("");

  let handleOnChange = (e: any) => 
  {
    let file : File;
    [file] = e.target.files;
    setPreview(URL.createObjectURL(file));
  }

  console.log(response);

  return (
    <>
      <form action={handleSubmit(setResponse)}>
        <table>
          <thead>
            <tr><td>File to upload:</td><td><input type="file" name="file" onChange={handleOnChange} /></td></tr>
            <tr><td></td><td><input type="submit" value="Upload" /></td></tr>
          </thead>
        </table>
        <ImagePreview url={preview} />
      </form>
      {response && (<img src={`data:image/png;base64,${response}`}/>)}
      <p>Response: {JSON.stringify(response)}</p>
    </>
  );
}
function ImagePreview(props : {url:string})
{
  let {url} = props;
  if (url)
  {
    return (
      <img src={url} />
    );
  }
  else
  {
    return (
      <></>
    );
  }
}
function handleSubmit(setResponse : (val : any)=>void)
{
  return async (e : FormData) => 
  {
    // Read the form data

    try
    {
      let response = await fetch(file_upload.endpoint, {
        method: file_upload.method,
        headers: {
          "Authorization": token
        },
        body: e
      });

      let string_result = await response.text();
      
      setResponse(string_result);
    }
    catch (e)
    {
      logError(e);
    }
  }
}

export default Upload;