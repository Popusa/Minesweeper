package minesweeper;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
public class GameUI implements MouseListener{
    public static final Color VERY_DARK_GREEN = new Color(0,102,0);
    public static final Color VERY_DARK_YELLOW = new Color(255,204,0);
    public static final Color SLIGHTLY_DARK_RED = new Color(204,0,0);
    public static final Color VERY_DARK_RED = new Color (153,0,0);
    public static final Color VERY_DARK_BLUE = new Color (0,0,153);
    public static final Color ONE = new Color (0,0,255);
    public static final Color TWO = new Color (0,128,0);    
    public static final Color THREE = new Color (255,255,0);    
    public static final Color FOUR = new Color (255,165,0);    
    public static final Color FIVE = new Color (255,0,0);    
    public static final Color SIX = new Color (75,0,130);    
    public static final Color SEVEN = new Color (238,130,238);    
    public static final Color EIGHT = new Color (0,0,0);
    public static final Color FLAG = new Color (255,255,255);        
    public static final Color HIDDEN_TILE = Color.DARK_GRAY;
    public static final Color EMPTY_TILE = new Color (145,145,145);
    final int defaultsize = 9;
    final int defaultgrid = 400;
    final int defaultbombcount = 10;
    final int hardsize = 18;
    final int hardgrid = 800;
    final int hardbombcount = 40;
    final int insanesize = 30;
    final int insanegrid = 1200;
    final int insanebombcount = 99;
    int clicks;
    boolean Lost;
    boolean Won;
    boolean cheat;
    Board b;
    Engine g;
    JFrame frame;
    JPanel gamepanel,
            Toppanel,
            Botpanel;
    JButton[][] Tiles;
    JButton ResetB;
    JLabel Botpanellabel;
    
    public void Reset(){
        frame.remove(gamepanel);
        frame.remove(Toppanel);
        frame.remove(Botpanel);
        Lost = false;
        Won = false;
        cheat = false;
        clicks = 0;
        b = new Board(hardsize);
        g = new Engine(b,hardbombcount);
        gamepanel = new JPanel();
        Toppanel = new JPanel();
        Botpanel = new JPanel();
        Botpanel.setLayout(new BorderLayout());
        Toppanel.setLayout(new BorderLayout());
        ResetB = new JButton("Reset");
        Botpanellabel = new JLabel(" ");
        Botpanellabel.setHorizontalAlignment(SwingConstants.CENTER);
        Botpanel.add(Botpanellabel);
        Toppanel.add(ResetB);
        ResetB.addMouseListener(this);
        gamepanel.setLayout(new GridLayout(b.getSize(),b.getSize()));
        gamepanel.add(Toppanel);
        gamepanel.setBackground(new Color(150,150,150));
        Tiles = new JButton[b.getSize()][b.getSize()];
                for (int i = 0; i < b.getSize(); i++){
                    for (int j = 0; j < b.getSize(); j++){
                        Tiles[i][j] = new JButton();
                        gamepanel.add(Tiles[i][j]);
                        Tiles[i][j].setBackground(HIDDEN_TILE);
                        Tiles[i][j].addMouseListener(this);
                    }
                }
                frame.add(gamepanel,BorderLayout.CENTER);
                frame.add(Toppanel,BorderLayout.NORTH);
                frame.add(Botpanel,BorderLayout.SOUTH);
                frame.revalidate();
                frame.repaint();
                UpdateTiles();
    }

    @Override
    public void mousePressed(MouseEvent  e) {
        if (e.getButton() == MouseEvent.BUTTON2){
//            clicks++;
            if (clicks == 4)
                cheat = true;
        }
        if (e.getSource() == ResetB && !SwingUtilities.isRightMouseButton(e) && !SwingUtilities.isMiddleMouseButton(e)){
            Reset();
        }
        else{
            if (Won || Lost)
                return;
        for (int i = 0; i < b.getSize(); i++){
        for (int j = 0; j < b.getSize(); j++){
            if (e.getSource() == Tiles[i][j] && e.getButton() == MouseEvent.BUTTON1){
            if (b.AllTiles[i][j].isBomb() && e.getButton() == MouseEvent.BUTTON1){
                Lost = true;
                for (int o = 0; o < b.getSize(); o++){
                    for (int p = 0; p < b.getSize(); p++){
                if (b.AllTiles[o][p].isBomb() == true)
                    Tiles[o][p].setBackground(VERY_DARK_RED);
                }
                }
                Botpanellabel.setText("You Lose!");
                return;
            }
            else{
                int row = i,col = j;
                g.ClearEmptySpace(b, row, col);
                UpdateTiles();
            }
        }
            if (e.getSource() == Tiles[i][j] && e.getButton() == MouseEvent.BUTTON3 && b.AllTiles[i][j].isHidden()){
                if (b.AllTiles[i][j].isFlagged())
                    b.AllTiles[i][j].setFlagged(false);
                else{
                    b.AllTiles[i][j].setFlagged(true);
                }
                UpdateTiles();
            }
        }
        }
        }
        CheatingMode(b);
        if (g.WinCon(b, hardbombcount)){
            Won = true;
            for (int o = 0; o < b.getSize(); o++){
                for (int p = 0; p < b.getSize(); p++){
                    if (b.AllTiles[o][p].isBomb() == true)
                        Tiles[o][p].setBackground(Color.GREEN);
                }
            }
            Botpanellabel.setText("You Win!");
        }
    }
    
