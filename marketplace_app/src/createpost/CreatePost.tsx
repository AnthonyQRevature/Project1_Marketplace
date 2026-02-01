import { useState, useEffect, type ChangeEvent, useContext } from "react";
import "./CreatePost.css";
import AuthenticationContext from "../authentication/AuthenticationContext";
import { useNavigate } from "react-router";
import { useAuthGuard } from "../util/AuthGuard";
import { Role } from "../util/DataStructure";

type TagEntity = {
  id: number;
  tag_name: string;
};

type PostMedia = {
  media_encoded: string;
  media_type: "image";
};

type CreatePostPayload = {
  description: string;
  price: number;
  status?: string;
  tags: number[];
  media: PostMedia[];
};

export default function CreatePost() {
  const [tags, setTags] = useState<TagEntity[]>([]);
  const [selectedTags, setSelectedTags] = useState<number[]>([]);
  const [description, setDescription] = useState("");
  const [price, setPrice] = useState<number>(0);
  const [images, setImages] = useState<string[]>([]); // base64 strings
  const [statusMessage, setStatusMessage] = useState("");
  const [auth, _] = useContext(AuthenticationContext);
  const guard = useAuthGuard(Role.USER);
  const nav = useNavigate();
  useEffect(() => {
    fetch("http://localhost:8080/tags")
      .then((res) => res.json())
      .then(setTags)
      .catch((err) => console.error("Failed to load tags", err));
  }, []);
  
  if (guard())
  {
    return <></>;
  }

  

  // Convert uploaded files to base64
  const handleFileChange = (e: ChangeEvent<HTMLInputElement>) => {
    if (!e.target.files) return;

    const files = Array.from(e.target.files);
    files.forEach((file) => {
      const reader = new FileReader();
      reader.onload = () => {
        if (typeof reader.result === "string") {
          setImages((prev) => [...prev, reader.result as string]);
        }
      };
      reader.readAsDataURL(file); // base64 encode
    });
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>, user_id : number) => {
    e.preventDefault();

    if (!description || price <= 0 || images.length === 0) {
      setStatusMessage("Please fill all fields and upload at least one image.");
      return;
    }

    const payload: CreatePostPayload = {
      description,
      price,
      status: "available",
      tags: selectedTags,
      media: images.map((img) => ({
        media_encoded: img,
        media_type: "image",
      })),
    };

    try {
      const res = await fetch(`http://localhost:8080/createpost?sellerId=${user_id}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });

      if (res.ok) {
        setStatusMessage("Post created successfully!");
        /*
        setDescription("");
        setPrice(0);
        setSelectedTags([]);
        setImages([]);
        */
        const post : any = await res.json();
        nav(`/listings/${post.id}`);
      } else {
        const err = await res.text();
        setStatusMessage("Failed to create post: " + err);
      }
    } catch (err) {
      setStatusMessage("Error: " + err);
    }
  };


  return (
    <div className="create-post">
      <h1>Create Post</h1>
      <form onSubmit={(e) => handleSubmit(e, auth.id)}>
        <label>Description:</label>
        <textarea
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          required
        />

        <label>Price:</label>
        <input
          type="number"
          min={0}
          step={0.01}
          value={price}
          onChange={(e) => setPrice(parseFloat(e.target.value))}
          required
        />

        <label>Tags:</label>
        <ul className="tag-list">
          {tags.map((tag) => (
            <li key={tag.id}>
              <label>
                <input
                  type="checkbox"
                  value={tag.id}
                  checked={selectedTags.includes(tag.id)}
                  onChange={(e) => {
                    const val = tag.id;
                    if (e.target.checked) {
                      setSelectedTags((prev) => [...prev, val]);
                    } else {
                      setSelectedTags((prev) =>
                        prev.filter((id) => id !== val)
                      );
                    }
                  }}
                />
                {tag.tag_name}
              </label>
            </li>
          ))}
        </ul>

        <label>Images (PNG only):</label>
        <input
          type="file"
          accept="image/png"
          multiple
          onChange={handleFileChange}
          required
        />
        {images.length > 0 && (
          <div className="preview">
            {images.map((img, idx) => (
              <img key={idx} src={img} alt="preview" />
            ))}
          </div>
        )}

        <button type="submit">Create Post</button>
      </form>

      {statusMessage && <p className="status">{statusMessage}</p>}
    </div>
  );
}
