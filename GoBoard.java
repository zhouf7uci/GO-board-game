import java.awt.Font;

public class GoBoard {
    private static final   int Lstd=19;
    private static final   double rad=0.003;
    private static         int NumberOfBoards=0;
    private                int L=Lstd;
    private                byte[][] P;
    private                boolean[][] C;
    private                double del=2.0;
    private                double prad;
    public                 int il, jl;

    
    //
    // CONSTRUCTORS
    //
    
    public GoBoard(){
	int i,j;
	P = new byte[L+1][L+1];
	C = new boolean[L+1][L+1];
	for(i=1;i<=L;i++){
	    for(j=1;j<=L;j++)
		{P[i][j]=0;}}
	NumberOfBoards++;}

    public GoBoard(int L){
	int i,j;
	this.L = L;
	P = new byte[L+1][L+1];
	C = new boolean[L+1][L+1];
	for(i=1;i<=L;i++){
	    for(j=1;j<=L;j++)
		{P[i][j]=0;}}
	NumberOfBoards++;}

    public GoBoard(byte[][] Q){
	int i,j,LL;
	boolean flag;
	LL = Q[0].length-1;
	flag = checkBoardState(Q,LL);
	if(flag){
	   L = LL;
  	   P = new byte[L+1][L+1];
	   C = new boolean[L+1][L+1];
	   for(i=1;i<=L;i++){for(j=1;j<=L;j++){
	   P[i][j]=Q[i][j];}}
	   NumberOfBoards++;}
	else System.out.println("Input board state incorrect");
    }
    
    //
    // PUBLIC METHODS
    //

    public static int getNumberOfBoards(){return NumberOfBoards;}
    public        int getBoardSize(){return L;}

    public boolean setBoardState(byte[][] Q){
	int i,j,LL; boolean flag;
	LL = L;
	flag = checkBoardState(Q,LL);
	if(flag){
	for(i=1;i<=L;i++){for(j=1;j<=L;j++)
	   {P[i][j]=Q[i][j];}}}
	return flag;}    // End method setBoardState

    
    
    public boolean makeMove(int i, int j, byte n){
	boolean status;
	byte m;

	if(i<1 || i>L || j<1 || j>L) return false;
	
	if(P[i][j]!=0)               return false;

	m = (byte)0;
	if      (n==(byte)1) m=(byte)2;
	else if (n==(byte)2) m=(byte)1;
	else System.out.println("makeMove unexpected error A");
	
	P[i][j]=n;
	checkDeadStones(i, j, m);
	
	setC(n);

	status = checkLiberties(i,j,n);
	if(!status) {P[i][j]=0; return false;}
	il = i; jl=j;
	return true;
	    
    } // End method makeMove
    

    private void setC(byte n){
	int i,j;
	for(i=1;i<=L;i++){for(j=1;j<=L;j++){C[i][j] = P[i][j]==n;}}
    } // End method setC


    

    public void checkDeadStones(int i, int j, byte n){
	boolean status;
	int k, l;

	k=i-1; l=j;
	if(k>0 && P[k][l]==n) {setC(n);status = checkLiberties(k,l,n);
	    if(!status) removeDeadStones(k,l,n);
	}

	k=i+1; l=j;
	if(k<=L && P[k][l]==n) {setC(n);status = checkLiberties(k,l,n);
	    if(!status) removeDeadStones(k,l,n);
	}

	k=i; l=j-1;
	if(l>0 && P[k][l]==n) {setC(n);status = checkLiberties(k,l,n);
	    if(!status) removeDeadStones(k,l,n);
	}

	k=i; l=j+1;
	if(l<=L && P[k][l]==n) {setC(n);status = checkLiberties(k,l,n);
	    if(!status) removeDeadStones(k,l,n);
	}
	
    } //End method checkDeadStones


   public void removeDeadStones(int i, int j, byte n){
       int k,l;

       P[i][j]=0;

       if(i>1){
	   k=i-1; l=j;
	   if(P[k][l]==n) removeDeadStones(k,l,n);
       }
	
       if(i<L){
	   k=i+1; l=j;
	   if(P[k][l]==n) removeDeadStones(k,l,n);
       }

       if(j>1){
	   k=i; l=j-1;
	   if(P[k][l]==n) removeDeadStones(k,l,n);
       }
	
       if(j<L){
	   k=i; l=j+1;
	   if(P[k][l]==n) removeDeadStones(k,l,n);
       }
	
   }

    
    public boolean checkLiberties(int i, int j, byte n){
	int k, l;
	boolean status=false;
	
	if(i>1 && P[i-1][j]==0) return true;
	if(i<L && P[i+1][j]==0) return true;
	if(j>1 && P[i][j-1]==0) return true;
	if(j<L && P[i][j+1]==0) return true;
	C[i][j] = false;

	if(i>1){
	k=i-1; l=j;
	if(C[k][l]) {status = checkLiberties(k,l,n);
	     if(status) return true;}}

	if(i<L){
	k=i+1; l=j;
	if(C[k][l]) {status = checkLiberties(k,l,n);
	    if(status) return true;}}

	if(j>1){
	k=i; l=j-1;
	if(C[k][l]) {status = checkLiberties(k,l,n);
	    if(status) return true;}}

	if(j<L){
	k=i; l=j+1;
	if(C[k][l]) {status = checkLiberties(k,l,n);
	    if(status) return true;}}

	return false;

    }





