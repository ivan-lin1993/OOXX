package com.example.ooxx;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

public class MainBoard extends View{
	private int h,w;
	private Bitmap bitmap;
	private Canvas buffer;
	private Paint paint;
	private Point[] hLine01,hLine02,vLine01,vLine02;
	private CellBoard cellBoard;
	
	
	
	
	public MainBoard (Context context){
		super(context);
		paint= new Paint();
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(20);
	}
	@Override
	protected void onDraw(Canvas canvas){
		canvas.drawBitmap(bitmap, 0, 0 ,paint);
	}
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
		buffer.drawLine(hLine01[0].x, hLine01[0].y, hLine01[1].x,hLine01[1].y, paint);
		buffer.drawLine(hLine02[0].x, hLine02[0].y, hLine02[1].x,hLine02[1].y, paint);
		buffer.drawLine(vLine01[0].x, vLine01[0].y, vLine01[1].x,vLine01[1].y, paint);
		buffer.drawLine(vLine02[0].x, vLine02[0].y, vLine02[1].x,vLine02[1].y, paint);
	}
	private void calculateLinePlacements(){
		int cellH=h/3;
		int cellW=w/3;
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
		p2=new Point(cellW,h);
		vLine01[0]=p1;
		vLine01[1]=p2;
		vLine02=new Point[2];
		p1=new Point(2*cellW,0);
		p2=new Point(2*cellW,h);
		vLine02[0]=p1;
		vLine02[1]=p2;
		cellBoard=new CellBoard(cellW,cellH);
	}
	@Override
	public boolean onTouchEvent (MotionEvent event){
		float x=event.getX();
		float y=event.getY();
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			RectF position=cellBoard.getCellToFill(x, y);
			int present=cellBoard.getCellPresent(x,y);
			if(position!=null){
				if(present==1){
					buffer.drawLine(position.left, position.top, position.right, position.bottom, paint);
					buffer.drawLine(position.right, position.top, position.left, position.bottom, paint);
				}
				else if (present==2){
					buffer.drawOval(position, paint);
				}
				invalidate();
				}
			}
		if(cellBoard.isOver()){
			
			Dialog d=new Dialog(null);
			//System.exit(0);
		}
		return true;
	}

}


