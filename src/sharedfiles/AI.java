
package sharedfiles;
import java.awt.*;
public class AI {
	public Piece [][] aiarr=new Piece[8][8];
	public Board gameBoard;
	public char color;//uppercase W or B
	private Piece [] threatened=new Piece[400];
	private Point [] locThreatened=new Point[400];
	private int numThreatened=0;
	private Piece [] threatening=new Piece[400];
	private Point [] locThreatening=new Point[400];
	private int numThreatening=0;
	Point loc1;// piece threatened //threateningscore
	Point loc2;// piece threatening
	int numTurns=0;
	public AI(){
		
	}
	public char setColor(char c){
		color=c;//black is false
		return c;
	}
	public Board takeTurn(Board b){
		
			
				this.readBoard(b);
				this.checkThreats(b, color);
				this.checkMoves();
				b.setBoardArray(this.aiarr);
				return b;
			
			
		}
		/*if(color=='W')
		{
			switch(numTurns){
			case 0: this.makeMove(new Point(5,6),new Point(5,4) );numTurns++;testBoard.setBoardArray(aiarr);return testBoard;
			case 1: this.makeMove(new Point(6,6), new Point(6,5));numTurns++;testBoard.setBoardArray(aiarr);return testBoard;
			case 2: this.makeMove(new Point(6,7), new Point(5,5));numTurns++;testBoard.setBoardArray(aiarr);return testBoard;
			case 3: this.makeMove(new Point(5,7), new Point(6,6));numTurns++;testBoard.setBoardArray(aiarr);return testBoard;
			case 4: aiarr[6][7]=aiarr[4][7]; aiarr[4][7]=new Blank(true); aiarr[5][7]=aiarr[7][7]; aiarr[7][7]=new Blank(true);testBoard.setBoardArray(aiarr);numTurns++;return testBoard;
			default:
				for(int i=0;i<10;i++){
					readBoard(testBoard);
					checkThreats(testBoard, color);
					checkMoves();
					testBoard.setBoardArray(this.aiarr);
					testBoard.printBoard();
					System.out.println();
					}
				return testBoard;
			
		}
		}//w
		if(color=='B'){
			switch(numTurns){
			case 0: this.makeMove(new Point(5,1),new Point(5,3) );numTurns++;testBoard.setBoardArray(aiarr);return testBoard;
			case 1: this.makeMove(new Point(6,1), new Point(6,2));numTurns++;testBoard.setBoardArray(aiarr);return testBoard;
			case 2: this.makeMove(new Point(6,0), new Point(5,2));numTurns++;testBoard.setBoardArray(aiarr);return testBoard;
			case 3: this.makeMove(new Point(5,0), new Point(6,1));numTurns++;testBoard.setBoardArray(aiarr);return testBoard;
			case 4: aiarr[6][0]=aiarr[4][0]; aiarr[4][0]=new Blank(true); aiarr[5][0]=aiarr[7][0]; aiarr[7][0]=new Blank(true);testBoard.setBoardArray(aiarr);numTurns++;return testBoard;
			default:
				for(int i=0;i<15;i++){
					readBoard(testBoard);
					checkThreats(testBoard, color);
					checkMoves();
					testBoard.setBoardArray(this.aiarr);
					testBoard.printBoard();
					System.out.println();
					}
				return testBoard;
			}
		}
			return null;*/
	
	public void readBoard(Board b)
		{
			gameBoard=b;
		}
	public boolean isThreatened(Point p){
		char c=aiarr[(int)p.getX()][(int)p.getY()].toString().charAt(0);
		checkThreats(gameBoard,c);
		for(int i=0;i<numThreatened;i++){
			if(locThreatened[i].getX()==p.getX() &&locThreatened[i].getY()==p.getY()){
				return true;
			}
		}
		return false;
		
	}
	public boolean doesThreaten(Point p){
		char c=aiarr[(int)p.getX()][(int)p.getY()].toString().charAt(0);
		if(c=='B')c='W';
		else c='B';
		checkThreats(gameBoard,c);
		for(int i=0;i<numThreatening;i++){
			if(locThreatening[i].getX()==p.getX() &&locThreatening[i].getY()==p.getY()){
				return true;
			}
		}
		return false;
	}
	public void makeMove(Point loc1, Point loc2){//TODO change instances of make move
		int x1=(int)loc1.getX();
		int y1=(int)loc1.getY();
		int x2=(int)loc2.getX();
		int y2=(int)loc2.getY();
		Piece Piece1=aiarr[x1][y1];//piece to move
		Piece Piece2=aiarr[x2][y2];
			aiarr[x2][y2]=Piece1;
			aiarr[x1][y1]=new Blank(true);
		
		
		
	}
	public int moveScore(Point a, Point b){
		return 1;
	}
	public void ThreateningScore(Board b){
		char c;
		if(color=='W')c='B';
		else c='W';
		int score=0;
		
		int best=-10;
		System.out.println(c);
		checkThreats(b,c);
		printThreats();
		for(int i=0;i<numThreatened;i++){
			score=toInt(threatened[i]);
			if(isThreatened(locThreatening[i])){
				score+=toInt(threatening[i]);
			}
			System.out.println(score);
			makeMove(locThreatened[i],locThreatening[i]);
			if(isThreatened(locThreatened[i])){
				score-=toInt(threatening[i]);
			}
			makeMove(locThreatened[i],locThreatening[i]);
			if(score>best){
				best=score;
				loc1=locThreatened[i];
				loc2=locThreatening[i];
			}
		}
	}
	public void checkMoves(){
		char c;
		if(color=='W'){
			c='W';
		}
		else c='B';
		//System.out.println(c);
		int best=-1;
		Point p1=new Point(0,0);//piece to move
		Point p2=new Point(0,0);//location to move to
			for(int x=0;x<8;x++){
				for(int y=0;y<8;y++){
				
					if(aiarr[x][y].toString().charAt(0)==c && aiarr[x][y].toString().charAt(1)!='X'){
						Point [] moves=canMove(new Point(x,y));
						for(int i=0;i<moves.length;i++){
							//System.out.println(x+" "+y);
							//System.out.println(i);
							//System.out.println(moves[i]);
							int score=0;
							if(moves[i]!=null){
								if(isThreatened(new Point(x,y))){
									score+=toInt(aiarr[x][y]);
								}
								if(aiarr[(int)moves[i].getX()][(int)moves[i].getY()].toString().charAt(1)!='X'){
									score+=toInt(aiarr[(int)moves[i].getX()][(int)moves[i].getY()]);
								}
								Piece [][] arr0=new Piece[8][8];
								for(int x1=0;x1<8;x1++){
									for(int y1=0;y1<8;y1++){
										arr0[x1][y1]=aiarr[x1][y1];
									}
								}
								Piece [][] a1=aiarr;
								aiarr=arr0;
								makeMove(new Point(x,y),moves[i]);
								if(isThreatened(moves[i])){
									score-=toInt(aiarr[(int)moves[i].getX()][(int)moves[i].getY()]);
								}
								aiarr=a1;//FIX
								if(score>best){
									best=score;
									p1=new Point(x,y);
									p2=new Point(moves[i]);
								}
								
							}
						}
					}
				}//for
			
			}
			makeMove(p1,p2);
		}
	
