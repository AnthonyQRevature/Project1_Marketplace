import "./Head.css";
import logo from '../assets/Logo100.png';
import SearchBar from './SearchBar.tsx';

function Test()
{
  return (
    <div className="head_title">
      <div className="head_userSearch">
        <SearchBar/>
      </div>
      <div className="head_content">
        <h1>
          Secondhand
        </h1>
      </div>
      <div className="head_img">
        <img src={logo}/>
      </div>
    </div>
  );
}

export default Test;