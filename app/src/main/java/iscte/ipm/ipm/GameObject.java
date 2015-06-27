package iscte.ipm.ipm;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Jorge on 25-05-2015.
 *
 *  ep4 ate aos 6 min
 */




public abstract class GameObject {
    protected double x;
    protected double y;
    protected double dy;
    protected double dx;
    protected int width;
    protected int height;
    protected boolean bad;
    protected FoodType ft;
    protected boolean alive = false;
    protected long laucheTime,viewtime;

    public abstract void update();

    public abstract void draw(Canvas canvas);

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public Rect getRectangle() {
        return new Rect((int) x, (int) y, (int) x + width, (int) y + height);
    }

    public boolean isBad() {
        return bad;
    }

    public FoodType getFt() {
        return ft;
    }

    public boolean getAlive() {
        return alive;
    }

    public long getLaucheTime(){
        return laucheTime;
    }

    public abstract void reporXDxy();

    public void setViewtime(long l){
        this.viewtime=l;
    }

    public long getViewtime(){
        return viewtime;
    }

    public void setBad(boolean bad) {
        this.bad = bad;
    }
}
