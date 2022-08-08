package dream.guys.hotdeskandroid.example.karthickcanvascheck;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import java.util.List;

public class KarthickCanvasDraw extends View {

    Canvas canvas;
    List<KarthickPoint> pointList;

    public KarthickCanvasDraw(Context context, List<KarthickPoint> pointList) {
        super(context);
        this.pointList=pointList;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        setDrawMethod();

        System.out.println("OnDrawCalled");
    }

    public void setDrawMethod() {
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setPathEffect(new DashPathEffect(new float[]{20, 20}, 0));
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path();


        //moves to the particular position
        path.moveTo(pointList.get(0).getX(), pointList.get(0).getY());

        for (int i = 1; i < pointList.size(); i++) {

            //starts from where it ends
            path.lineTo(pointList.get(i).getX(), pointList.get(i).getY());

        }
        //path.lineTo(pointList.get(0).getX(), pointList.get(0).getY());

        canvas.drawPath(path, paint);
    }
}
