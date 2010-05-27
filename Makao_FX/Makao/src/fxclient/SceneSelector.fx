package fxclient;
import javafx.scene.*;




/**
 * @author Wojtek
 */
public class SceneSelector
{
    public var scenes:Scene[];
    public var current:Scene;
    var currentInd:Integer = 0;
    public function nextScene()
    {
        var previous = current;
        current = scenes[++currentInd];
        delete scenes[currentInd-1] from scenes;
    }
}