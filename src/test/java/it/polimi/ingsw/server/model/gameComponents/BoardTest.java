package it.polimi.ingsw.server.model.gameComponents;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    final Board boardTest = new Board();

    @Test
    void clear() {
        boardTest.clear();
        for(int r=0;r<5;r++){
            for(int c=0;c<5;c++){
                assertTrue(boardTest.getBox(r, c).isEmpty());
            }
        }
    }

    @Test
    void isEmpty() {
        boardTest.clear();
        assertTrue(boardTest.isEmpty());
    }


    @Test
    void setBoxesNextTo() {
        boardTest.setBoxesNext();
        for(int row=0; row<5; row++){
            for(int column=0;column<5;column++){
                assertNotEquals(null,boardTest.getBoxesNextTo(row,column));
            }
        }
    }

    @Test
    void getBoxesNextTo() {
        ArrayList<Box> boxesNext = new ArrayList<>();
        for(int row=0; row<5; row++){
            for(int column=0; column<5; column++){
                boardTest.getBoxesNextTo(row,column);
                if(row==0 && column==0){
                    assertNull(boardTest.getBox(row, column).getBoxesNextTo().get(0));
                    assertNull(boardTest.getBox(row, column).getBoxesNextTo().get(1));
                    assertNull(boardTest.getBox(row, column).getBoxesNextTo().get(2));
                    assertNull(boardTest.getBox(row, column).getBoxesNextTo().get(3));
                    assertEquals(boardTest.getBox(row, (column+1)), boardTest.getBox(row,column).getBoxesNextTo().get(4));
                    assertNull(boardTest.getBox(row, column).getBoxesNextTo().get(5));
                    assertEquals(boardTest.getBox((row+1),column), boardTest.getBox(row,column).getBoxesNextTo().get(6));
                    assertEquals(boardTest.getBox((row+1),(column+1)), boardTest.getBox(row,column).getBoxesNextTo().get(7));
                }
                else if(row==0 && column<4){
                    assertNull(boardTest.getBox(row, column).getBoxesNextTo().get(0));
                    assertNull(boardTest.getBox(row, column).getBoxesNextTo().get(1));
                    assertNull(boardTest.getBox(row, column).getBoxesNextTo().get(2));
                    assertEquals(boardTest.getBox(row, (column-1)), boardTest.getBox(row,column).getBoxesNextTo().get(3));
                    assertEquals(boardTest.getBox(row, (column+1)), boardTest.getBox(row,column).getBoxesNextTo().get(4));
                    assertEquals(boardTest.getBox((row+1), (column-1)), boardTest.getBox(row,column).getBoxesNextTo().get(5));
                    assertEquals(boardTest.getBox((row+1), (column)), boardTest.getBox(row,column).getBoxesNextTo().get(6));
                    assertEquals(boardTest.getBox((row+1), (column+1)), boardTest.getBox(row,column).getBoxesNextTo().get(7));
                }
                else if(row==0 && column==4){
                    assertNull(boardTest.getBox(row, column).getBoxesNextTo().get(0));
                    assertNull(boardTest.getBox(row, column).getBoxesNextTo().get(1));
                    assertNull(boardTest.getBox(row, column).getBoxesNextTo().get(2));
                    assertEquals(boardTest.getBox(row, (column-1)), boardTest.getBox(row,column).getBoxesNextTo().get(3));
                    assertNull(boardTest.getBox(row, column).getBoxesNextTo().get(4));
                    assertEquals(boardTest.getBox((row+1), (column-1)), boardTest.getBox(row,column).getBoxesNextTo().get(5));
                    assertEquals(boardTest.getBox((row+1), (column)), boardTest.getBox(row,column).getBoxesNextTo().get(6));
                    assertNull(boardTest.getBox(row, column).getBoxesNextTo().get(7));
                }
                else if(column==0 && row<4){
                    assertNull(boardTest.getBox(row, column).getBoxesNextTo().get(0));
                    assertEquals(boardTest.getBox((row-1),column), boardTest.getBox(row,column).getBoxesNextTo().get(1));
                    assertEquals(boardTest.getBox((row-1), (column+1)), boardTest.getBox(row,column).getBoxesNextTo().get(2));
                    assertNull(boardTest.getBox(row, column).getBoxesNextTo().get(3));
                    assertEquals(boardTest.getBox(row, (column+1)), boardTest.getBox(row,column).getBoxesNextTo().get(4));
                    assertNull(boardTest.getBox(row, column).getBoxesNextTo().get(5));
                    assertEquals(boardTest.getBox((row+1), (column)), boardTest.getBox(row,column).getBoxesNextTo().get(6));
                    assertEquals(boardTest.getBox((row+1), (column+1)), boardTest.getBox(row,column).getBoxesNextTo().get(7));
                }
                else if( row==4 && column==0 ){
                    assertNull(boardTest.getBox(row, column).getBoxesNextTo().get(0));
                    assertEquals(boardTest.getBox((row-1),column), boardTest.getBox(row,column).getBoxesNextTo().get(1));
                    assertEquals(boardTest.getBox((row-1), (column+1)), boardTest.getBox(row,column).getBoxesNextTo().get(2));
                    assertNull(boardTest.getBox(row, column).getBoxesNextTo().get(3));
                    assertEquals(boardTest.getBox(row, (column+1)), boardTest.getBox(row,column).getBoxesNextTo().get(4));
                    assertNull(boardTest.getBox(row, column).getBoxesNextTo().get(5));
                    assertNull(boardTest.getBox(row, column).getBoxesNextTo().get(6));
                    assertNull(boardTest.getBox(row, column).getBoxesNextTo().get(7));
                }
                else if(row == 4 && column<4){
                    assertEquals(boardTest.getBox((row-1), (column-1)), boardTest.getBox(row,column).getBoxesNextTo().get(0));
                    assertEquals(boardTest.getBox((row-1),column), boardTest.getBox(row,column).getBoxesNextTo().get(1));
                    assertEquals(boardTest.getBox((row-1), (column+1)), boardTest.getBox(row,column).getBoxesNextTo().get(2));
                    assertEquals(boardTest.getBox(row, (column-1)), boardTest.getBox(row,column).getBoxesNextTo().get(3));
                    assertEquals(boardTest.getBox(row, (column+1)), boardTest.getBox(row,column).getBoxesNextTo().get(4));
                    assertNull(boardTest.getBox(row, column).getBoxesNextTo().get(5));
                    assertNull(boardTest.getBox(row, column).getBoxesNextTo().get(6));
                    assertNull(boardTest.getBox(row, column).getBoxesNextTo().get(7));
                } else if (row==4 && column==4) {
                    assertEquals(boardTest.getBox((row-1), (column-1)), boardTest.getBox(row,column).getBoxesNextTo().get(0));
                    assertEquals(boardTest.getBox((row-1),column), boardTest.getBox(row,column).getBoxesNextTo().get(1));
                    assertNull(boardTest.getBox(row, column).getBoxesNextTo().get(2));
                    assertEquals(boardTest.getBox(row, (column-1)), boardTest.getBox(row,column).getBoxesNextTo().get(3));
                    assertNull(boardTest.getBox(row, column).getBoxesNextTo().get(4));
                    assertNull(boardTest.getBox(row, column).getBoxesNextTo().get(5));
                    assertNull(boardTest.getBox(row, column).getBoxesNextTo().get(6));
                    assertNull(boardTest.getBox(row, column).getBoxesNextTo().get(7));
                }
                else if(row<4 && column==4){
                    assertEquals(boardTest.getBox((row-1), (column-1)), boardTest.getBox(row,column).getBoxesNextTo().get(0));
                    assertEquals(boardTest.getBox((row-1),column), boardTest.getBox(row,column).getBoxesNextTo().get(1));
                    assertNull(boardTest.getBox(row, column).getBoxesNextTo().get(2));
                    assertEquals(boardTest.getBox(row, (column-1)), boardTest.getBox(row,column).getBoxesNextTo().get(3));
                    assertNull(boardTest.getBox(row, column).getBoxesNextTo().get(4));
                    assertEquals(boardTest.getBox((row+1), (column-1)), boardTest.getBox(row,column).getBoxesNextTo().get(5));
                    assertEquals(boardTest.getBox((row+1), (column)), boardTest.getBox(row,column).getBoxesNextTo().get(6));
                    assertNull(boardTest.getBox(row, column).getBoxesNextTo().get(7));
                }
                else{
                    assertEquals(boardTest.getBox((row-1), (column-1)), boardTest.getBox(row,column).getBoxesNextTo().get(0));
                    assertEquals(boardTest.getBox((row-1),column), boardTest.getBox(row,column).getBoxesNextTo().get(1));
                    assertEquals(boardTest.getBox((row-1), (column+1)), boardTest.getBox(row,column).getBoxesNextTo().get(2));
                    assertEquals(boardTest.getBox(row, (column-1)), boardTest.getBox(row,column).getBoxesNextTo().get(3));
                    assertEquals(boardTest.getBox(row, (column+1)), boardTest.getBox(row,column).getBoxesNextTo().get(4));
                    assertEquals(boardTest.getBox((row+1), (column-1)), boardTest.getBox(row,column).getBoxesNextTo().get(5));
                    assertEquals(boardTest.getBox((row+1), (column)), boardTest.getBox(row,column).getBoxesNextTo().get(6));
                    assertEquals(boardTest.getBox((row+1), (column+1)), boardTest.getBox(row,column).getBoxesNextTo().get(7));
                }
            }
        }

    }
}