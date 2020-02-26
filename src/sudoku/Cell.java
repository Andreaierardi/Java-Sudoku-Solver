package sudoku;

import java.util.ArrayList;
import java.util.Collections;

public class Cell {
		
	public int quadrante;
	public ArrayList<Integer> combC;
	public ArrayList<Integer> manc;

	public int val;
	public int x;
	public int y;
	
	public Cell(int v,int x1,int y1,int q)
	{
		combC = new ArrayList<Integer>();
		manc = new ArrayList<Integer>();

		for(int i = 1;i<10;i++)
			manc.add(i);
		val = v;
		x = x1;
		y = y1;
		quadrante = q;
		
		
	}
	public Cell(int x,int y, int quadrante)
	{
		this(0,x,y,quadrante);
	}
	public Cell(int v, int quadrante)
	{
		this(v,0,0,quadrante);
	}
	public Cell()
	{
		this(0,0);
	}
	public void setC(int x2, int y2, int val2, int index) 
	{
		x=x2;
		y= y2;
		val = val2;
		quadrante = index;
	}
	public void printC()
	{
		System.out.println("valore:["+val+"]"+" ("+x+","+y+")"+" - quadrante: "+quadrante );
	}
	private void mancanti()
	{
		ArrayList<Integer> tmp = new ArrayList<Integer>(manc);

		for(int i : tmp)
			if(combC.contains(i))
				manc.remove(manc.indexOf(i));
		Collections.sort(manc);
		Collections.sort(combC);


	}
	public void printAllInfo()
	{
		mancanti();

		System.out.println("valore:["+val+"]"+" ("+x+","+y+")"+" - quadrante: "+quadrante +"     M:"+manc.toString()+"      I:"+combC);

	}
}
