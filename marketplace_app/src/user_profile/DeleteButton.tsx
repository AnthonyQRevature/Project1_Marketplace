import { useState } from 'react'
import './DeleteButton.css'
import { useParams } from 'react-router';

export default function DeleteButton(props : any)
{
  const [confirm, setConfirm] = useState<boolean>(false);
  const {user_id} = useParams();
  
  if (!confirm)
  {
    return (
      <div className='delete-grid'>
        <button className='delete danger' onClick={() => setConfirm(true)}>Delete</button>
      </div>
    );
  }
  else
  {
    return (
      <div className='delete-grid'>
        <button className='reset danger' onClick={() => setConfirm(false)}>Nevermind</button>
        <button className='confirm danger'>Confirm</button>
      </div>
    )
  }
}