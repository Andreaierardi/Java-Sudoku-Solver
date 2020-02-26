package sudoku;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Sudoku {
	
	public int [][]tabellone;
	public Quadrante[] sudoku;
	public ArrayList<Integer> combinazione;
	public Sudoku()
	{
		tabellone= new int[9][9];
		sudoku = new Quadrante[9];
		combinazione= new ArrayList<Integer>();
		for(int i = 0; i<sudoku.length;i++)
		{
			sudoku[i]= new Quadrante(i);
			combinazione.add(i+1);
		}
	}
	
	public void printSudoku()
	{
		
		for(int i =0 ; i<tabellone.length;i++)
		{
			System.out.print("\n");
			for(int j =0 ; j<tabellone[i].length;j++)
				if(tabellone[i][j]==0)
					System.out.print("_ ");
				else
				System.out.print(tabellone[i][j]+" ");
		}
		System.out.println("\n");
	}
	
	public void componi()
	{
		System.out.println();
		int index=0;
		for (int i = 0; i<3;i++)
			for (int j = 0; j<3;j++)
				{
					sudoku[0].modCell(0, index, tabellone[j][i], i, j);
					sudoku[1].modCell(1, index, tabellone[j][i+3], i+3, j);
					sudoku[2].modCell(2, index, tabellone[j][i+6], i+6, j);
					sudoku[3].modCell(3, index, tabellone[j+3][i], i, j+3);
					sudoku[4].modCell(4, index, tabellone[j+3][i+3], i+3, j+3);
					sudoku[5].modCell(5, index, tabellone[j+3][i+6], i+6, j+3);
					sudoku[6].modCell(6, index, tabellone[j+6][i], i, j+6);
					sudoku[7].modCell(7, index, tabellone[j+6][i+3], i+3, j+6);
					sudoku[8].modCell(8, index, tabellone[j+6][i+6], i+6, j+6);

					/*for(int z =0 ;z<9;z++)
					{
					
						if(z<3)
							sudoku[z].modCell(z,index, tabellone[i][j+z*3],j+z*3, i);
						else if(z>=3 && z<6)
							sudoku[z].modCell(z,index, tabellone[i+3][j+(z%3)*3],j+(z%3)*3, i+3);

						else
							sudoku[z].modCell(z,index,tabellone[i+6][j+(z%3)*3],j+(z%3)*3,i+6);
					}*/
					index++;
				}
	}
	public ArrayList<Integer> combQ(int q)
	{
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		//System.out.println("QUESTO:" +q);
	/*	if(q==8)
		{
			for(int i = 0 ;i < sudoku[8].qua.length;i++)
				System.out.println("Ck : " +sudoku[8].qua[i].val);
		}*/
		for(Cell c : sudoku[q].qua)
			if(c.val!=0)
				{	
					//System.out.println("ECCO: "+c.quadrante);
					tmp.add(c.val);
				}
		sudoku[q].combQ = tmp;
		freeL(q);
		return tmp;
	}
	
	public ArrayList<Cell> freeL(int q)
	{
		ArrayList<Cell> tmp = new ArrayList<Cell>();
		for(Cell c : sudoku[q].qua)
			if(c.val==0)
				tmp.add(c);
		sudoku[q].freeL = tmp;	
		return tmp;
	}
	public ArrayList<Integer> combC(int x,int y)
	{
		Cell q = findCell(x, y);
		//q.printC();
		for(int i  : trovaRiga(x))
			if(!q.combC.contains(i))
				q.combC.add(i);
		for(int i  : trovaColonna(y))
			if(!q.combC.contains(i))
				q.combC.add(i);
		
		for(int i : combQ(q.quadrante))
			if(!q.combC.contains(i))
				q.combC.add(i);
			
		return q.combC;
	}
	private ArrayList<Integer> combina(int q,int iter[] )
	{
	//	for(int i = 0 ;i<iter.length;i++)
		//	System.out.println(iter[i]);
		ArrayList<Integer> cQ= combQ(q);

		ArrayList<Integer> sol = findMax(iter,q);

	//	System.out.println("SOL"+sol.toString());
		//System.out.println("Q"+cQ.toString());

		for(int i : cQ)
			if(sol.contains(i))
				sol.remove(sol.indexOf(i));
		return sol;
		
	}
	public Quadrante findQuad(int x,int y)
	{
		if(x<3)
		{
			if(y<3)
				return sudoku[0];
			if(y>=3 && y<6)
				return sudoku[3];
			if(y>=6)
				return sudoku[6];
		}
		if(x>=3 && x<6)
		{
			if(y<3)
				return sudoku[1];
			if(y>=3 && y<6)
				return sudoku[4];
			if(y>=6)
				return sudoku[7];
		}
		if(x>=6)
		{
			if(y<3)
				return sudoku[2];
			if(y>=3 && y<6)
				return sudoku[5];
			if(y>=6)
				return sudoku[8];
		}
	
		return null;
		
	}
	public Cell findCell(int x, int y) 
	{
		
		Quadrante q= findQuad(x,y);
		for(Cell c: q.qua)
			if(c.x==x && c.y==y)
					return c;				
	
		return null;
	}
	public void risolvi()
	{
		ArrayList<Integer> v = new ArrayList<Integer>();
		v.add(0); v.add(1);v.add(2); v.add(3);v.add(4); 
		v.add(5);v.add(6);v.add(7); v.add(8);
		int x = 0;
		while(!v.isEmpty())
			{
			//for (int i =0 ;i<sudoku.length;i++)
			for (int i : v)
				{
					findSol(i);
					x++;
				}
				
			v= checkQ(v);
			printSudoku();
			}
		
		System.out.println("RIPETIZIONI: "+x);
		
	}
	
	private ArrayList<Integer> checkQ(ArrayList<Integer> v)
	{
		ArrayList<Integer> tmp = new ArrayList<Integer>(v);
		for(int i : v)
			if(sudoku[i].complete())
				tmp.remove(tmp.indexOf(i));
		return tmp;
	}
	public boolean findSol(int q)
	{	

		ArrayList<Integer> ris = new ArrayList<Integer>();
		int iter[] = new int[10];
		
		ArrayList<Cell> free = freeL(q);
	//	System.out.println("\n\n------------------");
		for(Cell check : free)
		{
			check.printAllInfo();
			combC(check.x,check.y);
			
		//	System.out.println("combinazione cella: "+combC(check.x,check.y).toString());
			if(combC(check.x,check.y).size() == 8)
			{
					int u = mancante(combC(check.x,check.y));
					System.out.println("Scrivo: "+u+" --("+check.x+","+check.y+")");
					tabellone[check.y][check.x] =check.val = u;

			}
			if(checkRiga(check).size()==1)
			{
				//if(checkCinQ(checkRiga(check).get(0),q))
				//{
					int u = checkRiga(check).get(0);
					System.out.println("CHECK Scrivo: "+u+" --("+check.x+","+check.y+")");
					
					tabellone[check.y][check.x] =check.val = u;
				//}
				
			}
		/*	if(combQ(q).size()==8)
			{
				int u= mancante(combQ(q));
				System.out.println(u+" --("+check.x+","+check.y+")  q:"+check.quadrante+" ");
				sudoku[q].print();
				check.val = tabellone[check.y][check.x]= u;
				
				
			}*/
			for (int i : combC(check.x,check.y))
					iter[i]= iter[i]+1;
		}
		
		ris = combina(q,iter);
		Collections.sort(combQ(q));
		System.out.println(combQ(q).toString());
		System.out.println("RIS "+ris.toString());
		if(ris.isEmpty())
			return false;
	//	System.out.println("MAX:"+findMax(iter,q).toString());
		
		writeSol(ris,free,q);
		
		printSudoku();
		return true;
	}
	
	private boolean checkCinQ(int v, int q)
	{
		for(int i = 0 ;i<sudoku[q].qua.length;i++)
			if(sudoku[q].qua[i].val==v)
				return false;
		return true;
	}
	public ArrayList<Integer> checkRiga(Cell check)
	{
		ArrayList<Cell> tmp = new ArrayList<Cell>();
		ArrayList<Integer> ris = new ArrayList<Integer>();

		for(int i = 0;i<tabellone[check.y].length;i++)
		{
			Cell p = findCell(i,check.y);
			if(tabellone[check.y][i]==0 && p.quadrante!=check.quadrante)
				{
					tmp.add(p);
					System.out.print("CHECK: ");
					p.printAllInfo();
				}
			
		}
				
		
	/*	for(int i : combinazione)
		{
			
		}*/
		for(int i : check.manc)
		{
			for(Cell c : tmp)
			{
				if(c.manc.contains(i))
					continue;
				else
					{
						if(!ris.contains(i))
							{
								ris.add(i);
								System.out.println("RIS__CHECK: "+ris.toString());
								if(check.y==8 && check.x==5)
									System.exit(0);
							}
					}		
			}
		}
		System.out.println("CHECK"+ris.toString());
		
		return ris;
	}
	public int mancante(ArrayList<Integer> check)
	{
		for(int i :combinazione)
			if(!check.contains(i))
				return i;
		return 0;
	}
	private void writeSol(ArrayList<Integer> ris, ArrayList<Cell> free, int q)
	{
		ArrayList<Integer>  tmp = checkval(ris,free,q);
		//System.out.println(tmp.equals(ris));
	//	System.out.println("Ora controllo i valori: "+tmp.toString());
		for(Cell check : free)
		{
			freeL(q);
			for (int i : tmp)//ris)
			{ 
				if(!combC(check.x,check.y).contains(i) && !combQ(q).contains(i))
					{			
					System.out.println("Scrivo: "+check.val+" --("+check.x+","+check.y+")");

						check.val = i;
						tabellone[check.y][check.x] = i;
						freeL(q);
						
					}
			}
			
		}
	}
	
	private ArrayList<Integer> checkval(ArrayList<Integer> ris, ArrayList<Cell> free, int q)
	{
		ArrayList<Integer> tmp = new ArrayList <Integer>(ris);
	/*	for(Cell check : free)
		{
			freeL(q);
			for (int i : ris)
			{ 
				int x =0;
				if(!combC(check.x,check.y).contains(i))
					x++;
				if (x>1)
					{
						System.out.println("OKOK");
						tmp.remove(tmp.indexOf(i));
					}
			}
			
			
		}*/
		
		for (int i : ris)
		{ int x = 0;
			for(Cell check : free )
			{
				
				if(!tmp.isEmpty())
					{
				//System.out.print("POINT:");
				//	check.printC();
					//System.out.println("VAL:" +i);
					//System.out.println(combC(check.x,check.y).toString());
					if(!combC(check.x,check.y).contains(i))
						{
						//	System.out.println("trovato!");
							x++;
						}
				//	System.out.println("x: "+x);
					if (x>1)
						{
					//		System.out.println("OKOK"+ tmp.indexOf(i));
						
								System.out.println(i);
								System.out.println(tmp.toString()+"------"+tmp.indexOf(i));
								if(tmp.size()==1)
									tmp.remove(0);

								else
									tmp.remove(tmp.indexOf(i));
						}
					}
				
			}
		
		}
		return tmp;
	}
	private ArrayList<Integer>findMax(int iter[],int q)
	{
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		int max = 0;
		for(int i = 0 ;i<iter.length;i++)
			if(iter[i]>max)
				max = iter[i];
		
		int max2 =0;
		for(int i = 0 ;i<iter.length;i++)
			if(iter[i]>max2 && iter[i]!=max)
				max2 = iter[i];
	/*	if (max2!=freeL(q).size()-1)
			return tmp;*/
		if(max-1!=max2)
			return tmp;
		for(int i = 1;i<iter.length;i++)
			if(iter[i]==max2)
				tmp.add(i);
		
		return tmp;
	}
	/*
	private ArrayList<Integer>  findList(ArrayList<Integer> check, ArrayList<Integer> fin)
	{
		ArrayList<Integer>  tmp = new ArrayList<Integer> ();
		for(int i = 0 ; i<check.size();i++)
			if(fin.contains(check.get(i)))
				tmp.add(check.get(i));
		return tmp;
	}*/
	public ArrayList<Integer> trovaRiga(int x)
	{
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		for(int i = 0;i<tabellone[x].length;i++)
			if(tabellone[i][x]!=0)
				tmp.add(tabellone[i][x]);
		
		return tmp;
	}
	public ArrayList<Integer> trovaColonna(int x)
	{
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		for(int i = 0;i<tabellone[x].length;i++)
			if(tabellone[x][i]!=0)
				tmp.add(tabellone[x][i]);
		
		return tmp;
	}
	
	public void tabellone()
	{
		for(int i = 0; i<tabellone.length;i++)
			for(int j = 0;j<tabellone.length;j++)
					findCell(j,i).printC();
	}
	public	void leggiSudoku(String nomeFile) throws IOException
	{
		
			BufferedReader in = new BufferedReader(new FileReader(nomeFile));
			String linea = in.readLine();
			String[] dati = linea.split(",");
			int index = 0;
			while (linea != null && index < tabellone.length) {
				dati = linea.split(" ");				
				for(int i = 0 ;i<dati.length;i++)
							tabellone[index][i]=Integer.parseInt(dati[i]);
				index ++;
				linea = in.readLine();
			}
			in.close();
		
	}
}
