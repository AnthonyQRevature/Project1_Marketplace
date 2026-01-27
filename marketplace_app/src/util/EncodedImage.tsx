export function EncodedImage(props : {img: string})
{
    let {img} = props;

    //a hack
    //ideally we would only store the encoded part of the image in the database
    if (img.match("data:[a-z,A-Z]*/[a-z,A-Z]*;base64,"))
        return (
            <img className="icon" src={img}/>
        );
    else
        return (
            <img className="icon" src={`data:image/png;base64,${img}`}/>
        );
}