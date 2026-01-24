import { useContext } from 'react';
import flag_icon from '../assets/flag_lmao.png'
import AuthenticationContext from '../authentication/AuthenticationContext';
import useLoader from '../util/AsyncLoader';
import type { Endpoint } from '../util/Endpoint';
import "./buttons.css"

function makeBlockGetter(id : number) : Endpoint {}
function makeBlockSetter(id : number) : Endpoint {}

export default function ProfileSocialButtons()
{
  const [auth, _] = useContext(AuthenticationContext);
  const blockEndpoint = makeBlockGetter(auth.id);
  const [AsyncLoader, reset] = useLoader(blockEndpoint);

  return (
    <div className='button-grid'>
    <button className='block' onClick={() => {}}>Block</button>
    <img className='icon report' onClick={() => {}} src={flag_icon} />
    </div>
  );
}

function BlockButton(props : {resource: })
{
  
}