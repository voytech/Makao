package fxclient;

/**
 * @author Zosia i S³awek
 */
public class CardViewList
{
    public var x : Integer = 0 on replace oldValue { onMoved(); }
    public var y : Integer = 0 on replace oldValue { onMoved(); }
    public var content : CardView[] 
          on replace oldValue[idxA..idxB] = newElement{
          		onContentChanged(newElement);	 
          }
    function onContentChanged(cv : CardView[]): Void
    {

    }   
    function onMoved()
    {
     
    }      
}