	/*public void checkMoves(Board b){
		checkThreats(b,color);
		int Level1=0;//level of piece threatened
		int Level2=0;//level of piece threatening
		Piece a1=new Blank(true);//best piece threatened
		Point loca1=new Point(0,0);//loc a1
		Piece a2=new Blank(true);
		Point loca2=new Point(0,0);//loc a2
		Piece b1=new Blank(true);//best piece threatening
		Point locb1=new Point(0,0);
		Piece b2=new Blank(true);
		Point locb2=new Point(0,0);
		Piece c1;
		Point locc1=new Point(0,0);//best piece threatened by a1
		int Levelc=0;
		for(int i=0;i<numThreatened;i++){
			if(toInt(threatened[i])>Level1){
				a1=threatened[i];
				loca1=locThreatened[i];
				a2=threatening[i];
				loca2=locThreatening[i];
				Level1=toInt(threatened[i]);
			}
		}
		if(color=='W'){
			checkThreats(b,'B');
		}
		else checkThreats(b,'W');
		for(int i=0;i<numThreatened;i++){
			if(toInt(threatened[i])>Level2){
				b1=threatened[i];
				locb1=locThreatened[i];
				b2=threatening[i];
				locb2=locThreatening[i];
				Level2=toInt(threatened[i]);
			}
		}
			if(Level2>=Level1){
				makeMove(locb1,locb2);
				if(isThreatened(locb1)){//piece after taking b1
					if(toInt(b2)>toInt(b1)){
						makeMove(locb1,locb2);
						//TODO decide move to make
					}
					else{}
				}
			}
			if(Level1>Level2){
				if(doesThreaten(loca1)){
					for(int i=0;i<numThreatening;i++){
						if(locThreatening[i].getX()==loca1.getX() && locThreatening[i].getY()==loca1.getY()){
							if(toInt(threatened[i])>Levelc){
								Levelc=toInt(threatened[i]);
								locc1=locThreatened[i];
							}
						}
					}
					makeMove(locc1,loca1);
					if(isThreatened(locc1)){
						if(toInt(a))
					}
				}
			}
		}
		
/*public void compareThreats(){
	for(int i=0;i<numThreatened;i++){
if(threatened[i].toString().charAt(1)=='N')
			{
				if(toInt(threatening[i])>1 && toInt(threatening[i])<6)
				{
					if(locThreatened[i].getX() == locThreatening[i].getX()-1 && locThreatened[i].getY() == locThreatening[i].getY()-1 || locThreatened[i].getX()-1==locThreatening[i].getX() && locThreatened[i].getY()+1==locThreatening[i].getY())
					{
						Piece[][] b= new Piece[8][8];
						for(int j=0;j<8;j++)
						{
							for(int k=0;k<8;k++)
							{
								b[j][k]=aiarr[j][k];
							}
						}
						b[(int)locThreatening[i].getX()][(int)locThreatening[i].getY()]= threatened[i];
						b[(int)locThreatened[i].getX()][(int)locThreatened[i].getY()]= new Blank(true);
						Board c= new Board(b);
						Move[q]=c;
						q++;
				       
						int $=(int)locThreatening[i].getX();
						int $$=locThreatening[i].getY();
						this.checkThreats(b);
						for(int p=0;p<400;p++)
						{
							if(locThreatened[p].getX=$ && locThreatened[p].getY()==$$)
							{
							this.compareThreats();
							}
							else
							{
							
							}
						}		
						
					}
				}
			}
}
}*/
	public Point[] canMove(Point a){
		int x=(int)a.getX();
		int y=(int)a.getY();
		//System.out.println(x);
		//System.out.println(y);
		//System.out.println(aiarr[x][y].toString().charAt(1));
		int q=toInt(aiarr[x][y]);
		//System.out.println(q);
		switch(q){
		case 0://pawn
			if(aiarr[x][y].toString().charAt(0)=='W'){//color of piece
				Point[] moves=new Point[4];
				if((x+1<8 && y-1>=0) &&aiarr[x+1][y-1].toString().charAt(0)=='B'){
				moves[0]=new Point(x+1,y-1);
				}
				if(x-1>=0 && y-1>=0 && aiarr[x-1][y-1].toString().charAt(0)=='B'){
				moves[1]=new Point(x-1,y-1);
				}
				if(y==6 && aiarr[x][y-2].toString().charAt(1)=='X' && aiarr[x][y-1].toString().charAt(1)=='X'){
				moves[2]=new Point(x,y-2);
				}
				if(y-1>=0 && aiarr[x][y-1].toString().charAt(1)=='X'){
					moves[3]=new Point(x,y-1);
				}
				
				return moves;
			}
			if(aiarr[x][y].toString().charAt(0)=='B'){
				Point[] moves=new Point[4];
				if((x+1<8 && y+1<8) &&aiarr[x+1][y+1].toString().charAt(0)=='W' &&aiarr[x+1][y+1].toString().charAt(1)!='X'){
				moves[0]=new Point(x+1,y+1);
				}
				if(x-1>=0 && y+1<8 && aiarr[x-1][y+1].toString().charAt(0)=='W' &&aiarr[x-1][y+1].toString().charAt(1)!='X'){
				moves[1]=new Point(x-1,y+1);
				}
				if(y==1 && aiarr[x][y+2].toString().charAt(1)=='X' && aiarr[x][y+1].toString().charAt(1)=='X'){
				moves[2]=new Point(x,y+2);
				}
				if(y+1<8 && aiarr[x][y+1].toString().charAt(1)=='X'){
					moves[3]=new Point(x,y+1);
				}				
				return moves;
			}
			break;
		case 1:
			if(aiarr[x][y].toString().charAt(0)=='W'){
				Point[] moves=new Point[8];
				if(x+1<8 &&y+2<8 &&( aiarr[x+1][y+2].toString().charAt(0)=='B' || aiarr[x+1][y+2].toString().charAt(1)=='X')){
				moves[0]=new Point(x+1,y+2);
				}
				if(x-1>=0 &&y+2<8 &&( aiarr[x-1][y+2].toString().charAt(0)=='B' || aiarr[x-1][y+2].toString().charAt(1)=='X')){
				moves[1]=new Point(x-1,y+2);
				}
				if(x+1<8 &&y-2>=0 &&( aiarr[x+1][y-2].toString().charAt(0)=='B' || aiarr[x+1][y-2].toString().charAt(1)=='X')){
				moves[2]=new Point(x+1,y-2);
				}
				if(x-1>=0 &&y-2>=0 &&( aiarr[x-1][y-2].toString().charAt(0)=='B' || aiarr[x-1][y-2].toString().charAt(1)=='X')){
					moves[3]=new Point(x-1,y-2);
				}
				if(x+2<8 &&y+1<8 &&( aiarr[x+2][y+1].toString().charAt(0)=='B' || aiarr[x+2][y+1].toString().charAt(1)=='X')){
					moves[4]=new Point(x+2,y+1);
				}
				if(x+2<8 &&y-1>=0 &&( aiarr[x+2][y-1].toString().charAt(0)=='B' || aiarr[x+2][y-1].toString().charAt(1)=='X')){
					moves[5]=new Point(x+2,y-1);
				}
				if(x-2>=0 &&y+1<8 &&( aiarr[x-2][y+1].toString().charAt(0)=='B' || aiarr[x-2][y+1].toString().charAt(1)=='X')){
					moves[6]=new Point(x-2,y+1);
				}
				if(x-2>=0 &&y-1>=0 &&( aiarr[x-2][y-1].toString().charAt(0)=='B' || aiarr[x-2][y-1].toString().charAt(1)=='X')){
					moves[7]=new Point(x-2,y-1);
				}
				return moves;
		}
			if(aiarr[x][y].toString().charAt(0)=='B'){
				Point[] moves=new Point[8];
				if(x+1<8 &&y+2<8 &&( aiarr[x+1][y+2].toString().charAt(0)=='W' || aiarr[x+1][y+2].toString().charAt(1)=='X')){
				moves[0]=new Point(x+1,y+2);
				}
				if(x-1>=0 &&y+2<8 &&( aiarr[x-1][y+2].toString().charAt(0)=='W' || aiarr[x-1][y+2].toString().charAt(1)=='X')){
				moves[1]=new Point(x-1,y+2);
				}
				if(x+1<8 &&y-2>=0 &&( aiarr[x+1][y-2].toString().charAt(0)=='W' || aiarr[x+1][y-2].toString().charAt(1)=='X')){
				moves[2]=new Point(x+1,y-2);
				}
				if(x-1>=0 &&y-2>=0 &&( aiarr[x-1][y-2].toString().charAt(0)=='W' || aiarr[x-1][y-2].toString().charAt(1)=='X')){
				moves[3]=new Point(x-1,y-2);
				}
				if(x+2<8 &&y+1<8 &&( aiarr[x+2][y+1].toString().charAt(0)=='W' || aiarr[x+2][y+1].toString().charAt(1)=='X')){
					moves[4]=new Point(x+2,y+1);
				}
				if(x+2<8 &&y-1>=0 &&( aiarr[x+2][y-1].toString().charAt(0)=='W' || aiarr[x+2][y-1].toString().charAt(1)=='X')){
					moves[5]=new Point(x+2,y-1);
				}
				if(x-2>=0 &&y+1<8 &&( aiarr[x-2][y+1].toString().charAt(0)=='W' || aiarr[x-2][y+1].toString().charAt(1)=='X')){
					moves[6]=new Point(x-2,y+1);
				}
				if(x-2>=0 &&y-1>=0 &&( aiarr[x-2][y-1].toString().charAt(0)=='W' || aiarr[x-2][y-1].toString().charAt(1)=='X')){
					moves[7]=new Point(x-2,y-1);
				}
				return moves;
			}
			break;
		case 2:
			if(aiarr[x][y].toString().charAt(0)=='W'){//color of piece
				Point[] moves=new Point[100];
				int numBishop=0;
				for(int i=x+1;i<8;i++){//Diagonal //ToDo-check if x+1 is out of bounds
					int l=i;
					if(y+l-x<8 && y+l-x>=0){
					if(aiarr[l][y+l-x].toString().charAt(0)=='B'){
						moves[numBishop]=new Point(l,y+l-x);
						numBishop++;
						i=8;
					}
					else if(aiarr[l][y+l-x].toString().charAt(0)=='W' && aiarr[l][y+l-x].toString().charAt(1)!='X'){
						i=8;
					}
					if(aiarr[l][y+l-x].toString().charAt(1)=='X'){
						moves[numBishop]=new Point(l,y+l-x);
						numBishop++;
					}
					}
				}
			for(int i=x+1;i<8;i++){//Diagonal //ToDo-check if x+1 is out of bounds
					int l=i;
					if(y-l+x<8 && y-l+x>=0){
					if(aiarr[l][y-l+x].toString().charAt(0)=='B'){
						moves[numBishop]=new Point(l,y-l+x);
						numBishop++;
						i=8;
					}
					if(aiarr[l][y-l+x].toString().charAt(0)=='W' && aiarr[l][y-l+x].toString().charAt(1)!='X'){
						i=8;
					}
					if(aiarr[l][y-l+x].toString().charAt(1)=='X'){
						moves[numBishop]=new Point(l,y-l+x);
						numBishop++;
					}
					}
				}
				for(int i=x-1;i>=0;i--){//Diagonal //ToDo-check if x+1 is out of bounds
					int l=i;
					if(y+l-x<8 && y+l-x>=0){
					if(aiarr[l][y+l-x].toString().charAt(0)=='B'){
						moves[numBishop]=new Point(l,y+l-x);
						numBishop++;
						i=-1;
					}
					if(aiarr[l][y+l-x].toString().charAt(0)=='W' && aiarr[l][y+l-x].toString().charAt(1)!='X'){
						i=-1;
					}
					if(aiarr[l][y+l-x].toString().charAt(1)=='X'){
						moves[numBishop]=new Point(l,y+l-x);
						numBishop++;
					}
					}
				}
				for(int i=x-1;i>=0;i--){//Diagonal //ToDo-check if x+1 is out of bounds
					int l=i;
					if(y-l+x<8 && y-l+x>=0){
					if(aiarr[l][y-l+x].toString().charAt(0)=='B'){
						moves[numBishop]=new Point(l,y-l+x);
						numBishop++;
						i=-1;
					}
					if(aiarr[l][y-l+x].toString().charAt(0)=='W' && aiarr[l][y-l+x].toString().charAt(1)!='X'){
						i=-1;
					}
					if(aiarr[l][y-l+x].toString().charAt(1)=='X'){
						moves[numBishop]=new Point(l,y-l+x);
						numBishop++;
					}
					}
				}
				return moves;
				}//color
			if(aiarr[x][y].toString().charAt(0)=='B'){
				Point[] moves=new Point[100];
				int numBishop=0;
				for(int i=x+1;i<8;i++){//Diagonal //ToDo-check if x+1 is out of bounds
					int l=i;
					if(y+l-x<8 && y+l-x>=0){
					if(aiarr[l][y+l-x].toString().charAt(0)=='W' && aiarr[l][y+l-x].toString().charAt(1)!='X'){
						moves[numBishop]=new Point(l,y+l-x);
						numBishop++;
						i=8;
					}
					if(aiarr[l][y+l-x].toString().charAt(0)=='B'){
						i=8;
					}
					if(aiarr[l][y+l-x].toString().charAt(1)=='X'){
						moves[numBishop]=new Point(l,y+l-x);
						numBishop++;
					}
					}
				}
				for(int i=x+1;i<8;i++){//Diagonal //ToDo-check if x+1 is out of bounds
					int l=i;
					if(y-l+x<8 && y-l+x>=0){
					if(aiarr[l][y-l+x].toString().charAt(0)=='W' && aiarr[l][y-l+x].toString().charAt(1)!='X'){
						moves[numBishop]=new Point(l,y-l+x);
						numBishop++;
						i=8;
					}
					if(aiarr[l][y-l+x].toString().charAt(0)=='B'){
						i=8;
					}
					if(aiarr[l][y-l+x].toString().charAt(1)=='X'){
						moves[numBishop]=new Point(l,y-l+x);
						numBishop++;
					}
					}
				}
				for(int i=x-1;i>=0;i--){//Diagonal //ToDo-check if x+1 is out of bounds
					int l=i;
					if(y+l-x<8 && y+l-x>=0){
					if(aiarr[l][y+l-x].toString().charAt(0)=='W' && aiarr[l][y+l-x].toString().charAt(1)!='X'){
						moves[numBishop]=new Point(l,y+l-x);
						numBishop++;
						i=-1;
					}
					if(aiarr[l][y+l-x].toString().charAt(0)=='B'){
						i=-1;
					}
					if(aiarr[l][y+l-x].toString().charAt(1)=='X'){
						moves[numBishop]=new Point(l,y+l-x);
						numBishop++;
					}
					}
				}
				for(int i=x-1;i>=0;i--){//Diagonal //ToDo-check if x+1 is out of bounds
					int l=i;
					if(y-l+x<8 && y-l+x>=0){
					if(aiarr[l][y-l+x].toString().charAt(0)=='W' && aiarr[l][y-l+x].toString().charAt(1)!='X'){
					
						moves[numBishop]=new Point(l,y-l+x);
						numBishop++;
						i=-1;
					}
					if(aiarr[l][y-l+x].toString().charAt(0)=='B'){
						i=-1;
					}
					if(aiarr[l][y-l+x].toString().charAt(1)=='X'){
				
						moves[numBishop]=new Point(l,y-l+x);
						numBishop++;
					}
					}
				}
				return moves;
				}//color
			break;
		case 3:
				if(aiarr[x][y].toString().charAt(0)=='W'){//color of piece
					int numRook=0;
					Point[] moves=new Point[20];
					for(int i=x+1;i<8;i++){
						//System.out.println(i);
						int l=i;
						//System.out.print(l+" ");
						//System.out.println(y);
						//System.out.println(aiarr[l][y].toString());
						 if(aiarr[l][y].toString().charAt(0)=='B'){
							 moves[numRook]=new Point(l,y);
							 numRook++;
							 i=8;
						 }
						 else if(aiarr[l][y].toString().charAt(0)=='W' && aiarr[l][y].toString().charAt(1)!='X'){
							 i=8;
						 }
						 if(aiarr[l][y].toString().charAt(1)=='X'){
							 moves[numRook]=new Point(l,y);
							 numRook++;
						 }
					}//for
					for(int i=x-1;i>=0;i--){
						int l=i;
						 if(aiarr[l][y].toString().charAt(0)=='B'){
							 moves[numRook]=new Point(l,y);
							 numRook++;
							 i=-1;
						 }
						 else if(aiarr[l][y].toString().charAt(0)=='W' && aiarr[l][y].toString().charAt(1)!='X'){
							 i=-1;
						 }
						 if(aiarr[l][y].toString().charAt(1)=='X'){
							 moves[numRook]=new Point(l,y);
							 numRook++;
						 }
					}//for 		   
					for(int i=y+1;i<8;i++){
						int l=i;
						 if(aiarr[x][l].toString().charAt(0)=='B'){
							 moves[numRook]=new Point(x,l);
							 numRook++;
							 i=8;
						 }
						 else if(aiarr[x][l].toString().charAt(0)=='W' && aiarr[x][l].toString().charAt(1)!='X'){
							 i=8;
						 }
						 if(aiarr[x][l].toString().charAt(1)=='X'){
							 moves[numRook]=new Point(x,l);
							 numRook++;
						 }
					}//for   
					for(int i=y-1;i>=0;i--){
						int l=i;
						//System.out.println(x+" "+l);
						 if(aiarr[x][l].toString().charAt(0)=='B'){
							 moves[numRook]=new Point(x,l);
							 numRook++;
							 i=-1;
						 }
						 else if(aiarr[x][l].toString().charAt(0)=='W' && aiarr[x][l].toString().charAt(1)!='X'){
							 i=-1;
						 }
						 if(aiarr[x][l].toString().charAt(1)=='X'){
							 moves[numRook]=new Point(x,l);
							 numRook++;
						 }
				
					}
					return moves;
				}
				if(aiarr[x][y].toString().charAt(0)=='B'){//color of piece
					int numRook=0;
					Point[] moves=new Point[100];
					for(int i=x+1;i<8;i++){
						int l=i;
						 if(aiarr[l][y].toString().charAt(0)=='W' && aiarr[l][y].toString().charAt(1)!='X'){
							
							 moves[numRook]=new Point(l,y);
							 numRook++;
							 i=8;
						 }
						 else if(aiarr[l][y].toString().charAt(0)=='B'){
							 i=8;
						 }
						 if(aiarr[l][y].toString().charAt(1)=='X'){
							 moves[numRook]=new Point(l,y);
							 numRook++;
						 }
					}//for
					for(int i=x-1;i>=0;i--){
						int l=i;
						 if(aiarr[l][y].toString().charAt(0)=='W' && aiarr[l][y].toString().charAt(1)!='X'){
							 moves[numRook]=new Point(l,y);
							 numRook++;
							 i=-1;
						 }
						 else if(aiarr[l][y].toString().charAt(0)=='B'){
							 i=-1;
						 }
						 if(aiarr[l][y].toString().charAt(1)=='X'){
							 moves[numRook]=new Point(l,y);
							 numRook++;
						 }
					}//for
					for(int i=y+1;i<8;i++){
						int l=i;
						 if(aiarr[x][l].toString().charAt(0)=='W' && aiarr[x][l].toString().charAt(1)!='X'){
							 moves[numRook]=new Point(x,l);
							 numRook++;
							 i=8;
						 }
						 else if(aiarr[x][l].toString().charAt(0)=='B'){
							 i=8;
						 }
						 if(aiarr[x][l].toString().charAt(1)=='X'){
							 moves[numRook]=new Point(x,l);
							 numRook++;
						 }
					}//for
					for(int i=y-1;i>=0;i--){
						int l=i;
						if(aiarr[x][l].toString().charAt(0)=='W' && aiarr[x][l].toString().charAt(1)!='X'){
							 moves[numRook]=new Point(x,l);
							 numRook++;
							 i=-1;
						 }
						 else if(aiarr[x][l].toString().charAt(0)=='B'){
							 i=-1;
						 }
						 if(aiarr[x][l].toString().charAt(1)=='X'){
							 moves[numRook]=new Point(x,l);
							 numRook++;
						 }
				
					}
						return moves;
				}
				break;
			case 4:
				if(aiarr[x][y].toString().charAt(0)=='W'){//color of piece
					int numQueen=0;
					Point[] moves=new Point[100];
					for(int i=x+1;i<8;i++){
				
						int l=i;
						 if(aiarr[l][y].toString().charAt(0)=='B'){
							
							 moves[numQueen]=new Point(l,y);
							 numQueen++;
							 i=8;
						 }
						 else if(aiarr[l][y].toString().charAt(0)=='W' && aiarr[l][y].toString().charAt(1)!='X'){
							 i=8;
						 }
						 if(aiarr[l][y].toString().charAt(1)=='X'){
							 moves[numQueen]=new Point(l,y);
							 numQueen++;
						 }
					}//for
					for(int i=x-1;i>=0;i--){
						int l=i;
						 if(aiarr[l][y].toString().charAt(0)=='B'){
							 moves[numQueen]=new Point(l,y);
							 numQueen++;
							 i=-1;
						 }
						 else if(aiarr[l][y].toString().charAt(0)=='W' && aiarr[l][y].toString().charAt(1)!='X'){
							 i=-1;
						 }
						 if(aiarr[l][y].toString().charAt(1)=='X'){
							 moves[numQueen]=new Point(l,y);
							 numQueen++;
						 }
					}//for
					for(int i=y+1;i<8;i++){
						int l=i;
						 if(aiarr[x][l].toString().charAt(0)=='B'){
							 moves[numQueen]=new Point(x,l);
							 numQueen++;
							 i=8;
						 }
						 else if(aiarr[x][l].toString().charAt(0)=='W' && aiarr[x][l].toString().charAt(1)!='X'){
							 i=8;
						 }
						 if(aiarr[x][l].toString().charAt(1)=='X'){
							 moves[numQueen]=new Point(x,l);
							 numQueen++;
						 }
					}//for
					for(int i=y-1;i>=0;i--){
						int l=i;
						 if(aiarr[x][l].toString().charAt(0)=='B'){
							 moves[numQueen]=new Point(x,l);
							 numQueen++;
							 i=-1;
						 }
						 else if(aiarr[x][l].toString().charAt(0)=='W' && aiarr[x][l].toString().charAt(1)!='X'){
							 i=-1;
						 }
						 if(aiarr[x][l].toString().charAt(1)=='X'){
							 moves[numQueen]=new Point(x,l);
							 numQueen++;
						 }
					}//for
					for(int i=x+1;i<8;i++){//Diagonal //ToDo-check if x+1 is out of bounds
						int l=i;
						if(y+l-x<8 && y+l-x>=0){
						if(aiarr[l][y+l-x].toString().charAt(0)=='B'){
							moves[numQueen]=new Point(l,y+l-x);
							numQueen++;
							i=8;
						}
						else if(aiarr[l][y+l-x].toString().charAt(0)=='W' && aiarr[l][y+l-x].toString().charAt(1)!='X'){
							i=8;
						}
						if(aiarr[l][y+l-x].toString().charAt(1)=='X'){
							moves[numQueen]=new Point(l,y+l-x);
							numQueen++;
						}
						}
					}
				for(int i=x+1;i<8;i++){//Diagonal //ToDo-check if x+1 is out of bounds
						int l=i;
						if(y-l+x<8 && y-l+x>=0){
						if(aiarr[l][y-l+x].toString().charAt(0)=='B'){
							moves[numQueen]=new Point(l,y-l+x);
							numQueen++;
							i=8;
						}
						if(aiarr[l][y-l+x].toString().charAt(0)=='W' && aiarr[l][y-l+x].toString().charAt(1)!='X'){
							i=8;
						}
						if(aiarr[l][y-l+x].toString().charAt(1)=='X'){
							moves[numQueen]=new Point(l,y-l+x);
							numQueen++;
						}
						}
					}
					for(int i=x-1;i>=0;i--){//Diagonal //ToDo-check if x+1 is out of bounds
						int l=i;
						if(y+l-x<8 && y+l-x>=0){
						if(aiarr[l][y+l-x].toString().charAt(0)=='B'){
							moves[numQueen]=new Point(l,y+l-x);
							numQueen++;
							i=-1;
						}
						if(aiarr[l][y+l-x].toString().charAt(0)=='W' && aiarr[l][y+l-x].toString().charAt(1)!='X'){
							i=-1;
						}
						if(aiarr[l][y+l-x].toString().charAt(1)=='X'){
							moves[numQueen]=new Point(l,y+l-x);
							numQueen++;
						}
						}
					}
					for(int i=x-1;i>=0;i--){//Diagonal //ToDo-check if x+1 is out of bounds
						int l=i;
						if(y-l+x<8 && y-l+x>=0){
						if(aiarr[l][y-l+x].toString().charAt(0)=='B'){
							moves[numQueen]=new Point(l,y-l+x);
							numQueen++;
							i=-1;
						}
						if(aiarr[l][y-l+x].toString().charAt(0)=='W' && aiarr[l][y-l+x].toString().charAt(1)!='X'){
							i=-1;
						}
						if(aiarr[l][y-l+x].toString().charAt(1)=='X'){
							moves[numQueen]=new Point(l,y-l+x);
							numQueen++;
						}
						}
					}
					return moves;
				}//color

				if(aiarr[x][y].toString().charAt(0)=='B'){//color of piece
					int numQueen=0;
					Point[] moves=new Point[100];
					for(int i=x+1;i<8;i++){
					
						int l=i;
						if(aiarr[l][y].toString().charAt(0)=='W' && aiarr[l][y].toString().charAt(1)!='X'){
							
							 moves[numQueen]=new Point(l,y);
							 numQueen++;
							 i=8;
						 }
						 else if(aiarr[l][y].toString().charAt(0)=='B'){
							 i=8;
						 }
						 if(aiarr[l][y].toString().charAt(1)=='X'){
							 moves[numQueen]=new Point(l,y);
							 numQueen++;
						 }
					}//for
					for(int i=x-1;i>=0;i--){
						int l=i;
						if(aiarr[l][y].toString().charAt(0)=='W' && aiarr[l][y].toString().charAt(1)!='X'){
							 moves[numQueen]=new Point(l,y);
							 numQueen++;
							 i=-1;
						 }
						 else if(aiarr[l][y].toString().charAt(0)=='B'){
							 i=-1;
						 }
						 if(aiarr[l][y].toString().charAt(1)=='X'){
							 moves[numQueen]=new Point(l,y);
							 numQueen++;
						 }
					}//for
					for(int i=y+1;i<8;i++){
						int l=i;
						if(aiarr[x][l].toString().charAt(0)=='W' && aiarr[x][l].toString().charAt(1)!='X'){
							 moves[numQueen]=new Point(x,l);
							 numQueen++;
							 i=8;
						 }
						 else if(aiarr[x][l].toString().charAt(0)=='B'){
							 i=8;
						 }
						 if(aiarr[x][l].toString().charAt(1)=='X'){
							 moves[numQueen]=new Point(x,l);
							 numQueen++;
						 }
					}//for
					for(int i=y-1;i>=0;i--){
						int l=i;
						if(aiarr[x][l].toString().charAt(0)=='W' && aiarr[x][l].toString().charAt(1)!='X'){
							 moves[numQueen]=new Point(x,l);
							 numQueen++;
							 i=-1;
						 }
						 else if(aiarr[x][l].toString().charAt(0)=='B'){
							 i=-1;
						 }
						 if(aiarr[x][l].toString().charAt(1)=='X'){
							 moves[numQueen]=new Point(x,l);
							 numQueen++;
						 }
					}//for
					for(int i=x+1;i<8;i++){//Diagonal //ToDo-check if x+1 is out of bounds
						int l=i;
						if(y+l-x<8 && y+l-x>=0){
						if(aiarr[l][y+l-x].toString().charAt(0)=='W' && aiarr[l][y+l-x].toString().charAt(1)!='X'){
							moves[numQueen]=new Point(l,y+l-x);
							numQueen++;
							i=8;
						}
						if(aiarr[l][y+l-x].toString().charAt(0)=='B'){
							i=8;
						}
						if(aiarr[l][y+l-x].toString().charAt(1)=='X'){
							moves[numQueen]=new Point(l,y+l-x);
							numQueen++;
						}
						}
					}
					for(int i=x+1;i<8;i++){//Diagonal //ToDo-check if x+1 is out of bounds
						int l=i;
						if(y-l+x<8 && y-l+x>=0){
						if(aiarr[l][y-l+x].toString().charAt(0)=='W' && aiarr[l][y-l+x].toString().charAt(1)!='X'){
							moves[numQueen]=new Point(l,y-l+x);
							numQueen++;
							i=8;
						}
						if(aiarr[l][y-l+x].toString().charAt(0)=='B'){
							i=8;
						}
						if(aiarr[l][y-l+x].toString().charAt(1)=='X'){
							moves[numQueen]=new Point(l,y-l+x);
							numQueen++;
						}
						}
					}
					for(int i=x-1;i>=0;i--){//Diagonal //ToDo-check if x+1 is out of bounds
						int l=i;
						if(y+l-x<8 && y+l-x>=0){
						if(aiarr[l][y+l-x].toString().charAt(0)=='W' && aiarr[l][y+l-x].toString().charAt(1)!='X'){
							moves[numQueen]=new Point(l,y+l-x);
							numQueen++;
							i=-1;
						}
						if(aiarr[l][y+l-x].toString().charAt(0)=='B'){
							i=-1;
						}
						if(aiarr[l][y+l-x].toString().charAt(1)=='X'){
							moves[numQueen]=new Point(l,y+l-x);
							numQueen++;
						}
						}
					}
					for(int i=x-1;i>=0;i--){//Diagonal //ToDo-check if x+1 is out of bounds
						int l=i;
						if(y-l+x<8 && y-l+x>=0){
						if(aiarr[l][y-l+x].toString().charAt(0)=='W' && aiarr[l][y-l+x].toString().charAt(1)!='X'){
						
							moves[numQueen]=new Point(l,y-l+x);
							numQueen++;
							i=-1;
						}
						if(aiarr[l][y-l+x].toString().charAt(0)=='B'){
							i=-1;
						}
						if(aiarr[l][y-l+x].toString().charAt(1)=='X'){
					
							moves[numQueen]=new Point(l,y-l+x);
							numQueen++;
						}
						}
					}
					return moves;
				}//color
				break;
			case 5: 
				if(aiarr[x][y].toString().charAt(0)=='W'){
					Point[] moves=new Point[8];
					if(x+1<8 &&y+1<8 &&( aiarr[x+1][y+1].toString().charAt(0)=='B' || aiarr[x+1][y+1].toString().charAt(1)=='X')){
					moves[0]=new Point(x+1,y+1);
					}
					if(x>=0 &&y+1<8 &&( aiarr[x][y+1].toString().charAt(0)=='B' || aiarr[x][y+1].toString().charAt(1)=='X')){
					moves[1]=new Point(x,y+1);
					}
					if(x-1>=0 &&y+1<8 &&( aiarr[x-1][y+1].toString().charAt(0)=='B' || aiarr[x-1][y+1].toString().charAt(1)=='X')){
					moves[2]=new Point(x,y+1);
					}
					if(x+1<8 &&y>=0 &&( aiarr[x+1][y].toString().charAt(0)=='B' || aiarr[x+1][y].toString().charAt(1)=='X')){
						moves[3]=new Point(x+1,y);
					}
					if(x-1>=0 &&y<8 &&( aiarr[x-1][y].toString().charAt(0)=='B' || aiarr[x-1][y].toString().charAt(1)=='X')){
						moves[4]=new Point(x-1,y);
					}
					if(x+1<8 &&y-1>=0 &&( aiarr[x+1][y-1].toString().charAt(0)=='B' || aiarr[x+1][y-1].toString().charAt(1)=='X')){
						moves[5]=new Point(x+1,y-1);
					}
					if(x>=0 &&y-1>=0 &&( aiarr[x][y-1].toString().charAt(0)=='B' || aiarr[x][y-1].toString().charAt(1)=='X')){
						moves[6]=new Point(x,y-1);
					}
					if(x-1>=0 &&y-1>=0 &&( aiarr[x-1][y-1].toString().charAt(0)=='B' || aiarr[x-1][y-1].toString().charAt(1)=='X')){
						moves[7]=new Point(x-1,y-1);
					}
					return moves;
			}
				if(aiarr[x][y].toString().charAt(0)=='B'){
					Point[] moves=new Point[8];
					if(x+1<8 &&y+1<8 &&( aiarr[x+1][y+1].toString().charAt(0)=='W' || aiarr[x+1][y+1].toString().charAt(1)=='X')){
					moves[0]=new Point(x+1,y+1);
					}
					if(x>=0 &&y+1<8 &&( aiarr[x][y+1].toString().charAt(0)=='W' || aiarr[x][y+1].toString().charAt(1)=='X')){
					moves[1]=new Point(x,y+1);
					}
					if(x-1>=0 &&y+1<8 &&( aiarr[x-1][y+1].toString().charAt(0)=='W' || aiarr[x-1][y+1].toString().charAt(1)=='X')){
					moves[2]=new Point(x-1,y+1);
					}
					if(x+1<8 &&y>=0 &&( aiarr[x+1][y].toString().charAt(0)=='W' || aiarr[x+1][y].toString().charAt(1)=='X')){
					moves[3]=new Point(x+1,y);
					}
					if(x-1>=0 &&y<8 &&( aiarr[x-1][y].toString().charAt(0)=='W' || aiarr[x-1][y].toString().charAt(1)=='X')){
						moves[4]=new Point(x-1,y);
					}
					if(x+1<8 &&y-1>=0 &&( aiarr[x+1][y-1].toString().charAt(0)=='W' || aiarr[x+1][y-1].toString().charAt(1)=='X')){
						moves[5]=new Point(x+1,y-1);
					}
					if(x>=0 &&y-1>=0 &&( aiarr[x][y-1].toString().charAt(0)=='W' || aiarr[x][y-1].toString().charAt(1)=='X')){
						moves[6]=new Point(x,y-1);
					}
					if(x-1>=0 &&y-1>=0 &&( aiarr[x-1][y-1].toString().charAt(0)=='W' || aiarr[x-1][y-1].toString().charAt(1)=='X')){
						moves[7]=new Point(x-1,y-1);
					}
					return moves;
				}
				break;
				case 6:
				break;
		}
		return null;
		}
	
