package it.polimi.ingsw;


public class Board {
    private static final int DIM = 5;
    private static Box[][] matrix;

    Board(){
        matrix= new Box[DIM][DIM];
        for(int i=0;i<DIM;i++){
            for(int j=0;j<DIM;j++){
                matrix[i][j]=new Box();
            }
        }
    }
    public void build(int r, int c){
        matrix[r][c].build();
    }
    public void clear(){
        for(int i=0;i<DIM;i++){
            for(int j=0;j<DIM;j++){
                matrix[i][j].clear();
            }
        }
    }
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
    public void print(){
        for(int i=0;i<DIM;i++){
            for(int j=0;j<DIM;j++){
                matrix[i][j].print();
            }
            System.out.println();
        }
    }
    public static void main( String[] args ){
        Board b= new Board();
        b.clear();
        b.build(0,0);
        b.build(0,0);
        b.build(1,2);
        b.print();

    }
}
