class go{

    public static void main(String[] args) {
	int n1,n2,n3,L,N, NB;
	double del, a1, a2;
	int i,j;
	int tau=200;
	boolean BlackTurn, WhiteTurn, flag, GameOn;
	GoBoard Board;
	byte Q[][];

	if(args.length==0) L=0;
	else L=Integer.parseInt(args[0]);

	if(L<=0){
	    System.out.println("Creating a default board");
	    Board = new GoBoard();
	    Board.setupCanvas();
	    Board.showBoard("Default Board, L="+Board.getBoardSize());}
	else{
	    System.out.println("Creating an empty board of size "+L);
	    Board = new GoBoard(L);
	    Board.setupCanvas();
	    Board.showBoard("Custom Board, L="+Board.getBoardSize());}

	
	GameOn = true;
	BlackTurn=true; WhiteTurn=false;

	N = 1;

	while(GameOn){
	    
	while(BlackTurn){
	    if (StdDraw.isMousePressed()){
		i = (int)(StdDraw.mouseX()+0.5);
		j = (int)(StdDraw.mouseY()+0.5);
		StdDraw.pause(tau);
        	flag = Board.makeMove(i,j,(byte)1);
		if(flag){BlackTurn=false;WhiteTurn=true;}}
		}
	Board.showBoard("Move="+N);

	while(WhiteTurn){
	    if (StdDraw.isMousePressed()){
		i = (int)(StdDraw.mouseX()+0.5);
		j = (int)(StdDraw.mouseY()+0.5);
		StdDraw.pause(tau);
        	flag = Board.makeMove(i,j,(byte)2);
		if(flag){BlackTurn=true;WhiteTurn=false;}}
	}
	Board.showBoard("Move="+N);

	N++;
	}

	
    } // End main method

}     // End class go

