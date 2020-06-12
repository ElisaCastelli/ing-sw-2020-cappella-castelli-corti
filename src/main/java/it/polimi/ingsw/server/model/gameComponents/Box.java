package it.polimi.ingsw.server.model.gameComponents;
import it.polimi.ingsw.server.model.building.*;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * This class represents the boxes that make up the board
 */
public class Box implements Serializable {

    /**
     * This attribute is a building that can be built in this box
     */
    private final Building building;
    /**
     * This attribute is a worker that can occupy this box
     */
    private Worker worker;
    /**
     * This attribute indicates the row of the matrix where the box is located
     */
    private int row;
    /**
     * This attribute indicates the column of the matrix where the box is located
     */
    private int column;
    /**
     * This attribute indicates if a box is reachable by a worker or not
     */
    private boolean reachable;
    /**
     * This attribute memorizes the eight adjacent boxes of the box
     */
    private ArrayList<Box> boxesNextTo;
    /**
     * This attribute indicates which block can build
     */
    private ArrayList<Block> possibleBlock;

    /**
     * @param row row of the box
     * @param column column of the box
     * Constructor with parameters
     */
    public Box(int row, int column){ //controllare r e c da 0 a 5
        building = new Building();
        possibleBlock= new ArrayList<>();
        this.row = row;
        this.column = column;
        worker = null;
        reachable = false;
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

    public void setWorker(Worker worker){
        this.worker = worker;
    }

    public Worker getWorker() {
        return worker;
    }

    public int getCounter(){
        return building.getArrayOfBlocks().size();
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public ArrayList<Box> getBoxesNextTo() {
        return boxesNextTo;
    }

    public void setBoxesNextTo(ArrayList<Box> boxesNextTo){
        this.boxesNextTo = boxesNextTo;
    }

    public Building getBuilding() {
        return building;
    }

    public ArrayList<Block> getPossibleBlock() {
        return possibleBlock;
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
        worker=null;
    }

    /**
     * @return true if there is NOT a worker in the box, else return false
     */
    public boolean notWorker(){
        return worker == null;
    }

    /**
     * This method tells if the box is empty
     * @return true if there isn't any building or worker, else return false
     */
    public boolean isEmpty(){
        boolean vuota = true;
        if((getCounter() != 0) || (worker != null)){
            vuota = false;
        }
        return vuota;
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
    }

    /**
     * This method clears the adjacent boxes
     */
    public void clearBoxesNextTo(){
        for (Box nextTo : boxesNextTo) {
            if(nextTo != null){
                nextTo.getPossibleBlock().clear();
                nextTo.setReachable(false);
            }
        }
    }

    /**
     * This method checks if there are any reachable boxes
     * @return true if there is at least one reachable box, false if there are no reachable boxes
     */
    public boolean checkPossible(){
        boolean possible = false;
        int index = 0;
        while((index < boxesNextTo.size()) && !possible){
            if(boxesNextTo.get(index) != null){
                if(boxesNextTo.get(index).isReachable()){
                    possible = true;
                }
            }
            index++;
        }
        return possible;
    }

    /**
     * This method prints the content of the box
     */
    public String print(){
        return "█";
    }

    /**
     * This method prints the arrayOfBlock size
     * @return an int which represents the size of the array
     */
    public int printsize(){
        return Math.max(building.getArrayOfBlocks().size(), 0);
    }
}

