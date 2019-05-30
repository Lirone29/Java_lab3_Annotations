package game;

import java.util.Vector;

public class Board {
    int sizeOfBoard;
    Vector<String> positionOfBoard;

    public Board(int size) {
        sizeOfBoard = size;
        positionOfBoard = new Vector<String>();
        for(int i = 0; i < size*size; i++)
        {
            positionOfBoard.add(" ");
        }

    }

    public Vector<String> move(int positionNumber, String moveString){

        for(int i = 0; i<sizeOfBoard; i++) {
            for(int j= 0; j< sizeOfBoard; j++) {
                if((i*sizeOfBoard+j) == positionNumber )
                    positionOfBoard.set(positionNumber, moveString);
            }
        }

        return this.positionOfBoard;
    }

    public Vector<String> getBoard(){
        return positionOfBoard;
    }

}
