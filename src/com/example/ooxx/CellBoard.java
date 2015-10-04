package com.example.ooxx;

import android.graphics.Point;
import android.graphics.RectF;

public class CellBoard {
	private int w;
	private int h;
	protected Cell[] position;
	protected Cell text;
	private int cell_num=9;
	private boolean nowPresentX=true;
	private char win=' ';
	private Point []winLine;
	
	public CellBoard(int cellWidth,int cellHeight){
		w=cellWidth;
		h=cellHeight;
		initialBoardCells();
		winLine=new Point[2];
		Point p1 =new Point(0,0);
		Point p2=new Point(0,0);
		winLine[0]=p1;
		winLine[1]=p2;
	}
	private class Cell extends RectF{
		private boolean filled;
		private char present;
		public Cell(float left, float top,float right,float bottom){
			super(left,top,right,bottom);
			filled=false;
			present=' ';
		}
		public void setFilled(boolean filled){
			this.filled=filled;
		}
		public boolean isFilled(){
			return filled;
		}
		public void setPresent(char c){
			present=c;
		}
		public char showPresent(){
			return present;
		}
	}
	public void Restart(){
		nowPresentX=true;
		for(int i=0;i<cell_num;i++){
			position[i].present=' ';
			position[i].setFilled(false);
		}
		win=' ';
	}
	private void initialBoardCells(){
		int offset=50;
		position=new Cell[cell_num];
		
		position[0]= new Cell(0+offset,		0+offset,	w-offset,	h-offset);
		position[1]= new Cell(w+offset,		0+offset,	2*w-offset,	h-offset);
		position[2]= new Cell(2*w+offset,	0+offset,	3*w-offset,	h-offset);
		position[3]= new Cell(0+offset,		h+offset,	w-offset,	2*h-offset);
		position[4]= new Cell(w+offset,		h+offset,	2*w-offset,	2*h-offset);
		position[5]= new Cell(2*w+offset,	h+offset,	3*w-offset,	2*h-offset);
		position[6]= new Cell(0+offset,		2*h+offset,	w-offset,	3*h-offset);
		position[7]= new Cell(w+offset,		2*h+offset,	2*w-offset,	3*h-offset);
		position[8]= new Cell(2*w+offset,	2*h+offset,	3*w-offset,	3*h-offset);
	}
	public RectF getCellToFill(float x,float y){
		for (Cell bp: position){
			if(bp.contains(x, y)&&!bp.isFilled()){
				RectF retCell=new RectF(bp);
				bp.setFilled(true);
				if(nowPresentX) bp.setPresent('X');
				else bp.setPresent('O');
				Turn();
				return retCell;
			}
		}
		return null;
	}
	public char getCellPresent(float x,float y){
		for (Cell bp: position){
			if(bp.contains(x, y)){
				return bp.showPresent();
			}
		}
		return ' ';
	}
	private void Turn(){
		nowPresentX=!nowPresentX;
	}
	public int showPresent(int ind){
		return position[ind].showPresent();
	}
	public boolean isOver(){
		if(isFull()) return true;
		int ind1=0,ind2=0;
		if(position[0].present!=' '&&position[0].present==position[1].present&&position[1].present==position[2].present) {
			win=position[0].present;
			ind1=0;
			ind2=2;
		}
		else if(position[3].present!=' '&&position[3].present==position[4].present&&position[4].present==position[5].present) {
			win=position[3].present;
			ind1=3;
			ind2=5;
		}
		else if(position[6].present!=' '&&position[6].present==position[7].present&&position[7].present==position[8].present) {
			win=position[6].present;
			ind1=6;
			ind2=8;
		}
		else if(position[0].present!=' '&&position[0].present==position[3].present&&position[3].present==position[6].present) {
			win=position[0].present;
			ind1=0;
			ind2=6;
		}
		else if(position[1].present!=' '&&position[1].present==position[4].present&&position[4].present==position[7].present) {
			win=position[1].present;
			ind1=1;
			ind2=7;
		}
		else if(position[2].present!=' '&&position[2].present==position[5].present&&position[5].present==position[8].present) {
			win=position[2].present;
			ind1=2;
			ind2=8;
		}
		else if(position[0].present!=' '&&position[0].present==position[4].present&&position[4].present==position[8].present) {
			win=position[0].present;
			ind1=0;
			ind2=8;
		}
		else if(position[2].present!=' '&&position[2].present==position[4].present&&position[4].present==position[6].present) {
			win=position[2].present;
			ind1=2;
			ind2=6;
		}
		if(win!=' '){
			winLine[0].x=(int) position[ind1].centerX();
			winLine[0].y=(int) position[ind1].centerY();
			winLine[1].x=(int) position[ind2].centerX();
			winLine[1].y=(int) position[ind2].centerY();
			return true;
		}
		else return false;
	}
	private boolean isFull() {
		// TODO Auto-generated method stub
		for (int i=0;i<cell_num;i++){
			if(position[i].present==' ') return false;
		}
		return true;
	}
	public char winner() {
		// TODO Auto-generated method stub
		
		return win;
	}
	public Point[] showWinLine(){
		return winLine;
	}
}
