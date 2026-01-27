import "./Head.css";
import logo from '../assets/Logo100.png';

function Test()
{
  return (
    <div className="media">
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