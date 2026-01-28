import { useContext, useEffect, useState, type FC } from "react";
import type { Endpoint } from "./Endpoint";
import AsyncLoader from "./AsyncLoaderPlain";
import logError from "./logError";
import AuthenticationContext from "../authentication/AuthenticationContext";

type AsyncLoaderInit<T> = 
{
  foward? : any,
  then : FC<any>, 
  otherwise? : FC,
  abort? : FC
};

type AsyncLoader<T> = FC<AsyncLoaderInit<T>>;

//this thing isnt perfect
//if i had a better understanding of typescript i could fix the lack of type safety
//the loader state is shared with the data when they should be seperate
export default function useLoader<T>(endpoint : Endpoint, repititions : number = 5) : [AsyncLoader<T>, () => void]
{
  const LOADER_STATE = 
  {
    LOADING: 0,
    ABORTED: 1
  }
  
  const [currentState, setState] = useState<T | number>(LOADER_STATE.LOADING);

  const resetFunction = () => 
  {
    setState(LOADER_STATE.LOADING);
  }
  
  const LoaderComponent = (props : AsyncLoaderInit<T>) =>
  {
    let Then = props.then;
    let Otherwise : FC = props.otherwise || (() => <></>);
    let Abort : FC = props.abort || props.otherwise || (() => <></>);

    const [auth, _] = useContext(AuthenticationContext);

    //run fetch up to rep times
    const async_fetch = async (rep: number) => {
      let i = 0;
      while (true) {
        try {
          const response = await fetch(endpoint.endpoint, { 
            method: endpoint.method,
            headers: {
              Authorization: auth.encryptedToken
            }
          });
          if (response.ok)
          {
            const value: T = await response.json();
            setState(value);
            return;
          }
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

    //run whenever currentState is set to LOADING
    useEffect(() => {
      if (currentState === LOADER_STATE.LOADING)
      async_fetch(repititions)
    }, [currentState]);

    if (currentState === LOADER_STATE.LOADING)
    {
      return (<Otherwise />);
    }
    else if (currentState === LOADER_STATE.ABORTED)
    {
      return (<Abort />);
    }
    else
    {
      //loaded the resource
      const resource = currentState as T;
      return (<Then {...{resource: resource, ...props.foward}}/>);
    }
  }
  return [LoaderComponent, resetFunction];
}

export function useLoaderses<T>(endpoints : Endpoint[], repititions : number = 5) : [AsyncLoader<T>, () => void]
{
  const LOADER_STATE = 
  {
    LOADING: 0,
    ABORTED: 1,
    DONE: 2
  }
  
  const [currentState, setState] = useState<number>(LOADER_STATE.LOADING);
  const [currentData, setData] = useState<T[] | null>(null);

  const resetFunction = () => 
  {
    setState(LOADER_STATE.LOADING);
  }
  
  const LoaderComponent = (props : AsyncLoaderInit<T>) =>
  {
    let Then = props.then;
    let Otherwise : FC = props.otherwise || (() => <></>);
    let Abort : FC = props.abort || props.otherwise || (() => <></>);

    const [auth, _] = useContext(AuthenticationContext);

    //run fetch up to rep times
    const async_fetch = async (rep: number, endpoint : Endpoint) => {
      let i = 0;
      while (true) {
        try {
          const response = await fetch(endpoint.endpoint, { 
            method: endpoint.method,
            headers: {
              Authorization: auth.encryptedToken
            }
          });
          if (response.ok)
          {
            const value: T = await response.json();
            return value;
          }
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
          break;
        }
      }
      return LOADER_STATE.ABORTED;
    };

    //run whenever currentState is set to LOADING
    useEffect(() => {
      let runner = async () => {
        if (currentState !== LOADER_STATE.LOADING) return;
        
        let aborted = false;
        let result = new Array<T>(endpoints.length);
        let promises = endpoints.map((endpoint) => async_fetch(repititions, endpoint));

        for (let i = 0; i < result.length; i++)
        {
          let val = await promises[i];
          if (val !== LOADER_STATE.ABORTED)
            result[i] = val as T;
          else
          {
            //if one of the operations aborts then we set the loader state to aborted (maybe keep going as to not waste the fetch request)
            aborted = true;
          }
        }
        
        setData(result);
        if (!aborted)
          setState(LOADER_STATE.DONE);
        else
          setState(LOADER_STATE.ABORTED);
      };
      runner();
    }, [currentState]);

    //only resort to Otherwise if there is no data present
    if (currentState === LOADER_STATE.LOADING && currentData == null)
    {
      return (<Otherwise />);
    }
    else if (currentState === LOADER_STATE.ABORTED)
    {
      return (<Abort />);
    }
    else
    {
      //loaded the resource
      const resources = currentData as T[];
      return (<Then {...{resources: resources, ...props.foward}}/>);
    }
  }
  return [LoaderComponent, resetFunction];
}