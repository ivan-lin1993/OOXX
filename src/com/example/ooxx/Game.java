package com.example.ooxx;

public class Game {
	char win=' ';
	private int []winIndex=new int[2];
	boolean nowPresentX=true;
	char []table=new char[9];
	boolean gameover=false;
	
	Game(){
		GameInitial();
	}
	
	public void GameInitial(){
		win=' ';
		winIndex[0]=0;
		winIndex[1]=0;
		nowPresentX=true;
		for (int i=0;i<9;i++){
			table[i]=' ';
		}
		gameover=false;
	}

	public boolean isGameOver(){
		win=whoWin();
		if(win!=' ') {
			gameover=true;
			return true;
		}
		else if(isFull()) {
			gameover=true;
			return true;
		}
		else return false;
	}
	boolean isFull(){
		for (int i=0;i<9;i++){
			if(table[i]==' ') return false;
		}
		return true;
	}
	private char whoWin(){
		char tmp=' ';
		for (int i=0;i<7;i+=2){
			if(table[i]!=' '&&table[i]==table[i+1]&&table[i+1]==table[i+2]) {
				tmp=table[i];
				winIndex[0]=i;
				winIndex[1]=i+2;
				break;
			}
		}
		for (int i=0;i<3;i+=1){
			if(table[i]!=' '&&table[i]==table[i+3]&&table[i+3]==table[i+6]) {
				tmp=table[i];
				winIndex[0]=i;
				winIndex[1]=i+6;
				break;
			}
		}
		if(table[0]!=' '&&table[0]==table[4]&&table[4]==table[8]) {
			tmp=table[0];
			winIndex[0]=0;
			winIndex[1]=8;
		}
		else if(table[2]!=' '&&table[2]==table[4]&&table[4]==table[6]){
			tmp=table[2];
			winIndex[0]=2;
			winIndex[1]=6;
		}

		return tmp;

	}
	public void play(int ind){
		if(!gameover){
			if(table[ind]==' '){
				if(nowPresentX) table[ind]='X';
				else table[ind]='O';
				Turn();
			}
		}
	}
	private void Turn(){
		nowPresentX=!nowPresentX;
	}	
	public int getWinIndex(int n) {
		return winIndex[n];
	}
	public char getPresent(int ind){
		return table[ind];
	}
	public boolean isPresentX(){
		return nowPresentX;
	}
	public boolean isFill(int ind){
		if(table[ind]!=' ') return true;
		else return false;
	}
	public char winner(){
		return win;
	}
}
