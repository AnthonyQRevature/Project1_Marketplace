import Head from './Head.tsx'
import { Link } from 'react-router';
import SearchBar from './SearchBar.tsx';
import AuthenticationContext from '../authentication/AuthenticationContext.tsx';
import { useContext } from 'react';

import './Homepage.css'

function Homepage()
{
  const [auth, _] = useContext(AuthenticationContext)

  return (
    <>
      <Head />
      <div className="cards">
        <div className="card">
          <Link to="/login"><p>Login</p></Link>
        </div>
        <div className="card">
          <LinkTo path="/listings" label='Listings' />
        </div>
        <div className="card">
          <p>EMPTY USE THIS FOR SOMETHING</p>
        </div>
        <div className="card">
          <SearchBar />
        </div>
      </div>
      {auth.isAdmin()? <Link to='/reports'><p className='danger'>view reports</p></Link> : <></>}
    </>
  );
}

function LinkTo(props: {path: string, label: string})
{
  return (<Link to={props.path}><p>{props.label}</p></Link>);
}

export default Homepage;