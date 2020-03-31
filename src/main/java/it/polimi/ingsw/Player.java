package it.polimi.ingsw;

import it.polimi.ingsw.gamerstate.GamerStateManager;
import it.polimi.ingsw.god.God;
import it.polimi.ingsw.god.Gods;

public class Player {
    /**
     * This is the name of the player
     */
    private String name;

    /**
     * Player's age
     */
    private int age;

    /**
     * This is the god card drawn by the player
     */
    private God myGod;

    /**
     * This array of Workers contains two workers for each player
     */
    private Worker myWorkers[];

    GamerStateManager gamerManager = new GamerStateManager();

    /**
     * Constructor with name and board as parameters
     * @param age
     * @param name
     */
    Player(String name, int age){
        this.name=name;
        myWorkers= new Worker[2];
        myWorkers[0]= new Worker(1,name);
        myWorkers[1]= new Worker(2,name);
        this.age=age;
        myGod=null;
    }
    public void setGod(String nameGod){
        myGod=new Gods(nameGod);
        //TO DO swtch in base al nome decidere decorator
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public int getAge() {
        return age;
    }
    /**
     * This method changes the attributes of a gamer with attributes of another, and the other way around
     * @param player2
     */
    public void swap(Player player2){
        Player newPlayer=new Player(player2.name,player2.age/*, gamer2.board*/);
        player2.setName(this.name);
        player2.setAge(this.age);
        //gamer2.setBoard(this.board);
        this.setName(newPlayer.name);
        this.setAge(newPlayer.age);
        //this.setBoard(newGamer.board);
    }
    public void print(){
        System.out.println(name+ " "+ age);
    }

    /**
     * This method sets the first position of a worker
     * @param index is the number of the worker i want to set
     * @param requestedBox is the box where i want to set the worker
     * @return true if initialization is successful, else false
     */
    //ELISA MANNAGGIA COSA GLIELA PASSI A FARE LA BOX????
    //MANNACCCCCCCCCIA
    public boolean initializeWorker(int index, Box requestedBox){
        if(myWorkers[index-1].initializePos(requestedBox)==true){
            return true;
        }
        return false;
    }

    public boolean checkPossibleMove(Box actualBox,Board boardToControl){
        for(int i=actualBox.getRow()-1;i<=actualBox.getRow()+1;i++){
            for(int j=actualBox.getColumn()-1;j<=actualBox.getColumn()+1;j++){
                if((boardToControl.getBox(i,j).notWorker()&& boardToControl.getBox(i,j).getCounter()!=4)
                        && (((boardToControl.getBox(i,j).getCounter()-actualBox.getCounter())==1)||((actualBox.getCounter()-boardToControl.getBox(i,j).getCounter())>=0))){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param finalBox
     * @param boardToControl
     * @return 0 if you can't build otherwise it will return the index of the worker nearby
     */
    public int checkPossibleBuild(Box finalBox, Board boardToControl){
        for(int i=finalBox.getRow()-1;i<=finalBox.getRow()+1;i++){
            for(int j=finalBox.getColumn()-1;j<=finalBox.getColumn()+1;j++){
                if(!boardToControl.getBox(i,j).notWorker() && boardToControl.getBox(i,j).getWorker().getGamerName()==name){
                    return boardToControl.getBox(i,j).getWorker().getWorkerId();
                }
            }
        }
        return 0;
    }

    public boolean playWorker(int indexWorker, Board board, int row,int column){
        boolean movedWorker=false;
        movedWorker=myGod.moveWorker(myWorkers[indexWorker],board.getBox(row,column),myGod.getGodName());
        if(movedWorker==true){
            return true;
        }
        return false;
    }

    public boolean playBlock(Board board,int row, int column){
        boolean movedBlock=false;

        int indexWorker=checkPossibleBuild(board.getBox(row,column),board);
        movedBlock=myGod.moveBlock(myWorkers[indexWorker], board.getBox(row,column),myGod.getGodName());
        if(movedBlock==true){
            return true;
        }
        return false;
    }

    public boolean checkWin( int indexWorkerMoved,Board board, int row, int column){ //index già giusto
        if(myGod.checkWin(board.getBox(row,column), myWorkers[indexWorkerMoved].getActualBox(),myGod.getGodName())==true){
            //gamerManager.moveToWin();
            return true;
        }
        return false;
    }

    /*public static void main( String[] args )
    {
        Board b= new Board();
        Gamer g= new Gamer("Io",b);
        Box box= new Box(0,1,1);
        if(g.initializeWorker(1,box)==true){
            System.out.println("ok");
        }
        else{
            System.out.println("mannaggia la miseria");
        }

    }*/
}
