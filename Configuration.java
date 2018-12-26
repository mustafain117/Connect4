package assignment4Game;

public class Configuration {
    
    public int[][] board;
    public int[] available;
    boolean spaceLeft;
    
    public Configuration(){
        board = new int[7][6];
        available = new int[7];
        spaceLeft = true;
    }
    
    public void print(){
        System.out.println("| 0 | 1 | 2 | 3 | 4 | 5 | 6 |");
        System.out.println("+---+---+---+---+---+---+---+");
        for (int i = 0; i < 6; i++){
            System.out.print("|");
            for (int j = 0; j < 7; j++){
                if (board[j][5-i] == 0){
                    System.out.print("   |");
                }
                else{
                    System.out.print(" "+ board[j][5-i]+" |");
                }
            }
            System.out.println();
        }
    }
    
    public void addDisk (int index, int player){
        int value= available[index];
        board[index][value]= player;
        available[index]++;
    }
    public void removeDisk(int index) {
        board[index][available[index] - 1] = 0;
        available[index]--;
    }
    
    public boolean isWinning(int lastColumnPlayed, int player) {
        int col = lastColumnPlayed;
        int row = available[col] - 1;
        row = (row < 0) ? 0 : row;
        int counter = player;
        int[][] grid = board;
        if (row > 2) {
            for (int i = (row - 1), j = 1; i >= 0; i--) {
                if (grid[col][i] != counter) {
                    break;
                }
                j++;
                if (j >= 4) {
                    return true;
                }
            }
        }
     
        int countHorizontal = 1;
        int j = 0;
        for (int i = 1; col + i < grid.length; i++) {
            if (grid[col + i][row] != counter) {
                break;
            } else {
                j++;
            }
        }
        countHorizontal += j;
        j = 0;
        for (int i = 1; col - i >= 0; i++) {
            if (grid[col - i][row] != counter) {
                break;
            } else {
                j++;
            }
        }
        countHorizontal += j;
        if (countHorizontal >= 4) {
            return true;
        }
        int countDiagonal = 1;
        j = 0;
        for (int i = 1; (row + i) < grid[col].length && (col + i) < grid.length; i++) {
            if (grid[col + i][row + i] != counter) {
                break;
            } else {
                j++;
            }
        }
        countDiagonal += j;
        j = 0;
        for (int i = 1; (row - i) >= 0 && (col - i) >= 0; i++) {
            if (grid[col - i][row - i] != counter) {
                break;
            } else {
                j++;
            }
        }
        countDiagonal += j;
        if (countDiagonal >= 4) {
            return true;
        }
        countDiagonal = 1;
        j = 0;
        for (int i = 1; (row + i) < grid[col].length && (col - i) >= 0; i++) {
            if (grid[col - i][row + i] != counter) {
                break;
            } else {
                j++;
            }
        }
        countDiagonal += j;
        j = 0;
        for (int i = 1; (row - i) >= 0 && (col + i) < grid.length; i++) {
            if (grid[col + i][row - i] != counter) {
                break;
            } else {
                j++;
            }
        }
        countDiagonal += j;
        if (countDiagonal >= 4) {
            return true;
        }
        return false; // DON'T FORGET TO CHANGE THE RETURN
    }
    
    
    public int canWinNextRound(int player) {
        for (int i = 0; i < board.length; i++) {
            if (available[i] < 6) {
                addDisk(i, player);
                boolean win = isWinning(i, player);
                removeDisk(i);
                if (win) {
                    return i;
                }
            }
            
        }
        return -1; // DON'T FORGET TO CHANGE THE RETURN
    }
    public int canWinTwoTurns(int player) {
        int player2 = (player == 1) ? 2 : 1;
        int can = -1;
        int can2 = -1;
    OUTER:
        for (int i = 0; i < available.length; i++) {
            if (available[i] < 6) {
                addDisk(i, player);
                can = canWinNextRound(player);
                if (can == -1) {
                    removeDisk(i);
                    continue OUTER;
                }
            INNER:
                for (int j = 0; j < available.length; j++) {
                    if (available[j] < 6) {
                        addDisk(j, player2);
                        can2 = canWinNextRound(player);
                        removeDisk(j);
                        if (can2 == -1) {
                            removeDisk(i);
                            continue OUTER;
                        }
                    }
                }
                removeDisk(i);
                return i;
            }
            
        }
        return -1;
    }
    
}

