package it.polimi.ingsw;

import it.polimi.ingsw.building.Building;

public class Box {
    private Building building;
    private int row;
    private int column;
    private int counter;
    private Worker worker;

    Box(){
        building=new Building();
        counter=0;
        row=0;
        column=0;
        worker=null;
    }

    public Box(int counter,int row, int column){ //controllare r e c da 0 a 5
        this.counter=counter;
        this.row=row;
        this.column=column;
    }
    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public int getColumn() {
        return column;
    }
    public void setColumn(int column) {
        this.column = column;
    }
    public int getCounter() {
        return counter;
    }
    public void setCounter(int counter) {
        this.counter = counter;
    }
    public void clear(){
        building.clear();
        counter=0;
        row=0;
        column=0;
    }
    public boolean notWorker(){
        if(worker==null) return true;
        else return false;
    }
    public boolean isEmpty(){
        boolean vuota=true;
        if(counter!=0 && worker!=null){
            vuota=false;
        }
        return vuota;
    }
    public void setWorker(Worker worker){
        this.worker=worker;
    }
    public void build(){
        //se la casella ha meno di 4 pezzi ed è adiacente a una pedina costruisce

        ///TO DO riguarda il counter del building perchè serve un identificatore per il pezzo da costruire

        if(counter<5 /*&& (reachable(worker1.getPos()) || reachable(worker2.getPos())*/){
            //HO MODIFICATO IL CONTATORE PER VEDERE SE FUNZIONAVA
            counter++;
            building.build(counter);

        }
    }
    ///TO DO METHOD FOR ATLAS FOR BUILDING DOME EVERYWHERE
    public void build(int blockId){
        //se la casella ha meno di 4 pezzi ed è adiacente a una pedina costruisce

        ///TO DO riguarda il counter del building perchè serve un identificatore per il pezzo da costruire

        if(counter<5 /*&& (reachable(worker1.getPos()) || reachable(worker2.getPos())*/){
            //HO MODIFICATO IL CONTATORE PER VEDERE SE FUNZIONAVA
            counter++;
            building.build(counter);

        }

    }

    //controllo se box2 è raggiungibile e vuota partendo da this
    public boolean reachable(Box box2){
        boolean reachable=false;
        int row2= box2.getRow();
        int column2=box2.getColumn();
        if(this.row-row2<=1 || row2-this.row<=1 || column2-this.column<=1 || this.column-column2<=1){
                reachable=true;
        }
        return reachable;
    }

    public void print(){
        /*if(worker!=null) worker.print();
        else if (building!=null)
        else{}*/
        building.print();
        System.out.print("["+counter+"] ");
    }
}
