package com.example.ooxx;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;


@SuppressLint("DrawAllocation")
public class MainBoard extends View{
	private int h,w;
	private Bitmap bitmap;
	private Canvas buffer;
	private Paint paint;
	private Point[] hLine01,hLine02,vLine01,vLine02;
	private CellBoard cellBoard;
	private Game game=new Game();
	private AI ai;
	
	public MainBoard (Context context){
		super(context);
		paint= new Paint();
	}
	public void Restart(){
		//cellBoard.Restart();
		game.GameInitial();
		buffer.drawColor(0,Mode.CLEAR);
		drawBoard();
	}
	@Override
	protected void onDraw(Canvas canvas){
		canvas.drawBitmap(bitmap, 0, 0 ,paint);
	}
	@SuppressLint("DrawAllocation")
	@Override
	protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
		h=View.MeasureSpec.getSize(heightMeasureSpec);
		w=View.MeasureSpec.getSize(widthMeasureSpec);
		setMeasuredDimension(w,h);
		bitmap=Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		buffer=new Canvas(bitmap);
		
		calculateLinePlacements();
		drawBoard();
	}
	private void drawBoard(){
		paint.setStrokeWidth(20);
		paint.setColor(Color.WHITE);
		buffer.drawPaint(paint);
		paint.setColor(Color.BLACK);
		buffer.drawLine(hLine01[0].x, hLine01[0].y, hLine01[1].x,hLine01[1].y, paint);
		buffer.drawLine(hLine02[0].x, hLine02[0].y, hLine02[1].x,hLine02[1].y, paint);
		buffer.drawLine(vLine01[0].x, vLine01[0].y, vLine01[1].x,vLine01[1].y, paint);
		buffer.drawLine(vLine02[0].x, vLine02[0].y, vLine02[1].x,vLine02[1].y, paint);
		paint.setColor(Color.RED);
		paint.setStyle(Paint.Style.FILL);
		paint.setAntiAlias(true);
		paint.setTextSize(100);
		paint.setTextSkewX((float) -0.5);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		buffer.drawText("Restart",w/2+80,h-80,paint);
		paint.setStrokeWidth(20);
	}
	private void calculateLinePlacements(){
		//int cellH=h/3;
		int cellW=w/3;
		int cellH=cellW+50;
		hLine01=new Point[2];
		Point p1 =new Point(0,cellH);
		Point p2=new Point(w,cellH);
		hLine01[0]=p1;
		hLine01[1]=p2;
		hLine02=new Point[2];
		p1=new Point(0,2*cellH);
		p2=new Point(w,2*cellH);
		hLine02[0]=p1;
		hLine02[1]=p2;
		vLine01=new Point[2];
		p1=new Point(cellW,0);
		p2=new Point(cellW,cellH*3);
		vLine01[0]=p1;
		vLine01[1]=p2;
		vLine02=new Point[2];
		p1=new Point(2*cellW,0);
		p2=new Point(2*cellW,cellH*3);
		vLine02[0]=p1;
		vLine02[1]=p2;
		
		cellBoard=new CellBoard(cellW,cellH,game);
	}
	
	public boolean TouchFunc (MotionEvent event,float a,float b){
		//float x=event.getX();
		//float y=event.getY();
		ai=new AI(cellBoard,'O');
		a=event.getX();
		b=event.getY();
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			
			
			int a1=(int)a,b1=(int)b;
			if(a1>w/2+70&&a1<w/2+450&&b1>h-200&&b1<h-50){
				Restart();
				//buffer.drawText("Success", 0, h-30, paint);
			}
			
			if(!game.isGameOver()){
				RectF position=cellBoard.getCellToFill(a, b);
				if(position!=null){
					char present=game.getPresent(cellBoard.getCellInd(a,b));
					if(present=='X'){
						paint.setColor(Color.BLUE);
						buffer.drawLine(position.left, position.top, position.right, position.bottom, paint);
						buffer.drawLine(position.right, position.top, position.left, position.bottom, paint);
					}
					else if (present=='O'){
						paint.setColor(Color.RED);
						paint.setStyle(Paint.Style.STROKE);
						buffer.drawOval(position, paint);
					}
					
				}
			}

		}
		char []tmp=new char[9];
		for (int i=0;i<9;i++){
			tmp[i]=game.getPresent(i);
		}
		if(game.isGameOver()){
			float x1=cellBoard.getPositionX(game.getWinIndex(0));
			float x2=cellBoard.getPositionX(game.getWinIndex(1));
			float y1=cellBoard.getPositionY(game.getWinIndex(0));
			float y2=cellBoard.getPositionY(game.getWinIndex(1));
			
			paint.setColor(Color.GREEN);
			paint.setStyle(Paint.Style.STROKE);
			
			if(game.winner()!=' '){
				paint.setStrokeWidth(50);
				buffer.drawLine(x1,y1,x2,y2, paint);
				paint.setStrokeWidth(5);
				buffer.drawText("win"+game.winner(), 80, h-200,paint);
				//invalidate();
			}
		}
		//AI turn............
		else {
			ai=new AI(cellBoard,'O');
		}
		invalidate();
		return true;
	}
	public char printWin() {
		// TODO Auto-generated method stub
		return game.winner();
	}

}


