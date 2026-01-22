export function EncodedImage(props : {img: string})
{
    let {img} = props;
    return (
        <img src={`data:image/png;base64,${img}`}/>
    );
}