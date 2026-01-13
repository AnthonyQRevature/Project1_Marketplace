import "./Listings.css";
import AsyncLoader from "../util/AsyncLoader";

const get_tags = {endpoint: "http://localhost:8080/tags", method: "GET"};

type TagEntity = {
  id: Number
  tag_name: string
};

function Listings()
{
  //asynchronously load in the tags
  const renderTags = (value : TagEntity[]) => (<TagsList tags={value} />);
  
  return (
    <>
      <AsyncLoader<TagEntity[]> endpoint={get_tags} then={renderTags} otherwise={(<p>Loading...</p>)} abort={<p>Failed</p>} />
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
    return 
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