package dream.guys.hotdeskandroid.example;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import dream.guys.hotdeskandroid.R;

public class CanvasShapeDrawActivity extends BaseClass {

    MyCanvasDraw myCanvasDraw;
    RelativeLayout relativeLayout;

    ImageView imv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_canvas_shape_draw);

        //imv=(ImageView)findViewById(R.id.imv);
        relativeLayout=(RelativeLayout)findViewById(R.id.relativeLayout) ;

        /*DrawCanvasCircle pcc = new DrawCanvasCircle (this);
        Bitmap result = Bitmap.createBitmap(25, 25, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        pcc.draw(canvas);
        pcc.setLayoutParams(new LayoutParams(25, 25));
        mControls.addView(pcc);*/

       /* LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height, weight);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(width, height, weight);*/



        MyCanvasDraw myCanvasDraw = new MyCanvasDraw (this,100,200);
        relativeLayout.addView(myCanvasDraw);
        /*MyCanvasDraw myCanvasDraw1 = new MyCanvasDraw (this,400,500);
        relativeLayout.addView(myCanvasDraw1);*/




      /*  for (int i = 0; i <7 ; i++) {
            MyCanvasDraw pcc = new MyCanvasDraw (this,100,200);
            relativeLayout.addView(pcc);
        }*/

        /**/

        /*MyCanvasDraw pcc1 = new MyCanvasDraw (this,300,400);
        //Bitmap result = Bitmap.createBitmap(25, 25, Bitmap.Config.ARGB_8888);
        // Bitmap result = BitmapFactory.decodeResource(getResources(), R.drawable.desk_avaliable);
        //Canvas canvas = new Canvas();
        //canvas.drawBitmap(result,50,100,null);
        //pcc.draw(canvas);
        //pcc.setLayoutParams(new ActionBar.LayoutParams(25, 25));
        relativeLayout.addView(pcc1);*/

    }
}