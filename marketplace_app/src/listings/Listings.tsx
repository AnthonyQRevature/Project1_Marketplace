import "./Listings.css";
import AsyncLoader from "../util/AsyncLoader";
import getAsset from "../util/AssetLoader";

const get_tags = {endpoint: "http://localhost:8080/tags", method: "GET"};
type TagEntity = {
  id: Number
  tag_name: string
};

const get_posts = {endpoint: "http://localhost:8080/listings", method: "GET"}
type PostEntity = {
  id: number,
  description: string,
  price: number,
//sellerId: number,
  status: string,
  createdAt: string,
  lastEditTime: string,
  media: {
    postId: number,
    mediaType: string,
    mediaUrl: string,
  }[]
};

function Listings()
{
  //asynchronously load in the tags
  const renderTags = (value : TagEntity[]) => (<TagsList tags={value} />);
  
  return (
    <>
      <AsyncLoader<TagEntity[]> endpoint={get_tags} then={renderTags} otherwise={(<p>Loading...</p>)} abort={<p>Failed</p>} />
      <AsyncLoader<PostEntity[]> endpoint={get_posts} then={(posts)=>(<PostsGrid posts={posts}/>)} otherwise={(<p>Loading...</p>)}/>
    </>
  );
}

/*
 * array props.tags;
 */
function TagsList(props : {tags: TagEntity[]})
{
  //retrieved tags
  let tagList = props.tags;

  const Checkboxes = tagList.map((value) => 
    (
      <li key={String(value.id)}>
        <label htmlFor={`tag${value.id}`}>{value.tag_name}</label>
        <input type="checkbox" name={`tag${value.id}`}/>
      </li>
    )
  );

  return (
    <>
      <form>
        <div className="TagsList">
          {Checkboxes}
        </div>
        <button>Submit</button>
      </form>
    </>
  )
}

function PostsGrid(props : {posts: PostEntity[]})
{
  return (
    <div className="postsGrid">
      {props.posts.map((post)=>(<PostCard key={post.id} post={post}/>))}
    </div>
  );
}

function PostCard(props : {post: PostEntity})
{
  //add title and user and stuff
  return (
    <div className="postCard">
      <img src={getAsset(props.post.media[0].mediaUrl)}/>
      <h1>{props.post.price.toLocaleString('en-US', { style: 'currency', currency: 'USD' })}</h1>
      <p>{props.post.description}</p>
    </div>
  );
}

export default Listings;