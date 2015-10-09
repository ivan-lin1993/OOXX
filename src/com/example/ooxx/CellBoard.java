package com.example.ooxx;

import android.graphics.RectF;

public class CellBoard {
	private int w;
	private int h;
	protected Cell[] position;
	private int cell_num=9;
	private Game game=new Game();
	
	public CellBoard(int cellWidth,int cellHeight,Game g){
		w=cellWidth;
		h=cellHeight;
		initialBoardCells();
		game=g;
	}
	private class Cell extends RectF{
		private int ind;
		public Cell(float left, float top,float right,float bottom,int i){
			super(left,top,right,bottom);
			ind=i;
		}
	}
	
	private void initialBoardCells(){
		int offset=50;
		position=new Cell[cell_num];
		
		position[0]= new Cell(0+offset,		0+offset,	w-offset,	h-offset,0);
		position[1]= new Cell(w+offset,		0+offset,	2*w-offset,	h-offset,1);
		position[2]= new Cell(2*w+offset,	0+offset,	3*w-offset,	h-offset,2);
		position[3]= new Cell(0+offset,		h+offset,	w-offset,	2*h-offset,3);
		position[4]= new Cell(w+offset,		h+offset,	2*w-offset,	2*h-offset,4);
		position[5]= new Cell(2*w+offset,	h+offset,	3*w-offset,	2*h-offset,5);
		position[6]= new Cell(0+offset,		2*h+offset,	w-offset,	3*h-offset,6);
		position[7]= new Cell(w+offset,		2*h+offset,	2*w-offset,	3*h-offset,7);
		position[8]= new Cell(2*w+offset,	2*h+offset,	3*w-offset,	3*h-offset,8);
	}
	public int getCellInd(float x,float y){
		for (Cell bp: position){
			if(bp.contains(x, y)){
				return bp.ind;
			}
		}
		return 0;
	}
	public RectF getCellToFill(float x,float y){
		for (Cell bp: position){
			
			if(bp.contains(x, y)){
				RectF retCell=new RectF(bp);
				game.play(bp.ind);
				return retCell;
			}
		}
		return null;
	}
	public float getPositionX(int ind){
		return position[ind].centerX();
	}
	public float getPositionY(int ind){
		return position[ind].centerY();
	}
}
