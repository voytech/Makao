package fxclient;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.effect.Glow;
import javafx.scene.effect.*;
import javafx.scene.effect.light.*;
import  javafx.scene.paint.Color;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;

/**
 * @author Wojtek
 */
public class NeonLight extends AnimatedNode
{
    
    var glowLevel = 0.0;
    postinit
    {
         image = Image{url: "{__DIR__}images/yellow_light.png"};
         effect =  Glow {
                 level: bind glowLevel
             	}         	         	
    }
    public override function defaultOnClick():Void
    {
            if (isPushed)
            {
                isPushed = false;
            }
            else
            {
                isPushed= true;
            } 
    }  
    public function turnOnRedLight()
    {
         image = Image{url: "{__DIR__}images/red_light.png"};       	     
    }
    public function turnOnGreenLight()
    {
         image = Image{url: "{__DIR__}images/green_light.png"};
    }
    public function turnOnYellowLight()
    {
         image = Image{url: "{__DIR__}images/yellow_light.png"};		    
    }
    public override function play()
    {
        var timeline = Timeline {
            repeatCount: Timeline.INDEFINITE
            autoReverse: true;
            interpolate: true;
            keyFrames: [
                KeyFrame {
                    time: 0s
                    values: [ glowLevel => 0.0 ]
                },
                KeyFrame {
                     time: 2s
                     values: [ glowLevel => 0.5 ]
                },
                KeyFrame {
                    time: 3s
                    values: [ glowLevel => 1 ]
                }
            ]
        }
        timeline.play();
    }
}