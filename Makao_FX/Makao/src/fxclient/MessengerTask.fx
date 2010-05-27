package fxclient;
import javafx.async.JavaTaskBase;
import javafx.async.RunnableFuture;
import java.net.Socket;
import shared.*;
/**
 * @author Wojtek
 */
public class MessengerTask extends JavaTaskBase
{
     public var socket : Socket;
     public-init var messenger : MessengerFX;
     public var listeners:RequestListener[];
     protected override function create() : RunnableFuture 
     {
           messenger = new MessengerFX(socket);
           for (listener in listeners) messenger.addListener(listener);
           return messenger;
     }
} 