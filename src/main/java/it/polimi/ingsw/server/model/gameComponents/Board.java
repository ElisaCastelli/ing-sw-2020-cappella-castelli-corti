package it.polimi.ingsw.server.model.gameComponents;
import it.polimi.ingsw.client.Color;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents the board where the game will be played
 */
public class Board implements Serializable{

    /**
     * Static attribute that indicates the number of rows and columns of  matrix that represents the board
     */
    private final int DIM = 5;
    /**
     * Matrix of boxes that represents the playing board
     */
    private final Box[][] matrix;

    /**
     * Constructor without parameters
     */
    public Board(){
        matrix = new Box[DIM][DIM];
        for(int i = 0; i < DIM; i++){
            for(int j = 0; j < DIM; j++){
                matrix[i][j] = new Box(i, j);
            }
        }
        setBoxesNext();
    }


    public void setBoxesNext(){
        for(int i = 0; i < DIM; i++){
            for(int j = 0; j < DIM; j++){
                matrix[i][j].setBoxesNextTo(getBoxesNextTo(i,j));
            }
        }
    }

    /**
     * This method launches the method clear for each box of the matrix
     */
    public void clear(){
        for(int i = 0; i < DIM; i++){
            for(int j = 0; j < DIM; j++){
                matrix[i][j].clear();
            }
        }
    }


    /**
     * @return true if each box of the matrix is empty else return false
     */
    public boolean isEmpty(){
        boolean trovato = false;
        int i = 0, j = 0;
        while(!trovato && i < DIM){
                while(!trovato && j < DIM){
                    if(matrix[i][j].isEmpty()){
                        trovato = true;
                    }
                    j++;
                }
                i++;
        }
        return trovato;
    }

    /**
     *
     * @param row indicates the row of the box in the matrix i want
     * @param column indicates the column of the box in the matrix i want
     * @return the box in position matrix[row][column]
     */
    public Box getBox(int row, int column){
        return matrix[row][column];
    }

    //Quinta pos è quella che ho chiesto
    public ArrayList<Box> getBoxesNextTo(int row, int column){
        ArrayList<Box> nextTo = new ArrayList<>();
        for(int r = row-1 ; r <= row+1; r++){
            for(int c = column-1; c <= column+1; c++){
                if(!(r==row && c==column)){
                    if( r>=0 && r<5 && c>=0 && c<5 ){
                        nextTo.add(getBox(r,c));
                    }
                    else
                        nextTo.add(null);
                }
            }
        }
        return nextTo;
    }

    /**
     * This method prints the content of the entire board
     */

    /**
     * This method prints the content of the entire board with the reachable
     */


    public void clearReachable(){
        for(int i = 0; i < DIM; i++){
            for(int j = 0; j < DIM; j++){
                matrix[i][j].clearBoxesNextTo();
            }
        }
    }

}
