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
	private boolean isover=false;

	
	public MainBoard (Context context){
		super(context);
		paint= new Paint();
	}
	public void Restart(){
		cellBoard.Restart();
		buffer.drawColor(0,Mode.CLEAR);
		drawBoard();
		isover=false;
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
		
		cellBoard=new CellBoard(cellW,cellH);
	}
	
	public boolean TouchFunc (MotionEvent event,float a,float b){
		//float x=event.getX();
		//float y=event.getY();
		a=event.getX();
		b=event.getY();
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			
			
			int a1=(int)a,b1=(int)b;
			if(a1>w/2+70&&a1<w/2+450&&b1>h-200&&b1<h-50){
				Restart();
				invalidate();
				//buffer.drawText("Success", 0, h-30, paint);
			}
			
			if(isover==false){
				RectF position=cellBoard.getCellToFill(a, b);
				if(position!=null){
					char present=cellBoard.getCellPresent(a,b);
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
					invalidate();
				}
			}

		}
		if(cellBoard.isOver()){
			isover=true;
			
			Point[] winLine=cellBoard.showWinLine();
			paint.setColor(Color.GREEN);
			paint.setStyle(Paint.Style.STROKE);
			
			if(cellBoard.winner()!=' '){
				paint.setStrokeWidth(50);
				buffer.drawLine(winLine[0].x,winLine[0].y,winLine[1].x,winLine[1].y, paint);
			}
			invalidate();
		}
		return true;
	}
	public boolean isOver(){
		
		return isover;
	}
	public char printWin() {
		// TODO Auto-generated method stub
		return cellBoard.winner();
	}

}


