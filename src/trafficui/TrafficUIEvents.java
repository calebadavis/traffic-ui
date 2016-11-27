package trafficui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import traffic.Board;

/**
 * Action listener class to handle PieceButton clicks
 */
public class TrafficUIEvents implements ActionListener {

    private static Board b = TrafficUI.theBoard;

    @Override
    public void actionPerformed(ActionEvent e) {
        
        // enable the 'Solve' button if the current layout isn't the solution
        boolean solved = false;
        
        // get the UI button that was clicked
        PieceButton pb = (PieceButton)e.getSource();

        // get the available moves
        List<Board.MoveDir> moves = pb.getPiece().getMoves();
        
        // Don't do anything if no moves are available
        int numMoves = moves.size();
        if (numMoves == 0) return;
        
        // Get the direction of the move
        Board.MoveDir dir;
        
        // Assume for now that only 2 possible moves exist
        // The default board has only 2 spaces on it...
        if (moves.size() > 1) {
            dir = moves.get( (TrafficUI.altDir) ? 1 : 0);
        }
        else dir = moves.get(0);
        
        // Attempt the move (this really shouldn't fail)
        if (b.move(pb.getPiece(), dir)) {
            
            // move the piece button to match the new piece position
            pb.setBounds(
                TrafficUI.GRID_ELEM_SIZE * pb.getPiece().getLeftPos(),
                TrafficUI.GRID_ELEM_SIZE * pb.getPiece().getTopPos(),
                TrafficUI.GRID_ELEM_SIZE * pb.getPiece().getType().getWidth(), 
                TrafficUI.GRID_ELEM_SIZE * pb.getPiece().getType().getHeight()
            );
            pb.repaint();
        }
        
        // Now that we're in a new position, clear out the solution chain,
        // disable the 'Next' button, and enable the 'Solve' button (assuming
        // the new layout isn't the solution).
        TrafficUI.solution.clear();
        TrafficUI.btnNext.setEnabled(false);
        solved = b.matches(b.pieceLocs(), b.getSolvedBoard());
        TrafficUI.btnSolve.setEnabled(!(solved));
    
        TrafficUI.theWindow.requestFocus();
        
    }    

}
