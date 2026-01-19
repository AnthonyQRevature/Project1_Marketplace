import { useState } from "react";
import "./Upload.css";
import logError from "../util/logError";

const file_upload = {endpoint: "http://localhost:8080/media", method: "POST"};

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
        body: e
      });
      
      setResponse(response);
    }
    catch (e)
    {
      logError(e);
    }
  }
}

export default Upload;