package trafficui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import traffic.Board;

/**
 * Class to handle keyboard events
 */
public class KeyEvents implements KeyListener {

    private static Board b = TrafficUI.theBoard;

    @Override
    public void keyTyped(KeyEvent e) {
    
        // Get the character that was typed:
        char c = e.getKeyChar();
        
        // For debugging, print a text representation of the boar layout on 'p'
        if (c == 'p') {
            b.printBoard();
            return;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        // On a lower level than 'keyTyped()', allow holding down 'a' or 'ctrl'
        // to specify the piece should move in the alternate direction on click
        int kc = e.getKeyCode();
        if (kc == KeyEvent.VK_CONTROL || e.getKeyChar() == 'a') {
           TrafficUI.altDir = true;
        }
    }
   
    @Override
    public void keyReleased(KeyEvent e) {
        
        // releasing 'e' or 'ctrl' specifies pieces move normally again
        if(e.getKeyCode() == KeyEvent.VK_CONTROL || e.getKeyChar() == 'a') {
            TrafficUI.altDir = false;
        }       
    }
}
