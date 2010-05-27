package fxclient;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

/**
 * @author Wojtek
 */
public abstract class PanelBase
{
    public var tittle : String;
    public var x:Integer;
    public var y:Integer;
    public var height:Integer;
	public var width:Integer;
    public var background = ImageView {
        image : Image{url:"{__DIR__}images/panel.png";}
        x: bind x
    	y: bind y
        fitWidth: bind width
    	fitHeight: bind height
    	     
    }   
    public function getContent() : ImageView[]
    {
        var content:ImageView[];
        insert background into content;
        return content;
    }
    public  function show() : Void
    {
    	for (node in getContent()) node.visible = true;    
    }
    public  function hide() : Void
    {
     	for (node in getContent()) node.visible = false;   
    }
}