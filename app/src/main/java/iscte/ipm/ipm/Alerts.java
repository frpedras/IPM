package iscte.ipm.ipm;


import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Created by Filipe on 28/05/2015.
 */

public class Alerts extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);
    }

    public void createAlert(View v){
        Intent i = new Intent(Alerts.this, CreatingAlert.class);
        startActivity(i);
    }

    public void save(View v){
        Intent i = new Intent(Alerts.this, ConfigurationsA.class);
        startActivity(i);
    }


}
