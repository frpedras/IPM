package iscte.ipm.ipm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by Filipe on 26/06/2015.
 */
public class ParamLevel extends Activity{

    Spinner dropdown;
    int cont = 1;
    ArrayList<String> stringOfLevels = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_param_level);

        dropdown = (Spinner)findViewById(R.id.levels);
        stringOfLevels.add("1");
        stringOfLevels.add("2");
        stringOfLevels.add("3");

        updateAdapter();


        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                cont = Integer.parseInt(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }


    private void updateAdapter() {
        String [] aux = new String[stringOfLevels.size()];
        stringOfLevels.toArray(aux);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, aux);
        dropdown.setAdapter(adapter);
    }

    public void addLevel(View v){
        int aux = dropdown.getChildCount();
        stringOfLevels.add(Integer.parseInt(stringOfLevels.get(stringOfLevels.size()-1))+1 + "");
        updateAdapter();
        dropdown.setSelection(stringOfLevels.size()-1);
    }

    public void remLevel(View v) {
        int here = dropdown.getSelectedItemPosition();

        if (stringOfLevels.size()>1) {
            stringOfLevels.remove(here);

            for (int x = here; x < stringOfLevels.size(); x++)
                stringOfLevels.set(x, Integer.parseInt(stringOfLevels.get(x)) - 1 + "");

            updateAdapter();

            if (here < stringOfLevels.size())
                dropdown.setSelection(here);
            else
                dropdown.setSelection(stringOfLevels.size() - 1);
        }
        else{
            AlertDialog.Builder alert = new AlertDialog.Builder(ParamLevel.this);
            alert.setTitle("Atenção");
            alert.setMessage("Não pode apagar o unico nível existente!");
            alert.setPositiveButton("OK", null);
            alert.show();
        }
    }

    public void save(View v){
            Intent i = new Intent(ParamLevel.this, ConfigurationsPsi.class);
            startActivity(i);
    }
}
