package it.polimi.ingsw;
import it.polimi.ingsw.building.*;

import java.util.ArrayList;


/**
 * This class represents the boxes that make up the board
 */
public class Box {
    /**
     * This attribute is a building that can be built in this box
     */
    private Building building;
    /**
     * This attribute is a worker that can occupy this box
     */
    private Worker worker; //MAGARI POTREMMO TOGLIERLO
    /**
     * This attribute indicates the row of the matrix where the box is located
     */
    private int row;
    /**
     * This attribute indicates the column of the matrix where the box is located
     */
    private int column;
    private boolean reachable;
    private ArrayList<Box> boxesNextTo = new ArrayList<>();

    /**
     *
     * @param row row of the box
     * @param column column of the box
     * Constructor with parameters
     */
    public Box(int row, int column, ArrayList<Box> boxesNextTo){ //controllare r e c da 0 a 5
        building = new Building();
        this.row = row;
        this.column = column;
        worker  =null;
        this.boxesNextTo = boxesNextTo;
    }

    /**
     * Row getter
     * @return the attribute row
     */
    public int getRow() {
        return row;
    }

    /**
     * Column getter
     * @return the attribute column
     */
    public int getColumn() {
        return column;
    }

    public Worker getWorker() {
        return worker;
    }

    public int getCounter(){
        return building.getArrayOfBlocks().size();
    }

    public ArrayList<Box> getBoxesNextTo() {
        return boxesNextTo;
    }

    public boolean isReachable() {
        return reachable;
    }

    public void setReachable(boolean reachable) {
        this.reachable = reachable;
    }

    /**
     * This method clear the attributes of a box setting them on default values
     */
    public void clear(){
        building.clear();
        if( worker != null ){
            worker.clear();
        }
        worker=null;
    }

    /**
     * This method remove the worker from the box
     */
    public void clearWorker(){
        if( worker != null ){
            worker.clear();
        }
        worker=null;
    }

    /**
     * @return true if there is NOT a worker in the box, else return false
     */
    public boolean notWorker(){
        if( worker == null ){
            return true;
        }
        else return false;
    }

    /**
     * This method tells if the box is empty
     * @return true if there isn't any building or worker, else return false
     */
    public boolean isEmpty(){
        boolean vuota = true;
        if((building.getArrayOfBlocks().size() != 0) || (worker != null)){
            vuota = false;
        }
        return vuota;
    }

    ///TODO...MA IL SETWORKER LO UTILIZZI QUANDO SPOSTI IL WORKER SULLA BOARD DA UNA POS ALL'ALTRA?
    //PERCHE IN QUESTO CASO NON CONTROLLA SE IL WORKER E' POSIZIONATO IN UN'ALTRA CASELLA
    //HO FATTO ILA!
    public void setWorker(Worker worker){
        this.worker = worker;
    }

    /**
     * This method calls the method build of Building if the counter is less than five
     */
    public void build(){
        building.build();
    }

    /**
     * this method is used by Atlas to build a dome at any case
     * @param domeIdentifier is set to 4 to identify the Dome
     */
    public void build(int domeIdentifier){
        building.build(domeIdentifier);
    };

    public void clearBoxesNextTo(){
        for(int index=0; index < boxesNextTo.size(); index++){
            boxesNextTo.get(index).setReachable(false);
        }
    }

    public boolean checkPossibleMove(){
        boolean possible=false;
        int index=0;
        while(index < boxesNextTo.size() && !possible){
            if(boxesNextTo.get(index).isReachable()){
                possible=true;
            }
            index++;
        }
        return possible;
    }

    /**
     * This method prints the content of the box
     */
    public void print(){
        if( worker!=null ) {
            System.out.print("["+worker.toString()+"] ");
        }
        else if ( building!=null ){
            building.print();
        }
        System.out.print("[" + building.getArrayOfBlocks().size() + "] ");
    }
}

