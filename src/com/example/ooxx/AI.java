package com.example.ooxx;

import java.util.ArrayList;

public class AI {
	char color;
	char []table=new char[9];
	Node head=new Node(table);
	AI(CellBoard tmp,char Co){
		color=Co;
		for (int i=0;i<9;i++){
			//table[i]= tmp.showPresent(i);
		}
	}
	public String getAIstate(){
		String tmp="";
		for (int i=0;i<9;i++){
			if(table[i]==' ') tmp+=' ';
			else tmp+=table[i];
		}
		return tmp;
	}
	class Node{
		
		int score=0;
		char []table=new char[9];
		ArrayList<Node> child = new ArrayList<Node>(); 
		
		Node(char[] str){
			
		}
	}
	private int CaculateScore(){
		//if()
		return 0;
	}
	public void CreateTree(){
		
	}
}
