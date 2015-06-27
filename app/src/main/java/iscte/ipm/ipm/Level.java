package iscte.ipm.ipm;
import android.graphics.BitmapFactory;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by Jorge on 25-05-2015.
 */
public class Level {
    private LinkedList<GameObject> throwableFood= new LinkedList<GameObject>();
    private GamePanel gp;
    private boolean tutorial=false;
    private long goTime,nerfFoodTime, nerfStopTime;
    private int pAcertos, id;

    public Level(GamePanel gp,long goTime, int pAcertos, long nerfFoodTime){
        this.gp=gp;
        this.goTime=goTime;
        this.pAcertos=pAcertos;
        this.nerfFoodTime=nerfFoodTime;
    }

    public Level(int id, long goTime, int pAcertos, long nerfFoodTime, long nerfStopTime){
        this.id = id;
        this.goTime=goTime;
        this.pAcertos=pAcertos;
        this.nerfFoodTime=nerfFoodTime;
        this.nerfStopTime=nerfStopTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void makeAlevel(){
        throwableFood.add(new Food(BitmapFactory.decodeResource(gp.getResources(), R.drawable.banana01),FoodType.FRUITS,gp,false));
        throwableFood.add(new Food(BitmapFactory.decodeResource(gp.getResources(), R.drawable.gelado01),FoodType.CAKE,gp,true));
        throwableFood.add(new Food(BitmapFactory.decodeResource(gp.getResources(), R.drawable.maca01), FoodType.FRUITS,gp,false));
        throwableFood.add(new Food(BitmapFactory.decodeResource(gp.getResources(), R.drawable.cachorro01), FoodType.FAST_FOOD,gp,true));
        throwableFood.add(new Food(BitmapFactory.decodeResource(gp.getResources(), R.drawable.hamburguer01),FoodType.FAST_FOOD,gp,false));
        throwableFood.add(new Food(BitmapFactory.decodeResource(gp.getResources(), R.drawable.batatas01), FoodType.FAST_FOOD,gp,true));
        throwableFood.add(new Food(BitmapFactory.decodeResource(gp.getResources(), R.drawable.pizza01), FoodType.FAST_FOOD,gp,true));
        throwableFood.add(new Food(BitmapFactory.decodeResource(gp.getResources(), R.drawable.pera01), FoodType.FRUITS,gp,false));
        throwableFood.add(new Food(BitmapFactory.decodeResource(gp.getResources(), R.drawable.cereja01), FoodType.FRUITS,gp,false));
        double randomHeight ;
        for(GameObject f: throwableFood) {
            randomHeight = Math.random() * (GamePanel.HEIGHT / 2) + (GamePanel.HEIGHT / 4);
            f.setY((int) randomHeight);
            f.setViewtime(f.getViewtime()-nerfFoodTime);
        }
        Collections.shuffle(throwableFood);
    }

    public void makeTutorial(){
        Food a = new Food(BitmapFactory.decodeResource(gp.getResources(), R.drawable.pera01),FoodType.FRUITS,gp,false);
        a.setY((GamePanel.HEIGHT / 2) + (GamePanel.HEIGHT / 4));
        throwableFood.add(a);
        Food b = new Food(BitmapFactory.decodeResource(gp.getResources(), R.drawable.cachorro01),FoodType.FAST_FOOD,gp,true);
        b.setY((GamePanel.HEIGHT / 2) + (GamePanel.HEIGHT / 4));
        throwableFood.add(b);
        setTutorial(true);
    }

    public LinkedList<GameObject> getThrowableFood(){
        return throwableFood;
    }

    public void setTutorial(boolean tutorial) {
        this.tutorial = tutorial;
    }

    public boolean isTutorial() {
        return tutorial;
    }

    public long getGoTime(){
        return goTime;
    }

    public int getPAcertos(){
        return pAcertos;
    }

    public void setGoTime(long goTime) {
        this.goTime = goTime;
    }

    public long getNerfFoodTime() {
        return nerfFoodTime;
    }

    public void setNerfFoodTime(long nerfFoodTime) {
        this.nerfFoodTime = nerfFoodTime;
    }

    public long getNerfStopTime() {
        return nerfStopTime;
    }

    public void setNerfStopTime(long nerfStopTime) {
        this.nerfStopTime = nerfStopTime;
    }

    public int getpAcertos() {
        return pAcertos;
    }

    public void setpAcertos(int pAcertos) {
        this.pAcertos = pAcertos;
    }

    public void reset() {

        for(GameObject f: throwableFood) {
            f.setY((GamePanel.HEIGHT / 2)+(GamePanel.HEIGHT / 4));
            f.reporXDxy();
        }
    }
}
