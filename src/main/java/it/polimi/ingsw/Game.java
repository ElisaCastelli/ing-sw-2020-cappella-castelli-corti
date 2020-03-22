package it.polimi.ingsw;

public class Game {
    private Board board;
    private Gamer gamers[];
    private  int nPlayer;


    Game(){
        board= new Board();
        gamers= new Gamer[3];
        for(int i=0;i<3;i++){
            gamers[i]=new Gamer();
        }
        nPlayer=0;
    }
    /*public void build(int r, int c){
        board.build(r,c);
    }
    public void print(){
        board.print();
    }*/
    public void play(){
        //ricevo partecipanti

        //getsisco i turni
    }
    /*public static void main( String[] args )
    {
        Game g= new Game();
        g.board.build(0,0);
        g.board.build(1,0);
        g.board.build(0,0);
        g.print();
    }*/
}