	public int toInt(Piece p){
		if(p.toString().charAt(1)== 'P')
			return 0;
		if(p.toString().charAt(1)=='N')
			return 1;
		if(p.toString().charAt(1)=='B')
			return 2;
		if(p.toString().charAt(1)== 'R')
			return 3;
		if(p.toString().charAt(1)== 'Q')
			return 4;
		if(p.toString().charAt(1)== 'K')
			return 5;
		if(p.toString().charAt(1)== 'X')
			return 6;
		return 80;
	}
	public void checkThreats(Board b,char color){
		aiarr=b.getBoardArray();
		numThreatened=0;
		numThreatening=0;
		for(int x=0;x<8;x++){ 
			for(int y=0;y<8;y++){
			String id=this.checkPiece(x,y);//x,y
				if(id.charAt(0)!=color)
				{
					int q=toInt(aiarr[x][y]);//x,y
					//int x=3;
					//int y=5;
					switch(q){
						case 0: if(color=='W'){//pawn
							
								if(x+1<8 && y+1<8){
								if((aiarr[x+1][y+1].toString().charAt(0)=='W')&& aiarr[x+1][y+1].toString().charAt(1)!='X'){
									threatened[numThreatened]=aiarr[x+1][y+1];
									threatening[numThreatening]=aiarr[x][y];
									locThreatened[numThreatened]=new Point(x+1,y+1);
									locThreatening[numThreatening]=new Point(x,y);
									numThreatened++;
									numThreatening++;
									}
								}
								if(x-1>=0 && y+1<8){
								if((aiarr[x-1][y+1].toString().charAt(0)=='W')&& aiarr[x-1][y+1].toString().charAt(1)!='X'){
									threatened[numThreatened]=aiarr[x-1][y+1];
									threatening[numThreatening]=aiarr[x][y];
									locThreatened[numThreatened]=new Point(x-1,y+1);
									locThreatening[numThreatening]=new Point(x,y);
									numThreatened++;
									numThreatening++;
									}
								}
								}//color
								else if(color=='B'){
									
									if(x+1<8 && y-1>=0){
									if((aiarr[x+1][y-1].toString().charAt(0)=='B')&& aiarr[x+1][y-1].toString().charAt(1)!='X'){
										threatened[numThreatened]=aiarr[x+1][y-1];
										threatening[numThreatening]=aiarr[x][y];
										locThreatened[numThreatened]=new Point(x+1,y-1);
										locThreatening[numThreatening]=new Point(x,y);
										numThreatened++;
										numThreatening++;
										}
									}
									if(x-1>=0 && y-1>=0){
									if((aiarr[x-1][y-1].toString().charAt(0)=='B')&& aiarr[x-1][y-1].toString().charAt(1)!='X'){
										threatened[numThreatened]=aiarr[x-1][y-1];
										threatening[numThreatening]=aiarr[x][y];
										locThreatened[numThreatened]=new Point(x-1,y-1);
										locThreatening[numThreatening]=new Point(x,y);
										numThreatened++;
										numThreatening++;
										}
									}
								
								}
								break;
						case 1: if(color=='W'){//KNIGHT
							
							if(x+1<8 && y+2<8){
							if((aiarr[x+1][y+2].toString().charAt(0)=='W')&& aiarr[x+1][y+2].toString().charAt(1)!='X'){
								threatened[numThreatened]=aiarr[x+1][y+2];
								threatening[numThreatening]=aiarr[x][y];
								locThreatened[numThreatened]=new Point(x+1,y+2);
								locThreatening[numThreatening]=new Point(x,y);
								numThreatened++;
								numThreatening++;
							}
							}
							if(x-1>=0 && y+2<8){
							if((aiarr[x-1][y+2].toString().charAt(0)=='W')&& aiarr[x-1][y+2].toString().charAt(1)!='X'){
								threatened[numThreatened]=aiarr[x-1][y+2];
								threatening[numThreatening]=aiarr[x][y];
								locThreatened[numThreatened]=new Point(x-1,y+2);
								locThreatening[numThreatening]=new Point(x,y);
								numThreatened++;
								numThreatening++;
							}
							}
							if(x+2<8 && y+1<8){
							if((aiarr[x+2][y+1].toString().charAt(0)=='W')&& aiarr[x+2][y+1].toString().charAt(1)!='X'){
								threatened[numThreatened]=aiarr[x+2][y+1];
								threatening[numThreatening]=aiarr[x][y];
								locThreatened[numThreatened]=new Point(x+2,y+1);
								locThreatening[numThreatening]=new Point(x,y);
								numThreatened++;
								numThreatening++;
							}
							}
							if(x-2>=0 && y+1<8){
							if((aiarr[x-2][y+1].toString().charAt(0)=='W')&& aiarr[x-2][y+1].toString().charAt(1)!='X'){
								threatened[numThreatened]=aiarr[x-2][y+1];
								threatening[numThreatening]=aiarr[x][y];
								locThreatened[numThreatened]=new Point(x-2,y+1);
								locThreatening[numThreatening]=new Point(x,y);
								numThreatened++;
								numThreatening++;
							}
							}
							if(x+1<8 && y-2>=0){
							if((aiarr[x+1][y-2].toString().charAt(0)=='W')&& aiarr[x+1][y-2].toString().charAt(1)!='X'){
								threatened[numThreatened]=aiarr[x+1][y-2];
								threatening[numThreatening]=aiarr[x][y];
								locThreatened[numThreatened]=new Point(x+1,y-2);
								locThreatening[numThreatening]=new Point(x,y);
								numThreatened++;
								numThreatening++;
							}
							}
							if(x-1>=0 && y-2>=0){
							if((aiarr[x-1][y-2].toString().charAt(0)=='W')&& aiarr[x-1][y-2].toString().charAt(1)!='X'){
								threatened[numThreatened]=aiarr[x-1][y-2];
								threatening[numThreatening]=aiarr[x][y];
								locThreatened[numThreatened]=new Point(x-1,y-2);
								locThreatening[numThreatening]=new Point(x,y);
								numThreatened++;
								numThreatening++;
							}
							}
							if(x+2<8 && y-1>=0){
							if((aiarr[x+2][y-1].toString().charAt(0)=='W')&& aiarr[x+2][y-1].toString().charAt(1)!='X'){
								threatened[numThreatened]=aiarr[x+2][y-1];
								threatening[numThreatening]=aiarr[x][y];
								locThreatened[numThreatened]=new Point(x+2,y-1);
								locThreatening[numThreatening]=new Point(x,y);
								numThreatened++;
								numThreatening++;
							}
							}
							if(x-2>=0 && y-1>=0){
							if((aiarr[x-2][y-1].toString().charAt(0)=='W')&& aiarr[x-2][y-1].toString().charAt(1)!='X'){
								threatened[numThreatened]=aiarr[x-2][y-1];
								threatening[numThreatening]=aiarr[x][y];
								locThreatened[numThreatened]=new Point(x-2,y-1);
								locThreatening[numThreatening]=new Point(x,y);
								numThreatened++;
								numThreatening++;
							}
							}
					
						
							
						}//color
							if(color=='B'){
								if(x+1<8 && y+2<8){
									if((aiarr[x+1][y+2].toString().charAt(0)=='B')&& aiarr[x+1][y+2].toString().charAt(1)!='X'){
										threatened[numThreatened]=aiarr[x+1][y+2];
										threatening[numThreatening]=aiarr[x][y];
										locThreatened[numThreatened]=new Point(x+1,y+2);
										locThreatening[numThreatening]=new Point(x,y);
										numThreatened++;
										numThreatening++;
									}
									}
									if(x-1>=0 && y+2<8){
									if((aiarr[x-1][y+2].toString().charAt(0)=='B')&& aiarr[x-1][y+2].toString().charAt(1)!='X'){
										threatened[numThreatened]=aiarr[x-1][y+2];
										threatening[numThreatening]=aiarr[x][y];
										locThreatened[numThreatened]=new Point(x-1,y+2);
										locThreatening[numThreatening]=new Point(x,y);
										numThreatened++;
										numThreatening++;
									}
									}
									if(x+2<8 && y+1<8){
									if((aiarr[x+2][y+1].toString().charAt(0)=='B')&& aiarr[x+2][y+1].toString().charAt(1)!='X'){
										threatened[numThreatened]=aiarr[x+2][y+1];
										threatening[numThreatening]=aiarr[x][y];
										locThreatened[numThreatened]=new Point(x+2,y+1);
										locThreatening[numThreatening]=new Point(x,y);
										numThreatened++;
										numThreatening++;
									}
									}
									if(x-2>=0 && y+1<8){
									if((aiarr[x-2][y+1].toString().charAt(0)=='B')&& aiarr[x-2][y+1].toString().charAt(1)!='X'){
										threatened[numThreatened]=aiarr[x-2][y+1];
										threatening[numThreatening]=aiarr[x][y];
										locThreatened[numThreatened]=new Point(x-2,y+1);
										locThreatening[numThreatening]=new Point(x,y);
										numThreatened++;
										numThreatening++;
									}
									}
									if(x+1<8 && y-2>=0){
									if((aiarr[x+1][y-2].toString().charAt(0)=='B')&& aiarr[x+1][y-2].toString().charAt(1)!='X'){
										threatened[numThreatened]=aiarr[x+1][y-2];
										threatening[numThreatening]=aiarr[x][y];
										locThreatened[numThreatened]=new Point(x+1,y-2);
										locThreatening[numThreatening]=new Point(x,y);
										numThreatened++;
										numThreatening++;
									}
									}
									if(x-1>=0 && y-2>=0){
									if((aiarr[x-1][y-2].toString().charAt(0)=='B')&& aiarr[x-1][y-2].toString().charAt(1)!='X'){
										threatened[numThreatened]=aiarr[x-1][y-2];
										threatening[numThreatening]=aiarr[x][y];
										locThreatened[numThreatened]=new Point(x-1,y-2);
										locThreatening[numThreatening]=new Point(x,y);
										numThreatened++;
										numThreatening++;
									}
									}
									if(x+2<8 && y-1>=0){
									if((aiarr[x+2][y-1].toString().charAt(0)=='B')&& aiarr[x+2][y-1].toString().charAt(1)!='X'){
										threatened[numThreatened]=aiarr[x+2][y-1];
										threatening[numThreatening]=aiarr[x][y];
										locThreatened[numThreatened]=new Point(x+2,y-1);
										locThreatening[numThreatening]=new Point(x,y);
										numThreatened++;
										numThreatening++;
									}
									}
									if(x-2>=0 && y-1>=0){
									if((aiarr[x-2][y-1].toString().charAt(0)=='B')&& aiarr[x-2][y-1].toString().charAt(1)!='X'){
										threatened[numThreatened]=aiarr[x-2][y-1];
										threatening[numThreatening]=aiarr[x][y];
										locThreatened[numThreatened]=new Point(x-2,y-1);
										locThreatening[numThreatening]=new Point(x,y);
										numThreatened++;
										numThreatening++;
									}
									}
									
							
							}
							break;
					case 2: if(color=='W'){//bishop
			
						for(int i=x+1;i<8;i++){//Diagonal //ToDo-check if x+1 is out of bounds
							if(y+i-x<8 && y+i-x>=0){
							if(aiarr[i][y+i-x].toString().charAt(0)=='B'){
								i=8;
							}
							else if((aiarr[i][y+i-x].toString().charAt(0)=='W')&& aiarr[i][y+i-x].toString().charAt(1)!='X'){
								threatened[numThreatened]=aiarr[i][y+i-x];
								threatening[numThreatening]=aiarr[x][y];
								locThreatened[numThreatened]=new Point(i,y+i-x);
								locThreatening[numThreatening]=new Point(x,y);
								numThreatened++;
								numThreatening++;
								i=8;
							}
							}
						}
						for(int i=x+1;i<8;i++){
							if(y-i+x<8 && y-i+x>=0){
								if(aiarr[i][y-i+x].toString().charAt(0)=='B'){
									i=8;
								}
								else if((aiarr[i][y-i+x].toString().charAt(0)=='W')&& aiarr[i][y-i+x].toString().charAt(1)!='X'){
								threatened[numThreatened]=aiarr[i][y-i+x];
								threatening[numThreatening]=aiarr[x][y];
								locThreatened[numThreatened]=new Point(i,y-i+x);
								locThreatening[numThreatening]=new Point(x,y);
								numThreatened++;
								numThreatening++;
								i=8;
							}
							}
						}
						for(int i=x-1;i>=0;i--){
							if(y+i-x>=0 && y+i-x<8){
								if(aiarr[i][y+i-x].toString().charAt(0)=='B'){
									i=-1;
								}
								else if((aiarr[i][y+i-x].toString().charAt(0)=='W')&& aiarr[i][y+i-x].toString().charAt(1)!='X'){
								threatened[numThreatened]=aiarr[i][y+i-x];
								threatening[numThreatening]=aiarr[x][y];
								locThreatened[numThreatened]=new Point(i,y+i-x);
								locThreatening[numThreatening]=new Point(x,y);
								numThreatened++;
								numThreatening++;
								i=-1;
							}
							}
						}
						for(int i=x-1;i>=0;i--){//Changed >=0 to >=0
							if(y-i+x>=0 && y-i+x<8){
								if(aiarr[i][y-i+x].toString().charAt(0)=='B'){
									i=-1;
								}
								else if((aiarr[i][y-i+x].toString().charAt(0)=='W')&& aiarr[i][y-i+x].toString().charAt(1)!='X'){
								threatened[numThreatened]=aiarr[i][y-i+x];
								threatening[numThreatening]=aiarr[x][y];
								locThreatened[numThreatened]=new Point(i,y-i+x);
								locThreatening[numThreatening]=new Point(x,y);
								numThreatened++;
								numThreatening++;
								i=-1;
							}
							}
						}
					}//color
						if(color=='B'){
						
							for(int i=x+1;i<8;i++){//Diagonal
								
								if(y+i-x<8 && y+i-x>=0){
									if(aiarr[i][y].toString().charAt(0)=='W' && aiarr[i][y].toString().charAt(1)!='X'){
										i=8;
									}
									else if((aiarr[i][y+i-x].toString().charAt(0)=='B')&& aiarr[i][y+i-x].toString().charAt(1)!='X'){
									threatened[numThreatened]=aiarr[i][y+i-x];
									threatening[numThreatening]=aiarr[x][y];
									locThreatened[numThreatened]=new Point(i,y+i-x);
									locThreatening[numThreatening]=new Point(x,y);
									numThreatened++;
									numThreatening++;
									i=8;
								}
								}
							}
							for(int i=x+1;i<8;i++){
								if(y-i+x<8 && y-i+x>=0){
									if(aiarr[i][y].toString().charAt(0)=='W' && aiarr[i][y].toString().charAt(1)!='X'){
										i=8;
									}
									else if((aiarr[i][y-i+x].toString().charAt(0)=='B')&& aiarr[i][y-i+x].toString().charAt(1)!='X'){
									threatened[numThreatened]=aiarr[i][y-i+x];
									threatening[numThreatening]=aiarr[x][y];
									locThreatened[numThreatened]=new Point(i,y-i+x);
									locThreatening[numThreatening]=new Point(x,y);
									numThreatened++;
									numThreatening++;
									i=8;
								}
								}
							}
							for(int i=x-1;i>=0;i--){
								if(y+i-x<8 && y+i-x>=0){
									if(aiarr[i][y].toString().charAt(0)=='W' && aiarr[i][y].toString().charAt(1)!='X'){
										i=-1;
									}
									else if((aiarr[i][y+i-x].toString().charAt(0)=='B')&& aiarr[i][y+i-x].toString().charAt(1)!='X'){
									threatened[numThreatened]=aiarr[i][y+i-x];
									threatening[numThreatening]=aiarr[x][y];
									locThreatened[numThreatened]=new Point(i,y+i-x);
									locThreatening[numThreatening]=new Point(x,y);
									numThreatened++;
									numThreatening++;
									i=-1;
								}
								}
							}
							for(int i=x-1;i>=0;i--){
								if(y-i+x<8 && y-i+x>=0){
									if(aiarr[i][y].toString().charAt(0)=='W' && aiarr[i][y].toString().charAt(1)!='X'){
										i=-1;
									}
									else if((aiarr[i][y-i+x].toString().charAt(0)=='B')&& aiarr[i][y-i+x].toString().charAt(1)!='X'){
									threatened[numThreatened]=aiarr[i][y-i+x];
									threatening[numThreatening]=aiarr[x][y];
									locThreatened[numThreatened]=new Point(i,y-i+x);
									locThreatening[numThreatening]=new Point(x,y);
									numThreatened++;
									numThreatening++;
									i=-1;
								}
								}
							}
						
						
					}
					break;
						case 3: if(color=='W'){// Rook
									for(int i=x+1;i<8;i++){
										 if(aiarr[i][y].toString().charAt(0)=='B'){
											 i=8;
										 }
										 else if((aiarr[i][y].toString().charAt(0)=='W')&& aiarr[i][y].toString().charAt(1)!='X'){
										
											threatened[numThreatened]=aiarr[i][y];
											threatening[numThreatening]=aiarr[x][y];
											locThreatened[numThreatened]=new Point(i,y);
											locThreatening[numThreatening]=new Point(x,y);
											numThreatened++;
											numThreatening++;
											i=8;
											
										}
									
							
									}
									for(int i=x-1;i>=0;i--){
										if(aiarr[i][y].toString().charAt(0)=='B'){
											 i=-1;
										 }
										else if((aiarr[i][y].toString().charAt(0)=='W')&& aiarr[i][y].toString().charAt(1)!='X'){
											
											threatened[numThreatened]=aiarr[i][y];
											threatening[numThreatening]=aiarr[x][y];
											locThreatened[numThreatened]=new Point(i,y);
											locThreatening[numThreatening]=new Point(x,y);
											numThreatened++;
											numThreatening++;
											i=-1;
										}
									
									}
									for(int i=y+1;i<8;i++){
										if(aiarr[x][i].toString().charAt(0)=='B'){
											i=8;
										}
										else if((aiarr[x][i].toString().charAt(0)=='W')&& aiarr[x][i].toString().charAt(1)!='X'){
								
											threatened[numThreatened]=aiarr[x][i];
											threatening[numThreatening]=aiarr[x][y];
											locThreatened[numThreatened]=new Point(x,i);
											locThreatening[numThreatening]=new Point(x,y);
											numThreatened++;
											numThreatening++;
											
											i=8;
										}
										
									}
									for(int i=y-1;i>=0;i--){
										if(aiarr[x][i].toString().charAt(0)=='B'){
											 i=-1;
										 }
										else if((aiarr[x][i].toString().charAt(0)=='W')&& aiarr[x][i].toString().charAt(1)!='X'){
										
											threatened[numThreatened]=aiarr[x][i];
											threatening[numThreatening]=aiarr[x][y];
											locThreatened[numThreatened]=new Point(x,i);
											locThreatening[numThreatening]=new Point(x,y);
											numThreatened++;
											numThreatening++;
											i=-1;
										}
										
									}
						}//color
									if(color=='B'){
										for(int i=x+1;i<8;i++){
											if(aiarr[i][y].toString().charAt(0)=='W' && aiarr[i][y].toString().charAt(1)!='X'){
												 i=8;
											 }
											else if((aiarr[i][y].toString().charAt(0)=='B')&& aiarr[i][y].toString().charAt(1)!='X'){
												threatened[numThreatened]=aiarr[i][y];
												threatening[numThreatening]=aiarr[x][y];
												locThreatened[numThreatened]=new Point(i,y);
												locThreatening[numThreatening]=new Point(x,y);
												numThreatened++;
												numThreatening++;
												i=8;
												
											}
											
								
										}
										for(int i=x-1;i>=0;i--){
											
											if(aiarr[i][y].toString().charAt(0)=='W' && aiarr[i][y].toString().charAt(1)!='X'){
												 i=-1;
											 }
											else if((aiarr[i][y].toString().charAt(0)=='B')&& aiarr[i][y].toString().charAt(1)!='X'){
												threatened[numThreatened]=aiarr[i][y];
												threatening[numThreatening]=aiarr[x][y];
												locThreatened[numThreatened]=new Point(i,y);
												locThreatening[numThreatening]=new Point(x,y);
												numThreatened++;
												numThreatening++;
												i=-1;
											}
											
										}
										for(int i=y+1;i<8;i++){
											if(aiarr[x][i].toString().charAt(0)=='W' && aiarr[x][i].toString().charAt(1)!='X'){
												 i=8;
											 }
											else if((aiarr[x][i].toString().charAt(0)=='B')&& aiarr[x][i].toString().charAt(1)!='X'){
												threatened[numThreatened]=aiarr[x][i];
												threatening[numThreatening]=aiarr[x][y];
												locThreatened[numThreatened]=new Point(x,i);
												locThreatening[numThreatening]=new Point(x,y);
												numThreatened++;
												numThreatening++;
												i=8;
											}
											
										}
										for(int i=y-1;i>=0;i--){
											if(aiarr[x][i].toString().charAt(0)=='W' && aiarr[x][i].toString().charAt(1)!='X'){
												 i=-1;
											 }
											else if((aiarr[x][i].toString().charAt(0)=='B')&& aiarr[x][i].toString().charAt(1)!='X'){
												threatened[numThreatened]=aiarr[x][i];
												threatening[numThreatening]=aiarr[x][y];
												locThreatened[numThreatened]=new Point(x,i);
												locThreatening[numThreatening]=new Point(x,y);
												numThreatened++;
												numThreatening++;
												i=-1;
											}
											
										
									}
						}	
						
									break;
								
								case 4: if(color=='W'){//Queen
						
									for(int i=x+1;i<8;i++){
										if(aiarr[i][y].toString().charAt(0)=='B'){
											i=8;
										}
										else if((aiarr[i][y].toString().charAt(0)=='W')&& aiarr[i][y].toString().charAt(1)!='X'){
											threatened[numThreatened]=aiarr[i][y];
											threatening[numThreatening]=aiarr[x][y];
											locThreatened[numThreatened]=new Point(i,y);
											locThreatening[numThreatening]=new Point(x,y);
											numThreatened++;
											numThreatening++;
											i=8;
											
										}
										
							
									}
									for(int i=x-1;i>=0;i--){
										if(aiarr[i][y].toString().charAt(0)=='B'){
											i=-1;
										}
										else if((aiarr[i][y].toString().charAt(0)=='W')&& aiarr[i][y].toString().charAt(1)!='X'){
											threatened[numThreatened]=aiarr[i][y];
											threatening[numThreatening]=aiarr[x][y];
											locThreatened[numThreatened]=new Point(i,y);
											locThreatening[numThreatening]=new Point(x,y);
											numThreatened++;
											numThreatening++;
											i=-1;
										}
										
									}
									for(int i=y+1;i<8;i++){
										if(aiarr[x][i].toString().charAt(0)=='B'){
											i=8;
										}
										else if((aiarr[x][i].toString().charAt(0)=='W')&& aiarr[x][i].toString().charAt(1)!='X'){
											threatened[numThreatened]=aiarr[x][i];
											threatening[numThreatening]=aiarr[x][y];
											locThreatened[numThreatened]=new Point(x,i);
											locThreatening[numThreatening]=new Point(x,y);
											numThreatened++;
											numThreatening++;
											i=8;
										}
										
									}
									for(int i=y-1;i>=0;i--){
										if(aiarr[x][i].toString().charAt(0)=='B'){
											i=-1;
										}
										else if((aiarr[x][i].toString().charAt(0)=='W')&& aiarr[x][i].toString().charAt(1)!='X'){
											threatened[numThreatened]=aiarr[x][i];
											threatening[numThreatening]=aiarr[x][y];
											locThreatened[numThreatened]=new Point(x,i);
											locThreatening[numThreatening]=new Point(x,y);
											numThreatened++;
											numThreatening++;
											i=-1;
										}
										
									}
									for(int i=x+1;i<8;i++){//Diagonal
										if(y+i-x<8 && y+i-x>=0){
											if(aiarr[i][y+i-x].toString().charAt(0)=='B'){
												i=8;
											}
											else if((aiarr[i][y+i-x].toString().charAt(0)=='W')&& aiarr[i][y+i-x].toString().charAt(1)!='X'){
											threatened[numThreatened]=aiarr[i][y+i-x];
											threatening[numThreatening]=aiarr[x][y];
											locThreatened[numThreatened]=new Point(i,y+i-x);
											locThreatening[numThreatening]=new Point(x,y);
											numThreatened++;
											numThreatening++;
											i=8;
										}
										}
									}
									for(int i=x+1;i<8;i++){
										if(y-i+x<8 && y-i+x>=0){
											if(aiarr[i][y-i+x].toString().charAt(0)=='B'){
												i=8;
											}
											else if((aiarr[i][y-i+x].toString().charAt(0)=='W')&& aiarr[i][y-i+x].toString().charAt(1)!='X'){
											threatened[numThreatened]=aiarr[i][y-i+x];
											threatening[numThreatening]=aiarr[x][y];
											locThreatened[numThreatened]=new Point(i,y-i+x);
											locThreatening[numThreatening]=new Point(x,y);
											numThreatened++;
											numThreatening++;
											i=8;
										}
										}
									}
									for(int i=x-1;i>=0;i--){
										if(y+i-x<8 && y+i-x>=0){
											if(aiarr[i][y+i-x].toString().charAt(0)=='B'){
												i=-1;
											}
											else if((aiarr[i][y+i-x].toString().charAt(0)=='W')&& aiarr[i][y+i-x].toString().charAt(1)!='X'){
											threatened[numThreatened]=aiarr[i][y+i-x];
											threatening[numThreatening]=aiarr[x][y];
											locThreatened[numThreatened]=new Point(i,y+i-x);
											locThreatening[numThreatening]=new Point(x,y);
											numThreatened++;
											numThreatening++;
											i=-1;
										}
										}
									}
									for(int i=x-1;i>=0;i--){
										if(y-i+x<8 && y-i+x>=0){
											if(aiarr[i][y-i+x].toString().charAt(0)=='B'){
												i=-1;
											}
											else if((aiarr[i][y-i+x].toString().charAt(0)=='W')&& aiarr[i][y-i+x].toString().charAt(1)!='X'){
											threatened[numThreatened]=aiarr[i][y-i+x];
											threatening[numThreatening]=aiarr[x][y];
											locThreatened[numThreatened]=new Point(i,y-i+x);
											locThreatening[numThreatening]=new Point(x,y);
											numThreatened++;
											numThreatening++;
											i=-1;
										}
										}
									}
								}//color
									if(color=='B'){
										for(int i=x+1;i<8;i++){
											if(aiarr[i][y].toString().charAt(0)=='W' && aiarr[i][y].toString().charAt(1)!='X'){
												i=8;
											}
											else if((aiarr[i][y].toString().charAt(0)=='B')&& aiarr[i][y].toString().charAt(1)!='X'){
												threatened[numThreatened]=aiarr[i][y];
												threatening[numThreatening]=aiarr[x][y];
												locThreatened[numThreatened]=new Point(i,y);
												locThreatening[numThreatening]=new Point(x,y);
												numThreatened++;
												numThreatening++;
												i=8;
												
											}
											
								
										}
										for(int i=x-1;i>=0;i--){
											if(aiarr[i][y].toString().charAt(0)=='W' && aiarr[i][y].toString().charAt(1)!='X'){
												i=-1;
											}
											else if((aiarr[i][y].toString().charAt(0)=='B')&& aiarr[i][y].toString().charAt(1)!='X'){
												threatened[numThreatened]=aiarr[i][y];
												threatening[numThreatening]=aiarr[x][y];
												locThreatened[numThreatened]=new Point(i,y);
												locThreatening[numThreatening]=new Point(x,y);
												numThreatened++;
												numThreatening++;
												i=-1;
											}
											
										}
										for(int i=y+1;i<8;i++){
											if(aiarr[x][i].toString().charAt(0)=='W' && aiarr[x][i].toString().charAt(1)!='X'){
												i=8;
											}
											else if((aiarr[x][i].toString().charAt(0)=='B')&& aiarr[x][i].toString().charAt(1)!='X'){
												threatened[numThreatened]=aiarr[x][i];
												threatening[numThreatening]=aiarr[x][y];
												locThreatened[numThreatened]=new Point(x,i);
												locThreatening[numThreatening]=new Point(x,y);
												numThreatened++;
												numThreatening++;
												i=8;
											}
											
										}
										for(int i=y-1;i>=0;i--){
											if(aiarr[x][i].toString().charAt(0)=='W' && aiarr[x][i].toString().charAt(1)!='X'){
												i=-1;
											}
											else if((aiarr[x][i].toString().charAt(0)=='B')&& aiarr[x][i].toString().charAt(1)!='X'){
												threatened[numThreatened]=aiarr[x][i];
												threatening[numThreatening]=aiarr[x][y];
												locThreatened[numThreatened]=new Point(x,i);
												locThreatening[numThreatening]=new Point(x,y);
												numThreatened++;
												numThreatening++;
												i=-1;
											}
											
										}
										for(int i=x+1;i<8;i++){//Diagonal
											if(y+i-x<8 && y+i-x>=0){
												if(aiarr[i][y+i-x].toString().charAt(0)=='W' && aiarr[i][y+i-x].toString().charAt(1)!='X'){
													i=8;
												}
												else if((aiarr[i][y+i-x].toString().charAt(0)=='B')&& aiarr[i][y+i-x].toString().charAt(1)!='X'){
												threatened[numThreatened]=aiarr[i][y+i-x];
												threatening[numThreatening]=aiarr[x][y];
												locThreatened[numThreatened]=new Point(i,y+i-x);
												locThreatening[numThreatening]=new Point(x,y);
												numThreatened++;
												numThreatening++;
												i=8;
											}
											}
										}
										for(int i=x+1;i<8;i++){
											if(y-i+x<8 && y-i+x>=0){
												if(aiarr[i][y-i+x].toString().charAt(0)=='W' && aiarr[i][y-i+x].toString().charAt(1)!='X'){
													i=8;
												}
												else if((aiarr[i][y-i+x].toString().charAt(0)=='B')&& aiarr[i][y-i+x].toString().charAt(1)!='X'){
												threatened[numThreatened]=aiarr[i][y-i+x];
												threatening[numThreatening]=aiarr[x][y];
												locThreatened[numThreatened]=new Point(i,y-i+x);
												locThreatening[numThreatening]=new Point(x,y);
												numThreatened++;
												numThreatening++;
												i=8;
											}
											}
										}
										for(int i=x-1;i>=0;i--){
											if(y+i-x<8 && y+i-x>=0){
												if(aiarr[i][y+i-x].toString().charAt(0)=='W' && aiarr[i][y+i-x].toString().charAt(1)!='X'){
													i=-1;
												}
												else if((aiarr[i][y+i-x].toString().charAt(0)=='B')&& aiarr[i][y+i-x].toString().charAt(1)!='X'){
												threatened[numThreatened]=aiarr[i][y+i-x];
												threatening[numThreatening]=aiarr[x][y];
												locThreatened[numThreatened]=new Point(i,y+i-x);
												locThreatening[numThreatening]=new Point(x,y);
												numThreatened++;
												numThreatening++;
												i=-1;
											}
											}
										}
										for(int i=x-1;i>=0;i--){
											if(y-i+x<8 && y-i+x>=0){
												if(aiarr[i][y-i+x].toString().charAt(0)=='W' && aiarr[i][y-i+x].toString().charAt(1)!='X'){
													i=-1;
												}
												else if((aiarr[i][y-i+x].toString().charAt(0)=='B')&& aiarr[i][y-i+x].toString().charAt(1)!='X'){
												threatened[numThreatened]=aiarr[i][y-i+x];
												threatening[numThreatening]=aiarr[x][y];
												locThreatened[numThreatened]=new Point(i,y-i+x);
												locThreatening[numThreatening]=new Point(x,y);
												numThreatened++;
												numThreatening++;
												i=-1;
											}
											}
										}
									}
										
									
								
								break;
								case 5: if(color=='W'){//KING
									
									if(x+1<8 && y+1<8){
									if((aiarr[x+1][y+1].toString().charAt(0)=='W') && aiarr[x+1][y+1].toString().charAt(1)!='X'){
										threatened[numThreatened]=aiarr[x+1][y+1];
										threatening[numThreatening]=aiarr[x][y];
										locThreatened[numThreatened]=new Point(x+1,y+1);
										locThreatening[numThreatening]=new Point(x,y);
										numThreatened++;
										numThreatening++;
									}
									}
									if(x+1<8){
									if((aiarr[x+1][y].toString().charAt(0)=='W') && aiarr[x+1][y].toString().charAt(1)!='X'){
										threatened[numThreatened]=aiarr[x+1][y];
										threatening[numThreatening]=aiarr[x][y];
										locThreatened[numThreatened]=new Point(x+1,y);
										locThreatening[numThreatening]=new Point(x,y);
										numThreatened++;
										numThreatening++;
									}
									}
									if(x+1<8 && y-1>=0){
									if((aiarr[x+1][y-1].toString().charAt(0)=='W') && aiarr[x+1][y-1].toString().charAt(1)!='X'){
										threatened[numThreatened]=aiarr[x+1][y-1];
										threatening[numThreatening]=aiarr[x][y];
										locThreatened[numThreatened]=new Point(x+1,y-1);
										locThreatening[numThreatening]=new Point(x,y);
										numThreatened++;
										numThreatening++;
									}
									}
									if(y+1<8){
									if((aiarr[x][y+1].toString().charAt(0)=='W') && aiarr[x][y+1].toString().charAt(1)!='X'){
										threatened[numThreatened]=aiarr[x][y+1];
										threatening[numThreatening]=aiarr[x][y];
										locThreatened[numThreatened]=new Point(x,y+1);
										locThreatening[numThreatening]=new Point(x,y);
										numThreatened++;
										numThreatening++;
									}
									}
									if(y-1>=0){
									if((aiarr[x][y-1].toString().charAt(0)=='W') && aiarr[x][y-1].toString().charAt(1)!='X'){
										threatened[numThreatened]=aiarr[x][y-1];
										threatening[numThreatening]=aiarr[x][y];
										locThreatened[numThreatened]=new Point(x,y-1);
										locThreatening[numThreatening]=new Point(x,y);
										numThreatened++;
										numThreatening++;
									}
									}
									if(x-1>=0 && y+1<8){
									if((aiarr[x-1][y+1].toString().charAt(0)=='W') && aiarr[x-1][y+1].toString().charAt(1)!='X'){
										threatened[numThreatened]=aiarr[x-1][y+1];
										threatening[numThreatening]=aiarr[x][y];
										locThreatened[numThreatened]=new Point(x-1,y+1);
										locThreatening[numThreatening]=new Point(x,y);
										numThreatened++;
										numThreatening++;
									}
									}
									if(x-1>=0){
									if((aiarr[x-1][y].toString().charAt(0)=='W') && aiarr[x-1][y].toString().charAt(1)!='X'){
										threatened[numThreatened]=aiarr[x-1][y];
										threatening[numThreatening]=aiarr[x][y];
										locThreatened[numThreatened]=new Point(x-1,y);
										locThreatening[numThreatening]=new Point(x,y);
										numThreatened++;
										numThreatening++;
									}
									}
									if(x-1>=0 && y-1>=0){
									if((aiarr[x-1][y-1].toString().charAt(0)=='W') && aiarr[x-1][y-1].toString().charAt(1)!='X'){
										threatened[numThreatened]=aiarr[x-1][y-1];
										threatening[numThreatening]=aiarr[x][y];
										locThreatened[numThreatened]=new Point(x-1,y-1);
										locThreatening[numThreatening]=new Point(x,y);
										numThreatened++;
										numThreatening++;
									}
									}
								}
								else if(color=='B'){
									if(x+1<8 && y+1<8){
									if((aiarr[x+1][y+1].toString().charAt(0)=='B') && aiarr[x+1][y+1].toString().charAt(1)!='X'){
										threatened[numThreatened]=aiarr[x+1][y+1];
										threatening[numThreatening]=aiarr[x][y];
										locThreatened[numThreatened]=new Point(x+1,y+1);
										locThreatening[numThreatening]=new Point(x,y);
										numThreatened++;
										numThreatening++;
									}
									}
									if(x+1<8){
									if((aiarr[x+1][y].toString().charAt(0)=='B') && aiarr[x+1][y].toString().charAt(1)!='X'){
										threatened[numThreatened]=aiarr[x+1][y];
										threatening[numThreatening]=aiarr[x][y];
										locThreatened[numThreatened]=new Point(x+1,y);
										locThreatening[numThreatening]=new Point(x,y);
										numThreatened++;
										numThreatening++;
									}
									}
									if(x+1<8 && y-1>=0){
									if((aiarr[x+1][y-1].toString().charAt(0)=='B') && aiarr[x+1][y-1].toString().charAt(1)!='X'){
										threatened[numThreatened]=aiarr[x+1][y-1];
										threatening[numThreatening]=aiarr[x][y];
										locThreatened[numThreatened]=new Point(x+1,y-1);
										locThreatening[numThreatening]=new Point(x,y);
										numThreatened++;
										numThreatening++;
									}
									}
									if(y+1<8){
									if((aiarr[x][y+1].toString().charAt(0)=='B') && aiarr[x][y+1].toString().charAt(1)!='X'){
										threatened[numThreatened]=aiarr[x][y+1];
										threatening[numThreatening]=aiarr[x][y];
										locThreatened[numThreatened]=new Point(x,y+1);
										locThreatening[numThreatening]=new Point(x,y);
										numThreatened++;
										numThreatening++;
									}
									}
									if(y-1>=0){
									if((aiarr[x][y-1].toString().charAt(0)=='B') && aiarr[x][y-1].toString().charAt(1)!='X'){
										threatened[numThreatened]=aiarr[x][y-1];
										threatening[numThreatening]=aiarr[x][y];
										locThreatened[numThreatened]=new Point(x,y-1);
										locThreatening[numThreatening]=new Point(x,y);
										numThreatened++;
										numThreatening++;
									}
									}
									if(x-1>=0 && y+1<8){
									if((aiarr[x-1][y+1].toString().charAt(0)=='B') && aiarr[x-1][y+1].toString().charAt(1)!='X'){
										threatened[numThreatened]=aiarr[x-1][y+1];
										threatening[numThreatening]=aiarr[x][y];
										locThreatened[numThreatened]=new Point(x-1,y+1);
										locThreatening[numThreatening]=new Point(x,y);
										numThreatened++;
										numThreatening++;
									}
									}
									if(x-1>=0){
									if((aiarr[x-1][y].toString().charAt(0)=='B') && aiarr[x-1][y].toString().charAt(1)!='X'){
										threatened[numThreatened]=aiarr[x-1][y];
										threatening[numThreatening]=aiarr[x][y];
										locThreatened[numThreatened]=new Point(x-1,y);
										locThreatening[numThreatening]=new Point(x,y);
										numThreatened++;
										numThreatening++;
									}
									}
									if(x-1>=0 && y-1>=0){
									if((aiarr[x-1][y-1].toString().charAt(0)=='B') && aiarr[x-1][y-1].toString().charAt(1)!='X'){
										threatened[numThreatened]=aiarr[x-1][y-1];
										threatening[numThreatening]=aiarr[x][y];
										locThreatened[numThreatened]=new Point(x-1,y-1);
										locThreatening[numThreatening]=new Point(x,y);
										numThreatened++;
										numThreatening++;
									}
									}
								}
								break;
								case 6:
								break;
									
								}
									
								}
								
						
							
						}
		}
			
		
			
		
	}
	