    public void setupCanvas(){

	prad = rad*(double)(19)/(double)(L);
	StdDraw.setCanvasSize(800,800);
	StdDraw.enableDoubleBuffering();

	StdDraw.setScale(1-del, L+del);
	StdDraw.setPenRadius(prad);
	Font font = new Font("Arial", Font.BOLD, 22);
	StdDraw.setFont(font);

    }
    
    public void showBoard(String boardTitle){

	double a1=0.1, a2=0.2;
	int n1, n2, n3, i, j;
	
	if     (L==19){n1=4;n2=16;n3=10;}
	else if(L==13){n1=4;n2=10;n3= 7;}
	else          {n1=0;n2= 0;n3= 0;}
	
	StdDraw.clear(StdDraw.LIGHT_GRAY);
        StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
	StdDraw.filledSquare((L-1)/2+1,(L-1)/2+1,(L-1)/2+del/4.);
        StdDraw.setPenColor(StdDraw.BLACK);

	for(i=1;i<=L;i++){
	StdDraw.line(i,1,i,L);
	StdDraw.line(1,i,L,i);}

	for(i=1;i<=L;i++){
	    for(j=1;j<=L;j++){
	StdDraw.filledCircle(i,j,a1);}}

	if(L==19 || L==13){
	StdDraw.filledCircle(n1, n1, a2);
	StdDraw.filledCircle(n1, n2, a2);
	StdDraw.filledCircle(n1, n3, a2);
	StdDraw.filledCircle(n2, n1, a2);
	StdDraw.filledCircle(n2, n2, a2);
	StdDraw.filledCircle(n2, n3, a2);
	StdDraw.filledCircle(n3, n1, a2);
	StdDraw.filledCircle(n3, n2, a2);
	StdDraw.filledCircle(n3, n3, a2);}

        StdDraw.setPenColor(StdDraw.BLACK);
	for(i=1;i<=L;i++){
	for(j=1;j<=L;j++){
	if(P[i][j]==1) StdDraw.filledCircle(i, j, 0.5);
	}}

        StdDraw.setPenColor(StdDraw.WHITE);
	for(i=1;i<=L;i++){
	for(j=1;j<=L;j++){
	if(P[i][j]==2) StdDraw.filledCircle(i, j, 0.5);
	}}

	StdDraw.setPenRadius(3.*prad);
	if(P[il][jl]==1) {StdDraw.setPenColor(StdDraw.WHITE);
	    StdDraw.circle(il,jl,0.3);}
	if(P[il][jl]==2) {StdDraw.setPenColor(StdDraw.BLACK);
	    StdDraw.circle(il,jl,0.3);}
	StdDraw.setPenRadius(prad);

	    
        StdDraw.setPenColor(StdDraw.RED);
	StdDraw.text((float)(L-1)/2.0,(float)L+0.5*del, boardTitle);
	StdDraw.show();

	} // End method showBoard

    

    public void waitForClick(){
	boolean flag=true;
	int i,j;
	while(flag){
	if(StdDraw.isMousePressed()){
	i = (int)(StdDraw.mouseX()+0.5);
	j = (int)(StdDraw.mouseY()+0.5);
	flag = !(i>=1 && i<=L && j>=1 && j<=L);}}
    } // End method waitForClick




    public static boolean checkBoardState(byte[][] Q, int LL){
	int i,j,I,J;
	
	I = Q.length-1;
	if(I != LL) return false;
	for(i=0;i<I;i++) {J=Q[i].length-1;
	   if(J != LL) return false;}

	for(i=1;i<=LL;i++){for(j=1;j<=LL;j++){
	   if(Q[i][j]<0 || Q[i][j]>2) return false;}}

	return true;
    } // End method checkBoardState


    
    //
    // PRIVATE METHODS
    //

    
    /*  private boolean checkLibertiesBlack(int i, int j){

	    }	*/  //End method checkLibertiesBlack

} // End class GoBoard


