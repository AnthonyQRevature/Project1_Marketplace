import { Link } from "react-router";
import useLoader from "./AsyncLoader";
import type { UserProfile } from "./DataStructure";
import { EncodedImage } from "./EncodedImage";
import { Children } from "react";


export default function LoadBrief(props : {user_id : number, lhs : string})
{
  const {user_id, lhs} = props;
  const endpoint = {
    endpoint: `http://localhost:8080/users/${user_id}`,
    method: "GET"
  }
  const [AsyncLoader, _] = useLoader(endpoint);

  return (
    <div className="inline">
      <p>{lhs}</p>
      <AsyncLoader then={UserBrief} otherwise={() => <p>...</p>}/>
    </div>
  )
}

export function UserBrief(props : {resource : UserProfile, children : React.ReactNode})
{
  const profile = props.resource;
  const {children} = props;

  return (
    <div className="inline">
      {children}
      <Link className="inline" to={`/users/${profile.id}`}>
        <EncodedImage img={profile.profile.pfp_encoded}/>
        <p>{profile.username}</p>
      </Link>
    </div>
  )
}