	public String checkPiece(int x, int y){
	Piece q=aiarr[x][y];
	return q.toString();
	}
	public void printThreats(){
		for(int i=0;i<numThreatened;i++){
			//System.out.println(numThreatened);
			System.out.print(threatened[i]);
			System.out.print(locThreatened[i].toString());
			System.out.print(threatening[i]);
			System.out.print(locThreatening[i].toString());
			
		}
	}
	
	public static void main(String[] args){
		/*AI test=new AI();
		test.setColor('B');
		Board b=new Board();
		Piece [][] arr1=new Piece[8][8];
		arr1=b.getBoardArray();
		//arr1[1][2]=new Knight(true);
		//arr1[2][3]=new Pawn(true);
		//arr1[3][4]=new Queen(false);
		//arr1[3][2]=new King(false);
		//arr1[2][2]=new Queen(true);
		//b.printBoard();
		test.readBoard(b);
		
	
	
		/*test.ThreateningScore(b);
		System.out.println(test.loc1);
		System.out.println(test.loc2);*/
		/*b.printBoard();
		System.out.println();
		
		
		test.checkThreats(b,'B');
		
		//test.checkMoves();
		b.printBoard();
		//test.printThreats();
		//boolean B=test.isThreatened(new Point(3,1));
		//System.out.println(B);
		/*Point[] a=test.canMove(new Point(5,6));
		for(int i=0;i<a.length;i++){
			System.out.println(a[i]);
		}*/
	AI test1=new AI();
	test1.setColor('W');
	Board testBoard=new Board();
		for(int i=0;i<10;i++){
			test1.readBoard(testBoard);
			test1.checkThreats(testBoard, 'W');
			test1.checkMoves();
			testBoard.setBoardArray(test1.aiarr);
			testBoard.printBoard();
			System.out.println();

		}
	}
	
	
}