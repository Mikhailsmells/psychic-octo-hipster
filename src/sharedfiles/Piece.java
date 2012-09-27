package sharedfiles;

public abstract class Piece {

	private boolean color;
	private String id; 
	public Piece(boolean b, String id){
		this.color=b; //True is White, Black is False
		this.id = id;
	}
	public boolean getColor(){
		return color;
	}
	public boolean setColor(boolean b){
		color = b;
		return b;
	}

	public String toString(){
		String x;
		if (color==false){
			x = "B";
		}
		else x = "W";
	return x+id; 
	}

}
