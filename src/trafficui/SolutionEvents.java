package trafficui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import traffic.Board;
import traffic.MoveNode;
import traffic.Piece;

/**
 * Handler class to respond to events related to the 'solution' UI gestures
 * (the 'Solve' button and the 'Next' button).
 * 
 * @see #TrafficUI
 * @author cdavis
 *
 */
public class SolutionEvents implements ActionListener {
    
    /**
     * Handles the clicked events for the 'Solve' and 'Next' buttons
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        // 'Solve' button click handler
        if (cmd.equals("Solve")) {
            
            // Clear the solution list of moves 
            TrafficUI.solution.clear();
            
            // Build up the MoveNode tree until a solution is found:
            MoveNode solved = b.solve();
            
            // Walk the tree from the solution node back up to this layout
            for (MoveNode mn = solved; mn != null; mn = mn.getParent()) {

                // Add each successive move to the solution moves list
                TrafficUI.solution.add(0, mn);
            }
            
            // Setup state for the first press of the 'Next' button
            curMove = 1;
            TrafficUI.btnSolve.setEnabled(false);
            TrafficUI.btnNext.setEnabled(true);
        }

        // 'Next' button click handler
        if (cmd.equals("Next")) {
            
            // If more moves remain:
            if (curMove < TrafficUI.solution.size() -1)
                
                // Place the pieces in the next configuration
                _rearrange(TrafficUI.solution.get(++curMove));

            else {
                
                // otherwise we're at the solution layout, so clear it.
                TrafficUI.solution.clear();
            }
            
            // Only enable the 'Next' button if the current layout differs
            // from the solution
            TrafficUI.btnNext.setEnabled(
                !(b.matches(b.pieceLocs(), b.getSolvedBoard()))
            );

        }
    }

    /**
     * Helper method to reposition all the pieces on the board at once. Also
     * updates the board UI to reflect this new layout.
     * 
     * Note: the result of this operation is to set up the board so that
     * the move specified in the MoveNode mn is now possible. It doesn't
     * perform the move.
     * 
     * @param mn - a specification of a move (the starting board layout,
     * the piece ID to move, and the direction of the move).
     */
    private void _rearrange(MoveNode mn) {
        
        // First reposition the pieces to match the MoveNode
        b.reset(mn);
        
        // Calculate possible moves
        for (Piece p : b.getPieces())
            b.storeMoves(p);
        
        // Redraw the PieceButtons
        TrafficUI.redrawPieces();
        
        // Make sure no button has focus
        TrafficUI.theWindow.requestFocus();                
    }

    private Board b = TrafficUI.theBoard;
    private int curMove = 0;
}
