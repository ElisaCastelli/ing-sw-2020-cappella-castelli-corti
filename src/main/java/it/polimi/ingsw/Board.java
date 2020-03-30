package it.polimi.ingsw;

/**
 * This class represents the board where the game will be played
 */
public class Board {
    /**
     * Static attribute that indicates the number of rows and columns of  matrix that represents the board
     */
    private static final int DIM = 5;
    /**
     * Matrix of boxes that represents the playing board
     */
    private Box[][] matrix;

    /**
     * Constructor without parameters
     */
    Board(){
        matrix= new Box[DIM][DIM];
        for(int i=0;i<DIM;i++){
            for(int j=0;j<DIM;j++){
                matrix[i][j]=new Box(i,j);
            }
        }
    }

    /**
     * This method launches the method clear for each box of the matrix
     */
    public void clear(){
        for(int i=0;i<DIM;i++){
            for(int j=0;j<DIM;j++){
                matrix[i][j].clear();
            }
        }
    }

    /**
     * @return true if each box of the matrix is empty else return false
     */
    public boolean isEmpty(){
        boolean trovato=false;
        int i=0,j=0;
        while(!trovato && i<DIM){
                while(!trovato && j<DIM){
                    if(matrix[i][j].isEmpty()){
                        trovato=true;
                    }
                    j++;
                }
                i++;
        }
        return trovato;
    }

    /**
     * This method prints the content of the entire board
     */
    public void print() {
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                matrix[i][j].print();
            }
            System.out.println();
        }
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

    /*public static void main( String[] args ){
        Board b= new Board();
        b.clear();
        b.build(0,0);
        b.build(0,0);
        b.build(1,2);
        b.print();
    }*/
}
