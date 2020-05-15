package it.polimi.ingsw.client;

import it.polimi.ingsw.network.*;
import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;

import java.util.ArrayList;
import java.util.Scanner;

public class CLIView extends View {
    private Scanner input = new Scanner(System.in);
    private int indexPlayer = -1;
    private boolean isPlaying;
    private int nPlayer;
    private ArrayList<ClientUser> usersArray;
    private Board board;


    @Override
    public void setBoard(Board board) {
        this.board=board;
        board.print();
    }

    @Override
    public void setUsers(ArrayList<ClientUser> users) {
        usersArray=users;
    }

    @Override
    public int askNPlayer() {
        System.out.println("Numero giocatori:");
        return input.nextInt();
    }

    @Override
    public String askName() {
        System.out.println("Nome giocatore:");
        return input.next();
    }

    @Override
    public int askAge() {
        System.out.println("Età giocatore:");
        return input.nextInt();
    }

    @Override
    public void setNPlayer(int nPlayer){
        this.nPlayer=nPlayer;
    }

    @Override
    public void setIndexPlayer(int indexPlayer){
        this.indexPlayer=indexPlayer;
    }

    @Override
    public int getIndexPlayer(){
        return indexPlayer;
    }

    @Override
    public void setPlaying(boolean isPlaying){
        this.isPlaying=isPlaying;
    }

    @Override
    public boolean isPlaying(){
        return isPlaying;
    }

    @Override
    public ArrayList<Integer> ask3Card(ArrayList<String> cards) {
        ArrayList<Integer> cardTemp= new ArrayList<>();
        boolean[] scelte= new boolean[cards.size()];
        for(int i=0;i<cards.size();i++){
            scelte[i]=false;
        }
        System.out.println("Scegli gli indici di " + nPlayer + " carte");
        for(int cardIndex=0; cardIndex < cards.size();cardIndex++){
            System.out.println("[ "+cardIndex+ "] "+cards.get(cardIndex));
        }
        while(cardTemp.size()<nPlayer){
            int cardDrawn= input.nextInt();
            if(!scelte[cardDrawn] && cardDrawn<9){
                System.out.println("Scelta carta numero "+ cardDrawn);
                cardTemp.add(cardDrawn);
                scelte[cardDrawn]=true;
            }
        }
        return cardTemp;
    }


    @Override
    public int askCard(ArrayList<String> cards) {
        boolean choose=false;
        int scelta=4;
        for(int index=0;index<cards.size();index++){
            System.out.println("[ "+index+ "] "+cards.get(index));
        }
        System.out.println("Scegli la tua carta");
        while(!choose){
            scelta=input.nextInt();
            if(scelta<cards.size()){
                System.out.println("Scelta carta numero "+ scelta);
                choose=true;
            }
        }
        return scelta;
    }

    @Override
    public ArrayList<Box> initializeWorker(){
        ArrayList<Box> boxes= new ArrayList<>();
        for(int indexWorker=0;indexWorker<2;indexWorker++){
            board.print();
            System.out.println("Posiziona la pedina "+indexWorker);
            System.out.println("Riga: ");
            int row= input.nextInt();
            System.out.println("Colonna: ");
            int column= input.nextInt();
            if(board.getBox(row,column).notWorker()){
                if(boxes.size()==1 && (boxes.get(0).getRow()!=row && boxes.get(1).getColumn()!=column)){
                    boxes.add(board.getBox(row,column));
                    //System.out.println("Casella gia' occupata");
                }
                else{
                    boxes.add(board.getBox(row,column));
                    //System.out.println("Casella assegnata");
                }
            }

        }
        return boxes;
    }
}
