package org.rrhs.connectfour.game;

public class ConnectFour
{
    Piece[][] board; 
    Piece current; 
    public ConnectFour()
    {
        board = new Piece[6][7]; 
        current = Piece.red;
    }
    public Piece[][] board()
    {
        return board; 
    }

    public Piece getCurrentPlayer()
    {
        return current; 
    }

    public boolean isGameOver()
    {
        if(isEmpty())
        {
            return true; 
        }
        if(checkDiag()!=null || checkRows()!=null || checkCol()!=null)
        {
            return true; 
        }
        return false; 
    }
    public void removePiece(int col)
    {
        for(int r=0; r<board.length; r++)
        {
            if(board[r][col]!=null)
            {
                board[r][col] = null ;
                return; 
            }
        }
    }
    public Piece winner()
    {

        if(checkDiag()!=null || checkRows()!=null || checkCol()!=null)
        {
            if(checkRows()!=null)
            {
                return checkRows(); 
            }
            if(checkDiag()!=null)
            {
                return checkDiag(); 
            }

            if(checkCol()!=null)
            {
                return checkCol(); 
            }
        }
        return null;
    }

    public void nextPlayer()
    {
        current = current==Piece.red? Piece.yellow: Piece.red;
    }
    public Piece loc(int r, int c)
    {
        return board[r][c]; 
    }
    public boolean placePiece(int c) 
    {
        if(c>7 || c<0)
        {
            return false; 
        }
        for(int r=board.length-1; r>=0; r--)
        {
            if(board[r][c]==null)
            {
                board[r][c] = current; 

                return true; 
            }
        }
        return false; 
    }

    public boolean isEmpty()
    {
        for(int r=0; r<board.length; r++)
        {
            for(int c=0; c<board[0].length; c++)
            {
                if(board[r][c]==null)
                {
                    return false;  
                }
            }
        }
        return true; 
    }

    public Piece checkDiag()
    {
        for(int r=0; r<board.length; r++)
        {
            for(int c=0; c<board[0].length; c++)
            {
                if((r+3)<board.length && (c+3)<board[0].length)
                {
                    Piece a = board[r][c]; 
                    Piece b = board[r+1][c+1]; 
                    Piece s = board[r+2][c+2]; 
                    Piece d = board[r+3][c+3]; 
                    Piece check = connect4(a,b,s,d);  
                    if(check!=null)
                    {
                        return connect4(a,b,s,d);
                    }
                }
                if((r-3)>=0 && (c-3)>=0)
                {
                    Piece a = board[r][c]; 
                    Piece b = board[r-1][c-1]; 
                    Piece s = board[r-2][c-2]; 
                    Piece d = board[r-3][c-3]; 
                    Piece check = connect4(a,b,s,d); 
                    if(check!=null)
                    {
                        return connect4(a,b,s,d);
                    }
                }
                if((r+3)<board.length && (c-3)>=board[0].length)
                {
                    Piece a = board[r][c]; 
                    Piece b = board[r+1][c-1]; 
                    Piece s = board[r+2][c-2]; 
                    Piece d = board[r+3][c-3]; 
                    Piece check = connect4(a,b,s,d);  
                    if(check!=null)
                    {
                        return connect4(a,b,s,d);
                    }
                }
                if((r-3)>=0 && (c+3)<board[0].length)
                {
                    Piece a = board[r][c]; 
                    Piece b = board[r-1][c+1]; 
                    Piece s = board[r-2][c+2]; 
                    Piece d = board[r-3][c+3]; 
                    Piece check = connect4(a,b,s,d);  
                    if(check!=null)
                    {
                        return connect4(a,b,s,d);
                    }
                }
            }
        }
        return null; 
    }

    public Piece checkRows()
    {
        for(int r=0; r<board.length; r++)
        {
            for(int c=0; c<board[0].length - 3; c++)
            {
                Piece a = board[r][c]; 
                Piece b = board[r][c+1]; 
                Piece s = board[r][c+2]; 
                Piece d = board[r][c+3]; 

                Piece check = connect4(a,b,s,d); 
                if(check!=null)
                {
                    return connect4(a,b,s,d);
                }
            }
        }
        return null; 

    }

    public Piece checkCol()
    {
        for(int c=0; c<board[0].length; c++)
        {
            for(int r=0; r<board.length - 3; r++)
            {
                Piece a = board[r][c]; 
                Piece b = board[r+1][c]; 
                Piece s = board[r+2][c]; 
                Piece d = board[r+3][c]; 

                Piece check = connect4(a,b,s,d); 
                if(check!=null)
                {
                    return connect4(a,b,s,d);
                }
            }
        }
        return null; 
    }

    public Piece connect4(Piece a, Piece b, Piece c, Piece d)
    {
        if(a==b && a==c && a==d)
        {
            return a; 
        }
        return null; 
    }
    
    public void printAll()
    {
        for(int r=0; r<board.length; r++)
        {
            for(int c=0; c<board[0].length; c++)
            {
                System.out.print(board[r][c] + " "); 
            }
            System.out.println(); 
        }
        System.out.println(); 
    }
}
