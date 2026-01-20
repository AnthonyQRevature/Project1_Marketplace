export default function logError(o : any) : void
{
    (console.error || console.log)(o);
}