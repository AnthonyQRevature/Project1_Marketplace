import { createContext, useState, type Dispatch } from "react";
import type { JSX } from "react/jsx-runtime";

//this stuff is complicated enough to be wrapped in a class
export type Authentication = 
{
  userId: number
  username: string
  token: string
} | {};

export type AuthenticationState = [Authentication, Dispatch<React.SetStateAction<Authentication>>];

const AuthenticationContext = createContext<AuthenticationState>([{}, (_x)=>{}]);

export function AuthenticationProvider(props : {children: JSX.Element})
{
  const [auth, setAuth] = useState<Authentication>({});
  
  return (
    <AuthenticationContext value={[auth, setAuth]}>
      {props.children}
    </AuthenticationContext>
  );
}

export default AuthenticationContext;