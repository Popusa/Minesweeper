
package minesweeper;
import java.util.ArrayList;
public class Board {
private int size;
public Tile[][] AllTiles = new Tile[size][size];

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
    public int getNumOfTiles(){
    return AllTiles.length;
}
    
    public Tile[][] getAllTiles() {
        return AllTiles;
    }

    public void setAllTiles(Tile[][] AllTiles) {
        this.AllTiles = AllTiles;
    }
    
    public Board(int size) {
        AllTiles = new Tile[size][size];
        this.size = size;
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                AllTiles[i][j] = new Tile();
            }
        }
    }
}
