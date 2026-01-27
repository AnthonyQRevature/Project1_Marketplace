export type UserProfile = {
  id : number,
  username : string,
  email : string,
  profile: 
  {
    pfp_encoded : string,
    bio : string,
    latitude : number,
    longitude : number,
    distance : number
  }
}

export type PostEntity = {
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

export type Blocks = Array<{blocker: number, blocked: number}>;

export type ProfileBrief = {id: number, username: string, pfp_encoded: string};
export type Conversations = Array<ProfileBrief>;
export type Message_t = {messageId: number, message: String, receiverId: number, senderId: number};
export type Messages = Array<Message_t>;