import "./Head.css";
import logo from '../assets/Logo100.png';
import SearchBar from './SearchBar.tsx';

function Test()
{
  return (
    <div id="head">
      <div className="userSearch">
        <SearchBar/>
      </div>
      <div className="title">
        <h1>
          Secondhand
        </h1>
      </div>
      <div className="logo">
        <img src={logo}/>
      </div>
    </div>
  );
}

export default Test;