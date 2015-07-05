package iscte.ipm.ipm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Jorge on 19-06-2015.
 */
public class FoodPoint extends GameObject {

    private Bitmap image;
    private GamePanel gp;

    private long viewtime=750;

    public FoodPoint (boolean bad, double x, double y, GamePanel gp) {
        this.gp = gp;
        this.ft=FoodType.SCORE;
        this.x = x;
        this.y = y;
        this.dy=-7;
        this.bad=bad;
        if (bad)
            image=(BitmapFactory.decodeResource(gp.getResources(), R.drawable.pontmenos));
        else
            image=(BitmapFactory.decodeResource(gp.getResources(), R.drawable.pontmais));

        this.height = image.getHeight();
        this.width = image.getWidth();
    }

    @Override
    public void update() {
        if (!alive && laucheTime==0) {
            laucheTime=System.currentTimeMillis();
            alive = true;
        }
        y += dy;
        dy+=0.2;
        if(laucheTime+viewtime<System.currentTimeMillis())
            alive=false;
    }

    @Override
    public void draw(Canvas canvas) {

        canvas.drawBitmap(image, (int)x, (int)y, null);

    }

    @Override
    public void reporXDxy(){
        this.dx=(int)(((gp.getWIDTH()+width)/((viewtime/1000)*29)));
        this.dy=-9.8*((viewtime/1000)/2);
    }
}
