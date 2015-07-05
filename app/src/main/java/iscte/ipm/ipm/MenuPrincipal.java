package iscte.ipm.ipm;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;


public class MenuPrincipal extends Activity {

    String nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        try {
            nome = sharedPrefs.getString("name", null);
        } catch (Exception e) { nome = "user"; }

        TextView tv = (TextView)findViewById(R.id.TVwelcome);
        tv.setText(getString(R.string.welcome) + nome);
    }

    public void changeName(View v){
        if (v.getId()==R.id.Bchangename) {
            Intent i = new Intent(MenuPrincipal.this, ChangeName.class);
            startActivity(i);
        }
    }

    public void configurations(View v){
        Intent i;
        if (v.getId()==R.id.Bconfigurations) {
            if ( nome.equals("agent"))
                i = new Intent(MenuPrincipal.this, ConfigurationsA.class);
            else if( nome.equals("psi"))
                i = new Intent(MenuPrincipal.this, ConfigurationsPsi.class);
            else
                i = new Intent(MenuPrincipal.this, ConfigurationsP.class);

            startActivity(i);
        }
    }

    public void playGame(View v){
        Intent i = new Intent(MenuPrincipal.this, Game.class);
        startActivity(i);
    }

    public void creditos(View v){
        Intent i = new Intent(MenuPrincipal.this, Creditos.class);
        startActivity(i);
    }
}
