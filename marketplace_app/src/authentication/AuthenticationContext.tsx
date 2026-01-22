import { createContext, useState, type Dispatch } from "react";
import type { JSX } from "react/jsx-runtime";

//this stuff is complicated enough to be wrapped in a class
export class Authentication {
  id = -1;
  username = "guest";
  encryptedToken = "";
};

export type AuthenticationState = [Authentication, Dispatch<React.SetStateAction<Authentication>>];

const AuthenticationContext = createContext<AuthenticationState>([new Authentication(), (_x)=>{}]);

export function AuthenticationProvider(props : {children: JSX.Element[] | JSX.Element})
{
  const [auth, setAuth] = useState<Authentication>(new Authentication());
  
  return (
    <AuthenticationContext value={[auth, setAuth]}>
      {props.children}
    </AuthenticationContext>
  );
}

export default AuthenticationContext;