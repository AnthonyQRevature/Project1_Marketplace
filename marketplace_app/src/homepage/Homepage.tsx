import './Homepage.css'
import Head from './Head.tsx'

function Homepage()
{
  return (
    <>
      <Head />
      <div className="cards">
        <div className="card">
          <p>Content</p>
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