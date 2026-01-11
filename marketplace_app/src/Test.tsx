import "./Test.css";
import reactLogo from './assets/react.svg';

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
        <img src={reactLogo}/>
      </div>
    </div>
  );
}

export default Test;