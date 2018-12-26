package assignment4Game;

import java.io.*;

public class Game {
    
    public static int play(InputStreamReader input){
        BufferedReader keyboard = new BufferedReader(input);
        Configuration c = new Configuration();
        int columnPlayed = 3; int player;
        
        // first move for player 1 (played by computer) : in the middle of the grid
        c.addDisk(firstMovePlayer1(), 1);
        int nbTurn = 1;
        
        while (nbTurn < 42){ // maximum of turns allowed by the size of the grid
            player = nbTurn %2 + 1;
            if (player == 2){
                columnPlayed = getNextMove(keyboard, c, 2);
            }
            if (player == 1){
                columnPlayed = movePlayer1(columnPlayed, c);
            }
            System.out.println(columnPlayed);
            c.addDisk(columnPlayed, player);
            if (c.isWinning(columnPlayed, player)){
                c.print();
                System.out.println("Congrats to player " + player + " !");
                return(player);
            }
            nbTurn++;
        }
        return -1;
    }
    
    public static int getNextMove(BufferedReader keyboard, Configuration c, int player) {
        
        int column=-1;
        do {
            c.print();
            System.out.println("Enter Column number  0-6: ");
            try {
                column=keyboard.read()-48;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }while(column<0 || column>6 || c.available[column]>5);
        
        return column; 
    }
    
    public static int firstMovePlayer1 (){
        return 3;
    }
    
    public static int movePlayer1 (int columnPlayed2, Configuration c){
        int move=c.canWinNextRound(1);
        if(move!=-1) {
            return move;
        }
        move=c.canWinTwoTurns(1);
        if(move!=-1) {
            return move;
        }
        if(c.available[columnPlayed2]<6) {
            return columnPlayed2;
        }else {
            for (int i = 1; columnPlayed2 + i < 7 || columnPlayed2 - i >= 0; i++) {
                if (columnPlayed2 - i >= 0 && c.available[columnPlayed2 - i] < 6) {
                    return columnPlayed2 - i;
                } else if (columnPlayed2 + i < 7 && c.available[columnPlayed2 + i] < 6) {
                    return columnPlayed2 + i;
                }
            }
        }
        
        return 0;
    }
    
}

