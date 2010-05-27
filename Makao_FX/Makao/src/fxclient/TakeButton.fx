package fxclient;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.animation.transition.TranslateTransition;
import javafx.animation.Timeline;


/**
 * @author Wojtek
 */
public class TakeButton extends AnimatedNode
{
    
      var translate = TranslateTransition {
                    duration: 0.5s
                    node: this
                    fromY: y
                    toY: y+10;   
                    repeatCount: Timeline.INDEFINITE
                    autoReverse: true
                };
        public override function prepare():Void
        {
            pushed = Image{url: "{__DIR__}images/hand_down.png"};
            released = Image{url: "{__DIR__}images/hand_down.png"};
            image = released;
        }
        public override function defaultOnClick() : Void 
        {    
              if (isPushed)
              {
                   isPushed = false;
                   image = released;
              }
              else
              {
                   isPushed= true;
                   image = pushed;
              }
              //translate.stop();               
        }
        public override function play()
        {
            translate.play();
        }
}