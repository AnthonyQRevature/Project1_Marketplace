import { useContext, useEffect, useState } from "react";
import "./ViewPost.css";
import LoadBrief from "../util/UserBrief";
import flag_icon from '../assets/flag_lmao.png';
import type { Endpoint } from '../util/Endpoint';
import { Link, useNavigate, useParams } from "react-router";
import AuthenticationContext from "../authentication/AuthenticationContext";

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
  sellerId : number;
  description: string;
  price: number;
  status: string;
  media: Media[];
  tags: TagEntity[];
};

function makeReportEndpoint(target : number) : Endpoint
{
  return {
    endpoint: `http://localhost:8080/users/${target}/reports`,
    method: "POST"
  };
}

function ViewPost() {
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
      <div className="viewer_report_loc">
        <Post_ReportButton post_report={post}/>
      </div>
      <h1>Post #{post.id}</h1>
      <div className="columns">
        <div className="left">
          <LoadBrief user_id={post.sellerId} lhs="Seller: "/>
          <p className="price">${post.price.toFixed(2)}</p>
          <p className={`status status-${post.status}`}>
            Status: {post.status}
          </p>

          <p className="description">{post.description}</p>

          {post.tags.length > 0 && (
            <div className="tags">
              <ul>
                {post.tags.map((tag) => (
                  <li key={tag.id} className="tag">
                    {tag.tag_name}
                  </li>
                ))}
              </ul>
            </div>
          )}
        </div>

        <div className="right"><Gallery media={post.media}/></div>
      </div>
    </div>
  );
}

function Post_ReportButton(props: {post_report: Post})
{
  const {post_report} = props;
  const [auth, _] = useContext(AuthenticationContext);

  if(post_report.sellerId == auth.id || auth.isGuest())
  {
    return (<></>)
  }

  const report = async () =>
  {

    const reason = prompt("Reason");

    if(reason != null)
    {
      const endpoint = makeReportEndpoint(Number(post_report.sellerId));
      const response = await fetch(endpoint.endpoint, {
        method: endpoint.method,
        body: JSON.stringify({
          reporter_id: auth.id,
          reported_id: Number(post_report.sellerId),
          reason: reason,
          post_id:Number(post_report.id)
        }),
        headers: {
          "Content-Type": "application/json",
          Authorization: auth.encryptedToken
        }
      });

      if (response.ok)
      {
        alert("your response has been successfully submitted");
      }
    }
  }

  return (
    <img className='icon view_report link' onClick={report} src={flag_icon} />
  )
}

function Gallery(props : {media : Post['media']})
{
  const {media} = props;
  return (
    <>
      {media.length > 0 && (
        <div className="media-gallery">
          {media.map((m, idx) =>
            m.mediaType === "image" ? (
              <img
                className={idx === 0? "first" : ""}
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
    </>
  );
}

export default ViewPost;