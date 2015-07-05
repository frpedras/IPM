package iscte.ipm.ipm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.os.Vibrator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;


/**
 * Created by Jorge on 24-05-2015.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    public int WIDTH;
    public int HEIGHT;
    private MainThread thread;
    private BackGround bg;
    private LinkedList<Level> levels = new LinkedList<Level>();
    private LinkedList<FoodPoint> fpoints =new LinkedList<FoodPoint>();
    private Vibrator v;
    private int score,foodTotalLevel,foodTotal,acertos,acertosTotal;
    private int nLevelActual=-1;
    private long removeTime;
    private LinkedList<Food> levelActual=new LinkedList<Food>();
    private boolean exportado=false;
    private long inicialGameTime,deltaTimeTotal;



    private MediaPlayer mp;


    public GamePanel(Context context) {
        super(context);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        WIDTH = metrics.widthPixels;
        HEIGHT = metrics.heightPixels;
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        mp=  MediaPlayer.create(context, R.raw.glass_ping);
        inicialGameTime= System.currentTimeMillis();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        String bgNumber, bgPath;
        try {
            bgNumber = sharedPref.getString("bgNumber", null);
            bgNumber.toString();
        } catch (Exception e) {bgNumber="1";}

        try {
            bgPath = sharedPref.getString("bg", null);
        } catch (Exception e) {bgPath="";}

        if (bgNumber.equals("1"))
            bg = new BackGround(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_white),WIDTH,HEIGHT,true));
        else if (bgNumber.equals("2"))
            bg = new BackGround(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_wood01),WIDTH,HEIGHT,true));
        else if (bgNumber.equals("3"))
            bg = new BackGround(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.black),WIDTH,HEIGHT,true));
        else if (bgNumber.equals("4")) {
            try {
                bg = new BackGround( Bitmap.createScaledBitmap(BitmapFactory.decodeFile(bgPath),1000,1000,true));
            }catch (Exception e){
                bg = new BackGround(BitmapFactory.decodeResource(getResources(), R.drawable.bg_white));
            }
        }

        Level level0 = new Level(this,2000,100,0);
        level0.makeTutorial();
        levels.add(level0);

        int tempPAcertos=50;
        for(int i=0;i<5;i++){
            Level level1 = new Level(this,1000,tempPAcertos,(int)((double)tempPAcertos*1.2));
            level1.makeAlevel();
            levels.add(level1);
            tempPAcertos+=10;
            if(tempPAcertos>100)
                tempPAcertos=100;
        }

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        event.setLocation(event.getX(),event.getY());

        if ((nLevelActual+1<levels.size()) && levelActual.isEmpty()) {
            if(nLevelActual==0 && levels.get(0).getPAcertos()>(int)(100*((double)acertos/(double) foodTotalLevel))) {
                levels.get(0).reset();
                nLevelActual--;
                score=0;
            }

            if(nLevelActual>0 && levels.get(nLevelActual).getPAcertos()>(int)(100*((double)acertos/(double) foodTotalLevel))){
                nLevelActual=levels.size();
            }

            if(nLevelActual+1<levels.size()) {
                nLevelActual++;
                actualizaLevelActual(levels.get(nLevelActual));
                foodTotal += foodTotalLevel;
                acertosTotal += acertos;
                foodTotalLevel = 0;
                acertos = 0;
            }
        }

        if ((nLevelActual<levels.size()) && !levelActual.isEmpty() && levelActual.getFirst().getRectangle().contains((int) event.getX(), (int) event.getY())) {

            if (levelActual.getFirst().isBad() && levelActual.getFirst().getFt()!=FoodType.SCORE) {
                levelActual.removeFirst();
                removeTime=System.currentTimeMillis();
                foodTotalLevel++;
                v.vibrate(400);
                //menos pontos
                score-=5;
                fpoints.add(new FoodPoint(true, event.getX(), event.getY(), this));
            }
            else if(levelActual.getFirst().getFt()!=FoodType.SCORE) {
                levelActual.removeFirst();
                removeTime=System.currentTimeMillis();
                foodTotalLevel++;

                mp.start();
                //mais pontos
                score+=5;
                acertos++;
                fpoints.add(new FoodPoint(false, event.getX(), event.getY(), this));
            }
        }

        return super.onTouchEvent(event);
    }

    public void update() {

        if ((nLevelActual<levels.size()) && !levelActual.isEmpty() && (levelActual.getFirst().getX() > WIDTH || levelActual.getFirst().getY() > HEIGHT || levelActual.getFirst().getX() < -levelActual.getFirst().getWidth() || levelActual.getFirst().getY() < -levelActual.getFirst().getHeight())) {
            if (!levelActual.getFirst().isBad()) {
                v.vibrate(400);
                score-=5;
                fpoints.add(new FoodPoint(true, levelActual.getFirst().getX() - 100, levelActual.getFirst().getY(), this));
            }
            else{
                score+=5;
                acertos++;
                mp.start();
                fpoints.add(new FoodPoint(false, levelActual.getFirst().getX()-100, levelActual.getFirst().getY(), this));

            }
            deltaTimeTotal+=(System.currentTimeMillis()-removeTime);
            removeTime=System.currentTimeMillis();

            levelActual.removeFirst();
            foodTotalLevel++;
        }
        if ((nLevelActual<levels.size()) && !levelActual.isEmpty() && System.currentTimeMillis()>(removeTime + levels.get(nLevelActual).getGoTime()))
            levelActual.getFirst().update();



        if(!fpoints.isEmpty()){
            for(FoodPoint fp: fpoints) {
                if (fp.getAlive() == false && fp.getLaucheTime() != 0) {
                    fpoints.remove(fp);
                } else
                    fp.update();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {

        Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        final float scaleFactorX = getWidth() / (WIDTH * 1.f);
        final float scaleFactorY = getHeight() / (HEIGHT * 1.f);
        if (canvas != null) {

            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            if((nLevelActual<levels.size()) && !levelActual.isEmpty()) {
                if (levels.get(nLevelActual).isTutorial()) {
                    Bitmap bt = null;
                    if (!levelActual.getFirst().isBad()) {
                        bt = (BitmapFactory.decodeResource(this.getResources(), R.drawable.tas01));
                    } else {
                        bt = (BitmapFactory.decodeResource(this.getResources(), R.drawable.ntas01));
                    }
                    canvas.drawBitmap(bt, -3, HEIGHT / 8, null);
                }
                if (System.currentTimeMillis() > (removeTime + levels.get(nLevelActual).getGoTime())) {
                    levelActual.getFirst().draw(canvas);
                }
            }
            if(!fpoints.isEmpty()) {
                for (FoodPoint fp : fpoints)
                    fp.draw(canvas);
            }
            else if(levelActual.isEmpty() ) {
                endLevel(canvas);
            }


            paint.setColor(Color.BLACK);
            paint.setTextSize(30);
            paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);
            canvas.drawText("Pontos de saúde: " + score, 20, 40, paint);


            canvas.restoreToCount(savedState);
        }
    }

    public void endLevel(Canvas canvas) {

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPaint(paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(60);

        if(nLevelActual==-1){
            canvas.drawText("Bem vindo!", 110, ((HEIGHT / 2) - 100), paint);
            paint.setTextSize(50);
            paint.setColor(Color.DKGRAY);
            canvas.drawText("Toque para começar", 30, ((HEIGHT / 2) + 100), paint);
        }
        else if((nLevelActual<levels.size()-1)&& levelActual.isEmpty()){
            if(levels.get(nLevelActual).getPAcertos() <=(int)(100*((double)acertos/(double) foodTotalLevel))) {
                canvas.drawText("Nível Completo", 65, ((HEIGHT / 2) - 100), paint);
                paint.setColor(Color.GREEN);
            }else {
                canvas.drawText("Nível Falhado", 75, ((HEIGHT / 2) - 100), paint);
                paint.setColor(Color.RED);
            }
            canvas.drawText("Acertos: " + (int)(100*((double)acertos/(double) foodTotalLevel))+"%", 100, ((HEIGHT / 2) + 50), paint);
            paint.setTextSize(50);
            paint.setColor(Color.DKGRAY);
            canvas.drawText("Toque para continuar", 25, ((HEIGHT / 2) + 200), paint);
        }else {
            canvas.drawText("Fim do Jogo!", 90, ((HEIGHT / 2) - 100), paint);
            canvas.drawText("Pontos: " + score , 110, ((HEIGHT / 2) + 50), paint);
        }

    }

    private void actualizaLevelActual(Level l){
        for(GameObject f: l.getThrowableFood()){
            levelActual.add((Food)f);
        }
    }



    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }
}
