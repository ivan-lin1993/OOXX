package com.example.ooxx;

import java.util.ArrayList;
import java.util.Random;

public class AI {
	char color;
	char []head=new char[9];
	
	AI(Game game,char Co){
		color=Co;
		for (int i=0;i<9;i++){
			head[i]= game.getPresent(i);
		}
	}
	Node root=new Node(head, 0,color);
	String teststate;
//	public String getAIstate(){
//		String tmp="";
//		for (int i=0;i<9;i++){
//			if(table[i]==' ') tmp+=' ';
//			else tmp+=table[i];
//		}
//		return tmp;
//	}
	class Node{
		
		int score;
		int dept;
		int step;
		char []table=new char[9];
		char color;
		ArrayList<Node> child = new ArrayList<Node>(); 
		
		Node(char[] str,int d,char c){
			table=str;
			dept=d;
			color=c;
			score=CaculateScore(table);
			step=-1;
		}
	}
	private int determinScore(int me,int opp){
		int tmps=0;
		if(me==3) tmps+=10;
		else if(me==2&&opp==0) tmps+=3;
		//else if (me==1&&opp==0) tmps+=1;
		else if (opp==3) tmps-=10;
		else if (me==0&&opp==2) tmps-=5;
		//else if (me==0&&opp==1) tmps-=1;
		
		return tmps;
	}
	private int CaculateScore(char []table){
		int score=0;
		for (int i=0;i<=6;i+=3){
			int me=0,opp=0;
			for(int j=i;j<i+3;j++)
			{
				if(table[j]==color)me+=1;
				else if (table[j]!=' '&&table[j]!=color) opp+=1;
			}
			score+=determinScore(me,opp);
		}
		for (int i=0;i<3;i+=1){
			
			int me=0,opp=0;
			for(int j=i;j<i+7;j+=3)
			{
				if(table[j]==color)me+=1;
				else if (table[j]!=' '&&table[j]!=color) opp+=1;
			}
			score+=determinScore(me,opp);
		}
		{
			int me=0,opp=0;
			for(int i=0;i<9;i+=4){
				if(table[i]==color)me+=1;
				else if (table[i]!=' '&&table[i]!=color) opp+=1;
			}
			score+=determinScore(me,opp);
		}
		{
			int me=0,opp=0;
			for(int i=2;i<7;i+=2){
				if(table[i]==color)me+=1;
				else if (table[i]!=' '&&table[i]!=color) opp+=1;
			}
			score+=determinScore(me,opp);
		}

		
		return score;
	}
	private boolean isOver(char []table){
		for (int i=0;i<=6;i+=3){
			if(table[i]!=' '&&table[i]==table[i+1]&&table[i+1]==table[i+2]) {
				return true;
			}
		}
		for (int i=0;i<3;i+=1){
			if(table[i]!=' '&&table[i]==table[i+3]&&table[i+3]==table[i+6]) {
				return true;
			}
		}
		if(table[0]!=' '&&table[0]==table[4]&&table[4]==table[8]) {
			return true;
		}
		else if(table[2]!=' '&&table[2]==table[4]&&table[4]==table[6]){
			return true;
		}
		return false;
	}
	public int AIthink(){
//		int for_fun=0;
//		for (int i=0;i<9;i++){
//			if(head[i]!=' '){
//				for_fun++;
//			}
//		}
//		if(for_fun<2){
//			Random ran=new Random();
//			int randplay=ran.nextInt(9);
//			while(head[randplay]!=' '){
//				randplay=ran.nextInt(9);
//			}
//			return randplay;
//		}
//		
//		
//		String tmp="";
		CreateTree(root,0,color);
		int a=minMax(root,0,color);
		int ind=0;
		for (int i=0;i<root.child.size();i++){
			if(root.child.get(i).score==a){
				ind=root.child.get(i).step;
				break;
			}
		}
		return ind;
		//TEST
		
		
	}
	private void CreateTree(Node head,int dept,char OX){
		if(dept<7||!head.child.isEmpty()){
			if(!isOver(head.table)){
				for(int i=0;i<9;i++){
					if(head.table[i]==' '){
						char []childstr=new char[9];
						for(int j=0;j<9;j++){
							childstr[j]=head.table[j];
						}
						//childstr=head.table;
						Node childnode;
						if(OX=='X'){
							childstr[i]='X';
							childnode=new Node(childstr,dept+1,'X');
							childnode.step=i;
							CreateTree(childnode,dept+1,'O');
						}
						else{
							childstr[i]='O';
							childnode=new Node(childstr,dept+1,'O');
							childnode.step=i;
							CreateTree(childnode,dept+1,'X');
						}
						
						//childnode.score=CaculateScore(childnode.table);
						head.child.add(childnode);
					}
				}
			}
		}
	}
	private int minMax(Node head,int dept,char OX){
		if(dept==7||head.child.isEmpty()){
			return head.score;
		}
		if(OX==color){
			int bestVal=-10000;
			for (int i=0;i<head.child.size();i++){
				int val=minMax(head.child.get(i),dept+1,'X');
				
				if(bestVal<val){
					bestVal=val;
					head.score=val;
				}
				//bestVal=Math.max(bestVal, val);
			}
			return bestVal;
		}
		else{
			int bestVal=10000;
			for (int i=0;i<head.child.size();i++){
				int val=minMax(head.child.get(i),dept+1,'O');
				
				if(bestVal>val){
					bestVal=val;
					head.score=val;
				}
				
				//bestVal=Math.min(bestVal,val);
			}
			return bestVal;
			
		}
		
	}
	private Node minMax2(Node head,int dept,char OX){
		if(dept==1||head.child.isEmpty()){
			return head;
		}
		Node best=new Node(null, 0, 'O');
		
		if(OX==color){
			best.score=-10000;
			for (int i=0;i<head.child.size();i++){
				int val=minMax2(head.child.get(i),dept+1,'X').score;
				
				if(best.score<val){
					best=head.child.get(i);
				}
			}
			return best;
		}
		else{
			best.score=10000;
			for (int i=0;i<head.child.size();i++){
				int val=minMax2(head.child.get(i),dept+1,'O').score;
				
				if(best.score>val){
					best=head.child.get(i);
				}
			}
			return best;
			
		}
		
	}
}
