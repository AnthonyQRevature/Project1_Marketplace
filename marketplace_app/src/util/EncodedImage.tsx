export function EncodedImage(props : {img: string, foward?:React.ImgHTMLAttributes<HTMLImageElement>})
{
    let {img, foward} = props;

    //a hack
    //ideally we would only store the encoded part of the image in the database
    if (!img.match("data:[a-z,A-Z]*/[a-z,A-Z]*;base64,"))
        img=`data:image/png;base64,${img}`;
    return (
        <img {...{...foward, src:img}}/>
    );
}