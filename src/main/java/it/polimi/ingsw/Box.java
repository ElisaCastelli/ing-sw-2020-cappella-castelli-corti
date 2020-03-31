package it.polimi.ingsw;

import it.polimi.ingsw.building.Building;

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



    /**
     *
     * @param row row of the box
     * @param column column of the box
     * Constructor with parameters
     */
    public Box(int row, int column){ //controllare r e c da 0 a 5
        building=new Building();
        this.row=row;
        this.column=column;
        worker=null;
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

    /**
     * Setter to change row
     * @param row
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Setter to change columnn
     * @param column
     */
    public void setColumn(int column) {
        this.column = column;
    }

    public Worker getWorker() {
        return worker;
    }

    public int getCounter(){
        return building.getArrayOfBlocks().size();
    }
    /**
     * This method clear the attributes of a box setting them on default values
     */
    public void clear(){
        building.clear();
        if(worker!=null)worker.clear();
        worker=null;
    }

    /**
     * This method remove the worker from the box
     */
    public void clearWorker(){
        if(worker!=null)worker.clear();
        worker=null;
    }

    /**
     * @return true if there is NOT a worker in the box, else return false
     */
    public boolean notWorker(){
        if(worker==null) return true;
        else return false;
    }

    /**
     * This method tells if the box is empty
     * @return true if there isn't any building or worker, else return false
     */
    public boolean isEmpty(){
        boolean vuota=true;
        if(building.getArrayOfBlocks().size()!=0 || worker!=null){
            vuota=false;
        }
        return vuota;
    }

    ///TO DO...MA IL SETWORKER LO UTILIZZI QUANDO SPOSTI IL WORKER SULLA BOARD DA UNA POS ALL'ALTRA?
    //PERCHE IN QUESTO CASO NON CONTROLLA SE IL WORKER E' POSIZIONATO IN UN'ALTRA CASELLA
    //HO FATTO ILA!
    public void setWorker(Worker worker){
        this.worker=worker;
        this.worker.setActualBox(this);
    }

    /**
     * This method calls the method build of Building if the counter is less than five
     */
    public void build(){
        building.build();
    }

    /**
     * This method checks if the parameter box2 is reachable starting from this box
     * @param boxToReach is the box I want to reach
     * @return true if boxToReach is reachable from this, else return false
     */
    public boolean reachable(Box boxToReach){
        boolean reachable=false;
        int row2= boxToReach.getRow();
        int column2=boxToReach.getColumn();
        if(this.row-row2<=1 || row2-this.row<=1 || column2-this.column<=1 || this.column-column2<=1){
            reachable=true;
        }
        return reachable;
    }

    /**
     * This method prints the content of the box
     */
    public void print(){
        if(worker!=null) {
            System.out.print("["+worker.toString()+"] ");
        }
        else if (building!=null){
            building.print();
        }
        System.out.print("["+building.getArrayOfBlocks().size()+"] ");
    }
}

