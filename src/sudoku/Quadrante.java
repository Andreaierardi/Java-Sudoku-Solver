package sudoku;

import java.util.ArrayList;

public class Quadrante {
	public ArrayList<Integer> combQ;
	public ArrayList<Cell> freeL;
	public Cell[] qua;
	public int num;
	public int numCells;

	public Quadrante(int x, int...v)
	{
		num = x;
		qua=new Cell[9];
		for(int i = 0 ;i<9;i++)
		{
				qua[i]= new Cell(v[i],num);
		}
	}	
	public Quadrante(int x)
	{
		this(x, 0,0,0,0,0,0,0,0,0);
	}
	public void modCell(int quad,int index,int val,int x,int y)
	{
		qua[index].setC(x,y,val,quad);
	}
	public void print()
	{
		System.out.println();
		
		for(int i =0 ;i<qua.length;i++)
		{	
			int j = i+1;
			if(j%3==1)
				System.out.println();
			System.out.print(qua[i].val);
		}
	}
	
	public boolean complete()
	{
		for(int i = 0 ;i<qua.length;i++)
				if(qua[i].val==0)
					return false;
	
		return true;
	}
}
