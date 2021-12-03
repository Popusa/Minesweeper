
package minesweeper;

public class Tile {
    private boolean bomb;
    private boolean alreadyrevealed;
    private boolean flagged;
    private boolean hidden;
    private int numbered;

    public boolean isAlreadyrevealed() {
        return alreadyrevealed;
    }

    public void setAlreadyrevealed(boolean alreadyrevealed) {
        this.alreadyrevealed = alreadyrevealed;
    }
    
    public boolean isBomb() {
        return bomb;
    }
    
    public void setBomb(boolean bomb) {
        this.bomb = bomb;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public int getNumbered() {
        return numbered;
    }

    public void setNumbered(int numbered) {
        this.numbered = numbered;
    }
    
    public Tile() {
        this.hidden = true;
    }
}