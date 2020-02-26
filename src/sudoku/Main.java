package sudoku;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		
		
		Sudoku s = new Sudoku();
		s.leggiSudoku("tabellonediff.txt");
		System.out.println("=================SUDOKU IN INPUT================");
		s.printSudoku();
		s.componi();
		//System.out.println("COLONNA:" +s.trovaColonna(2));
		//System.out.println("RIGA: "+s.trovaRiga(6).toString());
		s.combC(6,2);
			
		//s.tabellone();
		
		/*s.tabellone[8][0]=9;
		s.tabellone[8][1]=1;
		s.tabellone[8][2]=0;
		s.tabellone[8][3]=0;			
		s.tabellone[8][4]=7;
		s.tabellone[8][5]=0;s.tabellone[8][6]=3;s.tabellone[8][7]=0;s.tabellone[8][8]=0;*/
		System.out.println(s.checkRiga(s.findCell(5, 8)).toString());
		
		s.risolvi();
		System.out.println("=================SOLUZIONE================");

		s.printSudoku();
		
		
	}

}
