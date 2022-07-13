package dream.guys.hotdeskandroid.example;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.View;

public class LineView extends View {

    //https://www.youtube.com/watch?v=FsXzushEbp4&t=8s
    Paint paint=new Paint();
    PointF pointA,pointB;

    public LineView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(20);
        canvas.drawLine(pointA.x,pointA.y,pointB.x,pointB.y,paint);


    }

    public void setPointA(PointF point){
        pointA=point;

    }

    public void setPointB(PointF point){
        pointB=point;

    }

    public void draw(){
        invalidate();
        requestLayout();
    }
}
