package com.example.sketcher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WatchDrawingView extends View {
    private final DatabaseReference pathRef;
    private Path drawPath;
    private Paint drawPaint, canvasPaint;
    private static int paintColor;
    private static float paintSize;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;
    FirebaseDatabase database = FirebaseDatabase.getInstance();


    public WatchDrawingView(Context context, AttributeSet attrs){
        super(context, attrs);
        setupDrawing();
        this.setBackgroundColor(getColor(R.color.colorWhite));
        pathRef = database.getReference("DrawPaths");

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        System.out.println("IS CALLED?");
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawBitmap(canvasBitmap,0,0, canvasPaint);
        canvas.drawPath(drawPath,drawPaint);
    }
    public void drawWithPath(float xCord, float yCord, String moveOrLine, String drawingOrNot){
        if(moveOrLine.equals("MOVE")){
            drawPath.moveTo(xCord,yCord);
        }
        if(moveOrLine.equals("LINE")){
            drawPath.lineTo(xCord,yCord);
        }
        if(drawingOrNot.equals("FALSE")){
            drawCanvas.drawPath(drawPath, drawPaint);
            drawPath.reset();
        }

    }

    public void changePaintSize(float size){
        this.paintSize = size;
        drawPaint.setStrokeWidth(paintSize);
    }

    public void changePaintColor(int colorID){
        this.paintColor = getColor(colorID);
        drawPaint.setColor(paintColor);
    }

    public void setupDrawing(){
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(0);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(15);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }



    public void startNew(){
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }

    public Bitmap getBitmap(){
        return canvasBitmap;
    }


    /**
     * This function gets color with getResource on R.id.color_name
     * @param colorID
     * @return color value (integer)
     */
    public int getColor(int colorID){
        return ContextCompat.getColor(getContext(), colorID);
    }
}