package fxclient;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.animation.transition.ScaleTransition;
import javafx.animation.Timeline;

/**
 * @author Wojtek
 */
public class MakaoButton extends ReadyButton
{
    public override function prepare():Void
        {
            pushed = Image{url: "{__DIR__}images/makao_button_down.png"};
            released = Image{url: "{__DIR__}images/makao_button_up.png"};
            image = released;
        }
        
}