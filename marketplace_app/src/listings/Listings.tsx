import { useEffect, useState } from "react";
import "./Listings.css";
import logError from "../util/logError";

const get_tags = {endpoint: "http://localhost:8080/tags", method: "GET"};

type TagEntity = {
  id: Number
  tag_name: string
};

function Listings()
{
  //asynchronously load in the tags
  const [tagList, setTagList] = useState<TagEntity[]>([]);
  
  //try a certain number of times
  const fetch_tags = async (rep: number) => 
  {
    let i = 0;
    while (true)
    {
      try
      {
        const response = await fetch(get_tags.endpoint, {method: get_tags.method});
        const values : TagEntity[] = await response.json();
        setTagList(values);
        return;
      }
      catch (e)
      {
        //fail
        logError(e);
      }

      //failed
      i++;
      if (i < rep)
      {
        logError("Fetch request failed. Retrying.");
      }
      else
      {
        logError("Fetch request failed. Too many attempts. Aborting.")
        break;
      }
    }
  };

  useEffect(() => {fetch_tags(5);}, []);

  return (
    <>
      <TagsList tags={tagList} />
    </>
  );
}

/*
 * array props.tags;
 */
function TagsList(props : {tags: TagEntity[]})
{
  //loading
  if (props.tags.length==0)
  {
    return <p>Loading...</p>
  }

  //retrieved tags
  let tagList = props.tags;

  const Checkboxes = tagList.map((value) => 
    (
      <li key={String(value.id)}>
        <label htmlFor={`tag${value.id}`}>{value.tag_name}</label>
        <input type="checkbox" name=""/>
      </li>
    )
  );

  return (
    <>
      <form>
        <div className="TagsList">
          {Checkboxes}
        </div>  
      </form>
    </>
  )
}

export default Listings;