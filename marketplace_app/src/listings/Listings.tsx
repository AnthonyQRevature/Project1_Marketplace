import "./Listings.css";
import Messages from "../message/Messages";
import useLoader from "../util/AsyncLoader";
import getAsset from "../util/AssetLoader";
import Head from '../homepage/Head.tsx'
import { useEffect, useState } from "react";
import type { PostEntity } from "../util/DataStructure";
import { EncodedImage } from "../util/EncodedImage";

const get_tags = { endpoint: "http://localhost:8080/tags", method: "GET" };
type TagEntity = {
  id: number;
  tag_name: string;
};


function Listings() {
  const [posts, setPosts] = useState<PostEntity[]>([]);
  const [AsyncLoader, _] = useLoader<TagEntity[]>(get_tags);

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
    const distance = formData.get("distance") as string;

    const url = new URL("http://localhost:8080/listings");
    url.searchParams.append("distance", distance);
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
      <AsyncLoader
        then={renderTags}
        otherwise={() => <p>Loading...</p>}
        abort={() => <p>Failed</p>}
      />
      {/* Render PostsGrid from state, so it updates after submit */}
      <PostsGrid posts={posts} />
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

      <label htmlFor="distance">Maximum Distance:</label>
      <input type="range" id="distance" name="distance" min="0" max="150" defaultValue="100" />

      <button type="submit">Submit</button>
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
      <p>{props.post.description}</p>
    </div>
  );
}

export default Listings;