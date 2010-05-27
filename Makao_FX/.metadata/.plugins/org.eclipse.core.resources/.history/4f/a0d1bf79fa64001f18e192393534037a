package fxclient;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.animation.transition.ScaleTransition;
import javafx.animation.Timeline;



/**
 * @author Wojtek
 */
public class ReadyButton extends AnimatedNode
{
    var scaleTransition = ScaleTransition {
                duration: 1s
                node: this
                fromX: 1.0
                toX: 0.8
                fromY: 1.0
                toY: 0.8
                repeatCount: Timeline.INDEFINITE
                autoReverse: true
            };
    public override function prepare():Void
    {
        pushed = Image{url: "{__DIR__}images/ready_button_down.png"};
        released = Image{url: "{__DIR__}images/ready_button_up.png"};
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
          scaleTransition.stop();
          scaleTransition = ScaleTransition {
                          duration: 0.5s
                          node: this
                          fromX: 1.3
                          toX: 0.0
                          fromY: 1.3
                          toY: 0.0
                          repeatCount: 1
                          autoReverse: false
                      };
          scaleTransition.play();
           
    }
    public override function play()
    {
        scaleTransition.play();
    }
}