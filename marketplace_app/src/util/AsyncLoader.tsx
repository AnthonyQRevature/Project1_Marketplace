import { useEffect, useState, type JSX } from "react";
import logError from "./logError";

type Endpoint = {
  endpoint: string,
  method: string
};
type AsyncLoaderInit<T> = 
{
  then : (value : T) => JSX.Element, 
  repititions? : number,
  endpoint : Endpoint,
  otherwise? : JSX.Element,
  abort? : JSX.Element
};

/*
 * Component that makes a request to the backend and waits for a response before rendering
 * the component
 */
function AsyncLoader<T>(props : AsyncLoaderInit<T>)
{
  let otherwise = props.otherwise || <></>;
  let abort = props.abort || props.otherwise || <></>;
  let repititions = props.repititions || 5;
  const LOADER_STATE = 
  {
    LOADING: 0,
    ABORTED: 1
  }

  const [currentState, setState] = useState<T | number>(LOADER_STATE.LOADING);
  
  //try a certain number of times
  const async_fetch = async (rep: number) => {
    let i = 0;
    while (true) {
      try {
        const response = await fetch(props.endpoint.endpoint, { method: props.endpoint.method });
        const value: T = await response.json();
        setState(value);
        return;
      }
      catch (e) {
        //fail
        logError(e);
      }

      //failed
      i++;
      if (i < rep) {
        logError("Fetch request failed. Retrying.");
      }
      else {
        logError("Fetch request failed. Too many attempts. Aborting.")
        setState(LOADER_STATE.ABORTED);
        break;
      }
    }
  };
  useEffect(() => {async_fetch(repititions)}, []);

  if (currentState == LOADER_STATE.LOADING)
  {
    return otherwise;
  }
  else if (currentState == LOADER_STATE.ABORTED)
  {
    return abort;
  }
  else
  {
    //loaded the resource
    return props.then(currentState as T);
  }
}

export default AsyncLoader;