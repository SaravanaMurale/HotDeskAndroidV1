package dream.guys.hotdeskandroid.example;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

import dream.guys.hotdeskandroid.R;

public class MyCanvasDraw extends View {

    Rect rect;
    Paint paint,paint1;
    Canvas canvas;

    int i=0;
    int j=0;


    public MyCanvasDraw(Context context, int i, int i1) {
        super(context);

        this.i=i;
        this.j=i1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path path=new Path();
        paint=new Paint();


        Point a=new Point(350,50);
        Point b=new Point(350,300);
        Point c=new Point(550,60);

        //path.setFillType(null);


        path.lineTo(a.x,a.y);
        path.lineTo(b.x,b.y);
        path.lineTo(c.x,c.y);
        path.lineTo(a.x,a.y);
        path.close();

        canvas.drawPath(path,paint);




    }



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