    public void UpdateTiles(){
    for (int i = 0; i < b.getSize(); i++){
    for (int j = 0; j < b.getSize(); j++){
    if (b.AllTiles[i][j].isHidden())
        Tiles[i][j].setBackground(HIDDEN_TILE);
    else
        Tiles[i][j].setBackground(EMPTY_TILE);
    if (!b.AllTiles[i][j].isFlagged())
        Tiles[i][j].setText("");
    if (b.AllTiles[i][j].isFlagged() && !b.AllTiles[i][j].isHidden()){
        b.AllTiles[i][j].setFlagged(false);
            Tiles[i][j].setText("");
    }
    if (b.AllTiles[i][j].isFlagged() && b.AllTiles[i][j].isHidden()){
        Tiles[i][j].setText("F");
        Tiles[i][j].setForeground(FLAG);
    }
    if (!b.AllTiles[i][j].isHidden() && !b.AllTiles[i][j].isBomb()){
    switch(b.AllTiles[i][j].getNumbered()){
//        case 0:
//            Tiles[i][j].setText("0");
//            break;
        case 1:
            Tiles[i][j].setForeground(ONE);
            Tiles[i][j].setText("1");
            break;
        case 2:
            Tiles[i][j].setForeground(TWO);
            Tiles[i][j].setText("2");
            break;
        case 3:
            Tiles[i][j].setForeground(THREE);
            Tiles[i][j].setText("3");
            break;
        case 4:
            Tiles[i][j].setForeground(FOUR);
            Tiles[i][j].setText("4");
            break;
        case 5:
            Tiles[i][j].setForeground(FIVE);
            Tiles[i][j].setText("5");
            break;
        case 6:
            Tiles[i][j].setForeground(SIX);
            Tiles[i][j].setText("6");
            break;
        case 7:
            Tiles[i][j].setForeground(SEVEN);
            Tiles[i][j].setText("7");
            break;
        case 8:
            Tiles[i][j].setForeground(EIGHT);
            Tiles[i][j].setText("8");
            break;
    }
    }
    }
    }
    frame.revalidate();
    frame.repaint();
    }
    
        public void CheatingMode(Board b){
            //CHEATNG MODE
            if (cheat){
            for (int i = 0; i < b.getSize(); i++){
                for (int j = 0; j < b.getSize(); j++ ){    
        if (b.AllTiles[i][j].isBomb() && b.AllTiles[i][j].isHidden())
           Tiles[i][j].setBackground(Color.RED);
                }
        //CHEATING MODE
                   }
            }
        }
    public GameUI(){
//initializations
Lost = false;
Won = false;
cheat = false;
clicks = 0;
b = new Board(hardsize);
g = new Engine(b,hardbombcount);
//ALL PANELS
        gamepanel = new JPanel();
        Toppanel = new JPanel();
        Botpanel = new JPanel();
        Botpanel.setLayout(new BorderLayout());
        Toppanel.setLayout(new BorderLayout());
        ResetB = new JButton("Reset");
        Botpanellabel = new JLabel(" ");
        Botpanellabel.setHorizontalAlignment(SwingConstants.CENTER);
        Botpanel.add(Botpanellabel);
        Toppanel.add(ResetB);
        ResetB.addMouseListener(this);
        gamepanel.setLayout(new GridLayout(b.getSize(),b.getSize()));
        gamepanel.add(Toppanel);
        gamepanel.setBackground(new Color(150,150,150));
        Tiles = new JButton[b.getSize()][b.getSize()];
                for (int i = 0; i < b.getSize(); i++){
                    for (int j = 0; j < b.getSize(); j++){
                        Tiles[i][j] = new JButton();
                        gamepanel.add(Tiles[i][j]);
                        Tiles[i][j].setBackground(HIDDEN_TILE);
                        Tiles[i][j].addMouseListener(this);
                    }
                }
//JFRAME                
                frame = new JFrame("Popusaas MineSweeper 18x18");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setBounds(500, 500, hardgrid, hardgrid);
                frame.add(gamepanel,BorderLayout.CENTER);
                frame.add(Toppanel,BorderLayout.NORTH);
                frame.add(Botpanel,BorderLayout.SOUTH);
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
        } 

    @Override
    public void mouseClicked(MouseEvent e) {
        return;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
return;
        }

    @Override
    public void mouseEntered(MouseEvent e) {
        return;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        return;
    }
}