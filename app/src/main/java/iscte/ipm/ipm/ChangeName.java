package iscte.ipm.ipm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class ChangeName extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);
    }


    public void guardar(View v){
        if (v.getId()==R.id.Bguardar) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            final SharedPreferences.Editor editor = sp.edit();

            EditText savename = (EditText)findViewById(R.id.nome);
            editor.putString("name", savename.getText().toString());

            editor.commit();

            Intent i = new Intent(ChangeName.this, MenuPrincipal.class);
            startActivity(i);
        }
    }

    public void associateMail(View v){
        TextView s = (TextView)findViewById(R.id.nome);

        new AlertDialog.Builder(this)
                .setTitle("Associar e-mail")
                .setMessage("Email associado ao utilizador " + s.getText())
                .setPositiveButton(android.R.string.yes, null)
                .show();

    }


}
