import Head from './Head.tsx'
import { Link, useNavigate } from "react-router";
import SearchBar from './SearchBar.tsx';
//import listing from '../listings/Listings.tsx';
import AuthenticationContext from '../authentication/AuthenticationContext.tsx';
import { useContext, useEffect, useState } from "react";
import Messages from "../message/Messages";
import useLoader from "../util/AsyncLoader";
import getAsset from "../util/AssetLoader";
import type { PostEntity } from "../util/DataStructure";
import { EncodedImage } from "../util/EncodedImage";

import './Homepage.css'
import AdminBar from './AdminBar.tsx';

const get_tags = { endpoint: "http://localhost:8080/tags", method: "GET" };
type TagEntity = {
  id: number;
  tag_name: string;
};

function Homepage()
{
  const [posts, setPosts] = useState<PostEntity[]>([]);
  const [AsyncLoader, _] = useLoader<TagEntity[]>(get_tags);
  const [auth,] = useContext(AuthenticationContext);
  const nav = useNavigate();

  //load posts in when the webpage is opened
  useEffect(() => {
    fetch("http://localhost:8080/listings")
      .then((value) => {
        return value.json();
      }).then((data) => {
        setPosts(data);
      });
  }, [])

  // handle form submit
  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const formData = new FormData(e.currentTarget);
    const tags = formData.getAll("tags") as string[];

    const url = new URL("http://localhost:8080/listings");
    url.searchParams.append("distance", '0');
    url.searchParams.append("tags", tags.join(','));
    //tags.forEach(tag => url.searchParams.append("tags", tag));

    const response = await fetch(url.toString());
    const data = await response.json();

    setPosts(data); // update state to re-render PostsGrid
  };

  const renderTags = (props: {resource: TagEntity[]}) => (
    <TagsList tags={props.resource} onSubmit={handleSubmit} />
  );

  return (
    <>
      <Head/>
      <button style={{visibility: auth.isGuest()? "hidden" : "visible"}} onClick={() => {nav("/createpost")}}>Create</button>
      <AsyncLoader
        then={renderTags}
        otherwise={() => <p>Loading...</p>}
        abort={() => <p>Failed</p>}
      />
      {/* Render PostsGrid from state, so it updates after submit */}
      <PostsGrid posts={posts} />
      <AdminBar />
    </>
  );
}

/* TagsList with onSubmit prop */
function TagsList(props: { tags: TagEntity[]; onSubmit: (e: React.FormEvent<HTMLFormElement>) => void }) {
  const Checkboxes = props.tags.map(tag => (
    <li key={tag.id}>
      <label htmlFor={`tag${tag.id}`}>{tag.tag_name}</label>
      <input type="checkbox" name="tags" value={tag.id} />
    </li>
  ));

  return (
    <form method="GET" onSubmit={props.onSubmit}>
      <div className="TagsList">{Checkboxes}</div>

      <button type="submit" className="listings_search_submit_button">Submit</button>
    </form>
  );
}

function PostsGrid(props: { posts: PostEntity[] }) {
  return (
    <div className="postsGrid">
      {props.posts.map(post => (
        <PostCard key={post.id} post={post} />
      ))}
    </div>
  );
}

function PostCard(props: { post: PostEntity }) {
  return (
    <div className="postCard">
      <div className="post-image"><EncodedImage img={props.post.media[0].mediaEncoded} /></div>
      <h1>{props.post.price.toLocaleString("en-US", { style: "currency", currency: "USD" })}</h1>
      <p><Link to={`/listings/${props.post.id}`}>View</Link>{props.post.description}</p>
    </div>
  );
}

export default Homepage;