package minesweeper;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
public class Engine {
    Random rand = new Random(System.currentTimeMillis());
    private boolean Won;
    
    Engine(Board b,int bombcount){
        GenerateBombs(bombcount,b);
    for (int i = 0; i < b.getSize(); i++){
    for (int j = 0; j < b.getSize(); j++){
    b.AllTiles[i][j].setNumbered(CheckSurroundings(b,i,j));
    }
    }
    }
    public boolean CheckBombs(Board b,int row, int col){
        if (b.AllTiles[row][col].isBomb())
            return true;
        else
            return false;
    }
    public void GenerateBombs(int NumOfBombs, Board b){
        int counter = 0;
        while (counter != NumOfBombs){
            int row = rand.nextInt(b.getSize());
            int col = rand.nextInt(b.getSize());
            if (CheckBombs(b,row,col)){
                continue;
            }
            else{
                b.AllTiles[row][col].setBomb(true);
                counter++;
            }
        }
    }
    //WinCon will be false as long as player has not lost
    public boolean WinCon(Board b,int bombcount){
        int Counter_Flagged_Bomb_Tiles = 0,Counter_Flagged_Tiles = 0;
    for (int i = 0; i < b.getSize(); i++){
            for (int j = 0; j < b.getSize(); j++){
    if (b.AllTiles[i][j].isBomb() && b.AllTiles[i][j].isFlagged())
        Counter_Flagged_Bomb_Tiles++;
    if (b.AllTiles[i][j].isFlagged())
        Counter_Flagged_Tiles++;
        }
    }
    if (Counter_Flagged_Tiles - Counter_Flagged_Bomb_Tiles == 0 && Counter_Flagged_Bomb_Tiles == bombcount)
        Won = true;
    else
        Won = false;
    return Won;
    }
    //CLEAR EMPTY SPACE ALGORITHM
    public void ClearEmptySpace(Board b,int row, int col){
        // 111
        // 101
        // 111
        boolean[][] Revealed = new boolean[b.getSize()][b.getSize()];
        if ((row < 0 || row > b.getSize() - 1) || (col < 0 || col > b.getSize() - 1))
            return;
        if (Revealed[row][col])
            return;
        else{
            if (!b.AllTiles[row][col].isBomb() && b.AllTiles[row][col].isHidden()){
                if (b.AllTiles[row][col].getNumbered() == 0){
                b.AllTiles[row][col].setHidden(false);
                Revealed[row][col] = true;
                ClearEmptySpace(b, row - 1, col);
                ClearEmptySpace(b, row + 1, col);
                ClearEmptySpace(b, row, col - 1);
                ClearEmptySpace(b, row, col + 1);
                ClearEmptySpace(b, row - 1, col - 1);
                ClearEmptySpace(b, row + 1, col + 1);
                ClearEmptySpace(b, row + 1, col - 1);
                ClearEmptySpace(b, row - 1, col + 1);
                return;
            }
                else{
                    b.AllTiles[row][col].setHidden(false);
                    Revealed[row][col] = true;
                    return;
                }
            }
        }
    }
    //DISPLAY NUMBERS ALGORITHM
    public int CheckSurroundings(Board b,int row, int col){
        int counter = 0;
        if (row < b.getSize() - 1 && b.AllTiles[row + 1][col].isBomb())
            counter++;
        if (row >  0 && b.AllTiles[row - 1][col].isBomb())
            counter++;
        if (col > 0 && b.AllTiles[row][col - 1].isBomb())
            counter++;
        if (col < b.getSize() - 1 && b.AllTiles[row][col + 1].isBomb())
            counter++;
        if (row < b.getSize() - 1 && col < b.getSize() - 1 && b.AllTiles[row + 1][col + 1].isBomb())
            counter++;
        if (row > 0 && col > 0 && b.AllTiles[row - 1][col - 1].isBomb())
            counter++;
        if (row < b.getSize() - 1 && col > 0 && b.AllTiles[row + 1][col - 1].isBomb())
            counter++;
        if (row > 0 && col < b.getSize() - 1 && b.AllTiles[row - 1][col + 1].isBomb())
            counter++;
        return counter;
    }
}