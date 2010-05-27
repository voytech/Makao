package fxclient;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import shared.*;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.scene.effect.Glow;
import javafx.scene.effect.*;
import javafx.scene.effect.light.*;



/**
 * @author Voytech
 */
public class CardView extends ImageView
{
    public var card : Card on replace oldValue{createFrom(card);}
    public var selected : Boolean = false;
    var relatedImageKey : String;
    postinit
    {
        scaleX = 1.4;
        scaleY = 1.4;
    }
    function createFrom(card : Card)
    {
        var name : Card.Name = card.getName();
        var suit : Card.Suit = card.getSuit();
        if (name == Card.Name.ACE) relatedImageKey = "{getSuitRelatedString(suit)}1";
        if (name == Card.Name.TWO) relatedImageKey = "{getSuitRelatedString(suit)}2";
        if (name == Card.Name.THREE) relatedImageKey = "{getSuitRelatedString(suit)}3";
        if (name == Card.Name.FOUR) relatedImageKey = "{getSuitRelatedString(suit)}4";
        if (name == Card.Name.FIVE) relatedImageKey = "{getSuitRelatedString(suit)}5";
        if (name == Card.Name.SIX) relatedImageKey = "{getSuitRelatedString(suit)}6";
        if (name == Card.Name.SEVEN) relatedImageKey = "{getSuitRelatedString(suit)}7";
        if (name == Card.Name.EIGHT) relatedImageKey = "{getSuitRelatedString(suit)}8";
        if (name == Card.Name.NINE) relatedImageKey = "{getSuitRelatedString(suit)}9";
        if (name == Card.Name.TEN) relatedImageKey = "{getSuitRelatedString(suit)}10";
        if (name == Card.Name.JACK) relatedImageKey = "{getSuitRelatedString(suit)}j";
        if (name == Card.Name.QUEEN) relatedImageKey = "{getSuitRelatedString(suit)}q";
        if (name == Card.Name.KING) relatedImageKey = "{getSuitRelatedString(suit)}k";
        loadImage();
    }
    function getSuitRelatedString(suit : Card.Suit) :String
    {
    	if (suit == Card.Suit.CLUB) return "c";
    	if (suit == Card.Suit.DIAMOND) return "d";
    	if (suit == Card.Suit.HEART) return "h";
    	if (suit == Card.Suit.SPADE) return "s";
    	return null; 
    }
    function loadImage() : Void
    {
       image = Image{url: "{__DIR__}cards/{relatedImageKey}.gif"};  
    }


    
}