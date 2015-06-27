package iscte.ipm.ipm;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Jorge on 24-05-2015.
 */
public class BackGround {
    private Bitmap image;
    private int x, y, dx;

    public BackGround (Bitmap res){
        image= res;

    }

    public void update(){
        x+=dx;
        if(x<-GamePanel.WIDTH){
            x=0;
        }
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, x, y,null);
        if(x<0){
            canvas.drawBitmap(image, x+GamePanel.WIDTH, y, null);
        }
    }

}
