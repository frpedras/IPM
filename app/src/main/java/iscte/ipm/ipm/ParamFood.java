package iscte.ipm.ipm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Filipe on 07/06/2015.
 */
public class ParamFood extends Activity{

    ArrayList<Food> foods = new ArrayList<Food>();
    ImageView before, middle, after;
    RadioButton saudavel, naosaudavel;
    EditText tempoecra, ocorrencias, tempostop, nrstops;
    TextView type;
    int selected = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_param_food);
        before = (ImageView)findViewById(R.id.IVbefore);
        middle = (ImageView)findViewById(R.id.IVmiddle);
        after = (ImageView)findViewById(R.id.IVafter);

        saudavel = (RadioButton)findViewById(R.id.RBsaudavel);
        naosaudavel = (RadioButton)findViewById(R.id.RBnaosaudavel);

        tempoecra = (EditText)findViewById(R.id.ETtempoecra);
        ocorrencias = (EditText)findViewById(R.id.ETocorrencias);
        tempostop = (EditText)findViewById(R.id.ETtempostop);
        nrstops = (EditText)findViewById(R.id.ETnrstops);

        type = (TextView)findViewById(R.id.TVtipoalimento);

        getData();

    }

    private void getData(){
        foods.add(new Food("maca", typeOfFood.FRUTA, ((BitmapDrawable)getResources().getDrawable(R.drawable.maca01)).getBitmap(), false, 3000, 0, 10, 0));
        foods.add(new Food("batata", typeOfFood.FASTFOOD, ((BitmapDrawable)getResources().getDrawable(R.drawable.batatas01)).getBitmap(), true , 3500, 1000, 10, 5));
        foods.add(new Food("cachorro", typeOfFood.FASTFOOD, ((BitmapDrawable)getResources().getDrawable(R.drawable.cachorro01)).getBitmap(), true , 4000, 1500, 5, 5));
        foods.add(new Food("cereja", typeOfFood.FRUTA, ((BitmapDrawable)getResources().getDrawable(R.drawable.cereja01)).getBitmap(), false, 6000, 0, 2, 0));
        foods.add(new Food("gelado", typeOfFood.DOCE, ((BitmapDrawable)getResources().getDrawable(R.drawable.gelado01)).getBitmap(), false, 4500, 0, 12, 0));

        setSelected(0);

    }

    private void setSelected(int selected) {
        this.selected = selected;
        if (selected!=-1) {
            Food f = foods.get(selected);
            middle.setImageBitmap(f.getImage());

            if (selected >= 1) {
                before.setVisibility(View.VISIBLE);
                before.setImageBitmap(foods.get(selected - 1).getImage());
            } else
                before.setVisibility(View.INVISIBLE);

            if (selected < foods.size() - 1) {
                after.setVisibility(View.VISIBLE);
                after.setImageBitmap(foods.get(selected + 1).getImage());
            } else
                after.setVisibility(View.INVISIBLE);

            // Radio Buttons
            if (f.isBad())
                naosaudavel.setChecked(true);
            else
                saudavel.setChecked(true);

            // Campos
            tempoecra.setText(f.getViewtime() + " ms");
            ocorrencias.setText(f.getNumber() + "");
            tempostop.setText(f.getStoptime() + " ms");
            nrstops.setText(f.getStopnumber() + "");

            // Tipo de alimento
            String typeaux = f.getType() + "";

            switch (f.getType()) {
                case FASTFOOD:
                    typeaux = "Fast-Food";
                    break;
                case FRUTA:
                    typeaux = "Fruta";
                    break;
                case VEGETAL:
                    typeaux = "Vegetal";
                    break;
                case SALGADO:
                    typeaux = "Salgado";
                    break;
                case DOCE:
                    typeaux = "Doce";
                    break;
            }
            type.setText(typeaux);
        }
        else{
            naosaudavel.setChecked(false);
            saudavel.setChecked(false);
            type.setText("-");
            tempoecra.setText("- ms");
            ocorrencias.setText("-");
            tempostop.setText("- ms");
            nrstops.setText("-");
            middle.setImageBitmap(null);
        }

    }

    public void before(View v){
        if (saveFood())
            setSelected(selected-1);
    }

    public void after(View v){
        if (saveFood())
            setSelected(selected+1);
    }

    private boolean saveFood(){
        try {
            Food f = foods.get(selected);
            EditText aux = (EditText) findViewById(R.id.ETtempoecra);
            int x = Integer.parseInt((aux.getText().toString().replaceAll("[^\\d]", "")));
            f.setViewtime(x);
            aux = (EditText) findViewById(R.id.ETnrstops);
            x = Integer.parseInt(aux.getText().toString().replaceAll("[^\\d]", ""));
            f.setStopnumber(x);
            aux = (EditText) findViewById(R.id.ETocorrencias);
            x = Integer.parseInt(aux.getText().toString().replaceAll("[^\\d]", ""));
            f.setNumber(x);
            aux = (EditText) findViewById(R.id.ETtempostop);
            x = Integer.parseInt(aux.getText().toString().replaceAll("[^\\d]", ""));
            f.setStoptime(x);
            RadioButton aux2 = (RadioButton) findViewById(R.id.RBnaosaudavel);
            f.setBad(aux2.isChecked());
            return true;
        } catch (Exception e){
            AlertDialog.Builder alert = new AlertDialog.Builder(ParamFood.this);
            alert.setTitle("Atenção");
            alert.setMessage("Ocorreu um erro ao tentar guardar as alterações, tente de novo por favor.");
            alert.setPositiveButton("OK", null);
            alert.show();
            return false;
        }
    }

    public void usarEmTodos(View v){
        if (saveFood())
            for (Food f: foods){
                f.setStopnumber(foods.get(selected).getStopnumber());
                f.setViewtime(foods.get(selected).getViewtime());
                f.setNumber(foods.get(selected).getNumber());
                f.setStoptime(foods.get(selected).getStoptime());
            }
    }

    public void usarEmTipo(View v){
        if (saveFood())
            for (Food f: foods){
                if (f.getType().equals(foods.get(selected).getType())) {
                    f.setStopnumber(foods.get(selected).getStopnumber());
                    f.setViewtime(foods.get(selected).getViewtime());
                    f.setNumber(foods.get(selected).getNumber());
                    f.setStoptime(foods.get(selected).getStoptime());
                }
            }
    }

    public void save(View v){
        if (saveFood()) {
            Intent i = new Intent(ParamFood.this, ConfigurationsPsi.class);
            startActivity(i);
        }
    }

    public void createFood(View v){
        Intent i = new Intent(ParamFood.this, CreateFood.class);
        startActivity(i);
    }

    public void remFood(View v){
        if (selected!=-1) {
            foods.remove(selected);
            if (selected < foods.size())
                setSelected(selected);
            else if (foods.size() == 0)
                setSelected(-1);
            else
                setSelected(selected - 1);
        }
    }
}
