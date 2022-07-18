package dream.guys.hotdeskandroid.example;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import java.util.List;

import dream.guys.hotdeskandroid.model.request.Point;

public class MyCanvasDraw extends View {
//Super
    //http://android-er.blogspot.com/2011/08/drawpath-on-canvas.html

    List<Point> pointList;
    Canvas canvas;


    public MyCanvasDraw(Context context, List<Point> pointList) {
        super(context);
        this.pointList=pointList;
        System.out.println("OnDrawConstructorCalled");

        //drawUsingMethod();

    }

    private void drawUsingMethod() {
        this.canvas=canvas;

        System.out.println("DrawUsingMethodCalled");

        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setPathEffect(new DashPathEffect(new float[] {20,20}, 0));
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path();



        //moves to the particular position
        path.moveTo(pointList.get(0).getX(), pointList.get(0).getY());

        for (int i = 1; i < pointList.size(); i++){

            //starts from where it ends
            path.lineTo(pointList.get(i).getX(), pointList.get(i).getY());

        }

        canvas.drawPath(path, paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        this.canvas=canvas;

        System.out.println("onDrawCalled");
        setDrawMethod();

        //DashedLine
        /*Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(new DashPathEffect(new float[] {20,20}, 0));
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        canvas.drawPath(line, paint);*/


        /*Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setPathEffect(new DashPathEffect(new float[] {20,20}, 0));
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path();



        //moves to the particular position
        path.moveTo(pointList.get(0).getX(), pointList.get(0).getY());

        for (int i = 1; i < pointList.size(); i++){

            //starts from where it ends
            path.lineTo(pointList.get(i).getX(), pointList.get(i).getY());

        }
        path.lineTo(pointList.get(0).getX(), pointList.get(0).getY());

        canvas.drawPath(path, paint);*/






    }

    public void setDrawMethod() {
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setPathEffect(new DashPathEffect(new float[] {20,20}, 0));
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path();



        //moves to the particular position
        path.moveTo(pointList.get(0).getX(), pointList.get(0).getY());

        for (int i = 1; i < pointList.size(); i++){

            //starts from where it ends
            path.lineTo(pointList.get(i).getX(), pointList.get(i).getY());

        }
        path.lineTo(pointList.get(0).getX(), pointList.get(0).getY());

        canvas.drawPath(path, paint);
    }

    public void  setInvalidate(){
        System.out.println("CanvasInvalidateCalled");
        invalidate();
        postInvalidate();
    }


    /*@Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        System.out.println("DrawCAlledEveryTime");
    }*/

    /*public void drawHere(){

        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setPathEffect(new DashPathEffect(new float[] {20,20}, 0));
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path();



        //moves to the particular position
        path.moveTo(pointList.get(0).getX(), pointList.get(0).getY());

        for (int i = 1; i < pointList.size(); i++){

            //starts from where it ends
            path.lineTo(pointList.get(i).getX(), pointList.get(i).getY());

        }

        this.canvas.drawPath(path, paint);
    }*/



}

    /*public void setDeskInActivity(){

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.desk_avaliable);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bm,500,500,null);


        *//* int x = getWidth();
        int y = getHeight();

        Paint paintTopLeft = new Paint();
        paintTopLeft.setStyle(Paint.Style.FILL);
        paintTopLeft.setColor(Color.WHITE);
        //canvas.drawPaint(paintTopLeft);  // don't do that
        // Use Color.parseColor to define HTML colors
        paintTopLeft.setColor(Color.parseColor("#F44336"));
       // canvas.drawRect(0,0,x / 2,y / 2,paintTopLeft);

        Paint paintTopRight = new Paint();
        paintTopRight.setStyle(Paint.Style.FILL);
        paintTopRight.setColor(Color.WHITE);
        // canvas.drawPaint(paintTopRight);  // don't do that
        // Use Color.parseColor to define HTML colors
        paintTopRight.setColor(Color.parseColor("#2196F3"));
        //canvas.drawRect(x / 2, 0, x, y / 2, paintTopRight);*//*

    }*/



      /*  rect=new Rect(0,100,200,300);
        paint=new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(40);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rect,paint);*/



    /*    canvas.save();
        Bitmap bm= BitmapFactory.decodeResource(getResources(), R.drawable.desk_avaliable);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bm,i,j,paint);
        canvas.restore();
        canvas.save();

        Bitmap bm1 = BitmapFactory.decodeResource(getResources(), R.drawable.desk_avaliable);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bm1,300,400,null);
        canvas.restore();
*/




//canvas.save();




        /*Bitmap bm1 = BitmapFactory.decodeResource(getResources(), R.drawable.desk_avaliable);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bm1,i,j,null);*/

/*
        for (int i = 0; i <5 ; i++) {
            if(i==1) {
                Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.desk_avaliable);
                canvas.drawColor(Color.WHITE);
                canvas.drawBitmap(bm, 100, 200, paintTopLeft);


            }

            if(i==2){
                Bitmap bm2 = BitmapFactory.decodeResource(getResources(), R.drawable.desk_avaliable);
                canvas.drawColor(Color.WHITE);
                canvas.drawBitmap(bm2, 0, 10, paintTopRight);
            }
        }*/


//canvas.setBitmap(bm);

// canvas.drawBitmap(bm,0,0);