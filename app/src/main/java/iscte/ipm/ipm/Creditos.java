package iscte.ipm.ipm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Filipe on 30/06/2015.
 */

public class Creditos extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditos);
    }

    public void voltar(View v){
        Intent i = new Intent(this, MenuPrincipal.class);
        startActivity(i);
    }
}
