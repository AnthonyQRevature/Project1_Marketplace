import { useEffect, useState, type FC } from "react";
import type { Endpoint } from "./Endpoint";
import AsyncLoader from "./AsyncLoaderPlain";
import logError from "./logError";

type AsyncLoaderInit<T> = 
{
  foward? : any,
  then : FC<any>, 
  otherwise? : FC,
  abort? : FC
};

type AsyncLoader<T> = FC<AsyncLoaderInit<T>>;

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

    //run fetch up to rep times
    const async_fetch = async (rep: number) => {
      let i = 0;
      while (true) {
        try {
          const response = await fetch(endpoint.endpoint, { method: endpoint.method });
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

    //run whenever currentState is set to LOADING
    useEffect(() => {
      if (currentState === LOADER_STATE.LOADING)
      async_fetch(repititions)
    }, [currentState]);

    if (currentState == LOADER_STATE.LOADING)
    {
      return (<Otherwise />);
    }
    else if (currentState == LOADER_STATE.ABORTED)
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