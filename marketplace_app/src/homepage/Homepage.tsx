import './Homepage.css'
import Head from './Head.tsx'
import { Link } from 'react-router';
import SearchBar from './SearchBar.tsx';

function Homepage()
{
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
          <LinkTo path="/uploads" label="uploads" />
        </div>
        <div className="card">
          <SearchBar />
        </div>
      </div>
    </>
  );
}

function LinkTo(props: {path: string, label: string})
{
  return (<Link to={props.path}><p>{props.label}</p></Link>);
}

export default Homepage;