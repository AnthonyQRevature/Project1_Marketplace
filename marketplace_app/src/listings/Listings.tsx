import "./Listings.css";
import AsyncLoader from "../util/AsyncLoader";
import getAsset from "../util/AssetLoader";
import { useState } from "react";

const get_tags = { endpoint: "http://localhost:8080/tags", method: "GET" };
type TagEntity = {
  id: number;
  tag_name: string;
};

type PostEntity = {
  id: number;
  description: string;
  price: number;
  status: string;
  createdAt: string;
  lastEditTime: string;
  media: {
    postId: number;
    mediaType: string;
    mediaUrl: string;
  }[];
  postTags: {
    postNum: number;
    postTagId: number;
  }[];
};

function Listings() {
  const [posts, setPosts] = useState<PostEntity[]>([]);

  // handle form submit
  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const formData = new FormData(e.currentTarget);
    const tags = formData.getAll("tags") as string[];
    const distance = formData.get("distance") as string;

    const url = new URL("http://localhost:8080/listings");
    url.searchParams.append("distance", distance);
    tags.forEach(tag => url.searchParams.append("tags", tag));

    const response = await fetch(url.toString());
    const data = await response.json();

    setPosts(data); // update state to re-render PostsGrid
  };

  const renderTags = (tags: TagEntity[]) => (
    <TagsList tags={tags} onSubmit={handleSubmit} />
  );

  return (
    <>
      <AsyncLoader<TagEntity[]>
        endpoint={get_tags}
        then={renderTags}
        otherwise={<p>Loading...</p>}
        abort={<p>Failed</p>}
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
      <img src={getAsset(props.post.media[0].mediaUrl)} />
      <h1>{props.post.price.toLocaleString("en-US", { style: "currency", currency: "USD" })}</h1>
      <p>{props.post.description}</p>
    </div>
  );
}

export default Listings;