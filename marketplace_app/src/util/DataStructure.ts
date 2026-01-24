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

export type Blocks = Array<{blocker: number, blocked: number}>;