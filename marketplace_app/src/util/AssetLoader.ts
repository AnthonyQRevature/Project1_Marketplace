
const ASSET_ROOT = "/src/assets/";

export default function getAsset(url : string) : string
{
    return ASSET_ROOT + url;
}