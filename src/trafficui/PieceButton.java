package trafficui;

import javax.swing.JButton;

import traffic.Piece;

/**
 * A Swing JButton which also contains a Piece
 */
class PieceButton extends JButton {

    /**
     * Construct a PieceButton instance
     * 
     * @param p the Piece to contain
     */
    public PieceButton(Piece p) {
        super();
        this.p = p;
    }

    /**
     * Retrieve the contained Piece instance
     * @return the contained Piece instance
     */
    public Piece getPiece() {
        return p;
    }

    private Piece p;

    private static final long serialVersionUID = 1L;
}
