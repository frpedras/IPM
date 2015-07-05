package iscte.ipm.ipm;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Jorge on 24-05-2015.
 */
public class BackGround {
    private Bitmap image;

    public BackGround (Bitmap res){
        image= res;

    }


    public void draw(Canvas canvas){
        canvas.drawBitmap(image, 0, 0,null);
    }

}
