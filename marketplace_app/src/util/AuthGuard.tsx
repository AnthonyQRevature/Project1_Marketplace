import { useContext, useEffect } from "react";
import type { Authentication } from "../authentication/AuthenticationContext";
import AuthenticationContext from "../authentication/AuthenticationContext";
import { useNavigate } from "react-router";


export function useAuthGuard(required_role : number)
{
  const [auth,] = useContext(AuthenticationContext);
  const nav = useNavigate();

  useEffect(() =>
  {
    if (auth.role < required_role)
    {
      nav("/login");
    }
  }, [auth]);

  function guard()
  {
    return auth.role < required_role;
  }

  return guard;
}
