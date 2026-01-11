import './Homepage.css'
import Head from './Head.tsx'
import { Link } from 'react-router';

function Homepage()
{
  return (
    <>
      <Head />
      <div className="cards">
        <div className="card">
          <Link to="/register"><p>Register</p></Link>
        </div>
        <div className="card">
          <p>Content</p>
        </div>
        <div className="card">
          <p>Content</p>
        </div>
        <div className="card">
          <p>Content</p>
        </div>
      </div>
    </>
  );
}

export default Homepage;