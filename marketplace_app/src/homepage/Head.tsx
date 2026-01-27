import "./Head.css";
import logo from '../assets/Logo100.png';
import SearchBar from './SearchBar.tsx';

function Test()
{
  return (
    <div className="title">
      <div className="userSearch">
        <SearchBar/>
      </div>
      <div className="content">
        <h1>
          Secondhand
        </h1>
      </div>
      <div className="img">
        <img src={logo}/>
      </div>
    </div>
  );
}

export default Test;