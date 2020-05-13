package it.polimi.ingsw.client;

import it.polimi.ingsw.network.User;
import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Player;
import it.polimi.ingsw.server.model.god.God;
import it.polimi.ingsw.server.model.playerState.IsPlaying;
import it.polimi.ingsw.server.model.playerState.IsWaiting;
import it.polimi.ingsw.server.model.playerState.PlayerState;

import java.util.ArrayList;
import java.util.Scanner;

public class CLIView extends View {
    private Scanner input = new Scanner(System.in);
    private int indexPlayer = -1;
    private boolean isPlaying;
    private int nPlayer;
    private ArrayList<User> usersArray;
    private Board board;


    @Override
    public void setBoard(Board board) {
        this.board=board;
        board.print();
    }

    @Override
    public void setUsers(ArrayList<User> users) {
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
    public synchronized ArrayList<Integer> ask3Card(ArrayList<String> cards) {
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
    public  synchronized int askCard(ArrayList<String> cards) {
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
}
