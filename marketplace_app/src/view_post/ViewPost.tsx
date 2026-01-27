import { useEffect, useState } from "react";
import { useParams } from "react-router";
import "./ViewPost.css";

type Media = {
  mediaEncoded: string;
  mediaType: "image" | "video";
};

type TagEntity = {
  id: number;
  tag_name: string;
};

type Post = {
  id: number;
  description: string;
  price: number;
  status: string;
  media: Media[];
  tags: TagEntity[];
};

export default function ViewPost() {
  const { postId } = useParams<{ postId: string }>();
  const [post, setPost] = useState<Post | null>(null);
  const [error, setError] = useState("");

  useEffect(() => {
    fetch(`http://localhost:8080/listings/${postId}`)
      .then((res) => {
        if (!res.ok) throw new Error("Post not found");
        return res.json();
      })
      .then(setPost)
      .catch((err) => setError(err.message));
  }, [postId]);

  if (error) {
    return <p className="error">{error}</p>;
  }

  if (!post) {
    return <p>Loading post...</p>;
  }

  return (
    <div className="view-post">
      <h1>Post #{post.id}</h1>

      <p className="price">${post.price.toFixed(2)}</p>
      <p className={`status status-${post.status}`}>
        Status: {post.status}
      </p>

      <p className="description">{post.description}</p>

      {post.tags.length > 0 && (
        <div className="tags">
          {post.tags.map((tag) => (
            <span key={tag.id} className="tag">
              {tag.tag_name}
            </span>
          ))}
        </div>
      )}

      {post.media.length > 0 && (
        <div className="media-gallery">
          {post.media.map((m, idx) =>
            m.mediaType === "image" ? (
              <img
                key={idx}
                src={`${m.mediaEncoded}`}
                alt={`Post media ${idx}`}
              />
            ) : (
              <video key={idx} controls>
                <source src={m.mediaEncoded} />
              </video>
            )
          )}
        </div>
      )}
    </div>
  );
}
