package it.polimi.ingsw;

import it.polimi.ingsw.god.God;

import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
    public int addNPlayer(){
        System.out.println("Inserire numero di giocatore: ");
        Scanner input = new Scanner(System.in);
        return Integer.parseInt(input.nextLine());
    }

    public String addNamePlayer(int indexPlayer){
        System.out.print("Inserire nome del giocatore numero "+ indexPlayer +": ");
        Scanner playerName = new Scanner(System.in);
        return playerName.nextLine();
    }

    public int addAgePlayer(int indexPlayer){
        System.out.print("Inserire età del giocatore numero "+ indexPlayer +": ");
        Scanner gamerAge = new Scanner(System.in);
        return Integer.parseInt(gamerAge.nextLine());
    }

    public int askRow(){
        System.out.print("Riga: ");
        Scanner r = new Scanner(System.in);
        return Integer.parseInt(r.nextLine());
    }

    public int askColumn(){
        System.out.print("Colonna dove voglio muovermi: ");
        Scanner c = new Scanner(System.in);
        return Integer.parseInt(c.nextLine());
    }

    public int askWorker(){
        System.out.println("Pedina da muovere [1] 0 [2]:");
        Scanner worker = new Scanner(System.in);
        return Integer.parseInt(worker.nextLine());
    }

    public God askGodCard(ArrayList<God> godsArray){
        for(int g = 0; g < godsArray.size(); g++){
            godsArray.get(g).toString();
        }
        System.out.println(" Quale carta vuoi scegliere? ");
        Scanner godCard = new Scanner(System.in);
        String nameCard= godCard.nextLine();
        God godDrawn = godsArray.get(godsArray.indexOf(nameCard));
        godsArray.remove(godsArray.indexOf(nameCard));
        return godDrawn;
    }

}
