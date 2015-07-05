package iscte.ipm.ipm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Jorge on 25-05-2015.
 */
public class Food extends GameObject {

    private Bitmap image;
    private Bitmap stopImage;
    private String name;
    private typeOfFood type;
    private int number, stoptime, stopnumber;
    private GamePanel gp;

    private long stopTime=600; //milis


    private long viewtime=3000;  //milis



    public Food(Bitmap image, FoodType ft,GamePanel gp, boolean bad) {
        this.gp = gp;
        this.ft = ft;
        this.x = -image.getWidth();
        this.height = image.getHeight();
        this.width = image.getWidth();
        this.image = image;
        this.bad=bad;
        if (bad)
            stopImage=(BitmapFactory.decodeResource(gp.getResources(), R.drawable.stopimage2));

        this.dx=(int)(((gp.getWIDTH()+width)/((viewtime/1000)*29)));
        this.dy=-9.8*((viewtime/1000)/2);

    }

    public Food(String name, typeOfFood type,Bitmap image, boolean bad, int viewtime, int stoptime, int stopnumber, int number) {
        this.name = name;
        this.type = type;
        this.image = image;
        this.bad = bad;
        setViewtime(viewtime);
        this.stoptime = stoptime;
        this.number = number;
        this.stopnumber = stopnumber;
    }


    @Override
    public void update() {
        if (!alive) {
            laucheTime=System.currentTimeMillis();
            alive = true;
        }
        x += dx;
        double rAltura =Math.random()*(gp.getWIDTH()-y);
        y += dy;
        //dy+=(9.8)/45;
        dy+=(0.0000725*viewtime);


    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, (int)x, (int)y, null);

        if(bad &&(laucheTime+stopTime)<System.currentTimeMillis())
        canvas.drawBitmap(stopImage, (int)(x+width-(width/8)),(int) y, null);

    }

    @Override
    public void reporXDxy(){
        this.x = -image.getWidth();
        this.dx=(int)(((gp.getWIDTH()+width)/((viewtime/1000)*29)));
        this.dy=-9.8*((viewtime/1000)/2);
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public typeOfFood getType() {
        return type;
    }

    public void setType(typeOfFood type) {
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getStoptime() {
        return stoptime;
    }

    public void setStoptime(int stoptime) {
        this.stoptime = stoptime;
    }

    public int getStopnumber() {
        return stopnumber;
    }

    public void setStopnumber(int stopnumber) {
        this.stopnumber = stopnumber;
    }

    public long getStopTime() {
        return stopTime;
    }

    public void setStopTime(long stopTime) {
        this.stopTime = stopTime;
    }
}
