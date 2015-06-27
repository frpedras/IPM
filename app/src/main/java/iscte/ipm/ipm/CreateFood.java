package iscte.ipm.ipm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Filipe on 26/06/2015.
 */
public class CreateFood extends Activity {

    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    Spinner dropdown;
    ArrayList<typeOfFood> types = new ArrayList<>();
    typeOfFood item_selected;
    ImageView imgView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_food);
        dropdown = (Spinner)findViewById(R.id.types);

        for (typeOfFood t : typeOfFood.values()){
            types.add(t);
        }

        updateAdapter();

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item_selected = (typeOfFood) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }


    private void updateAdapter() {
        typeOfFood [] aux = new typeOfFood[types.size()];
        types.toArray(aux);
        ArrayAdapter<typeOfFood> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, aux);
        dropdown.setAdapter(adapter);
    }

    public void getImage(View v){
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();

                imgView = (ImageView) findViewById(R.id.IVgetImage);

                // Set the Image in ImageView after decoding the String
                Bitmap resized = Bitmap.createScaledBitmap(BitmapFactory
                        .decodeFile(imgDecodableString), 100, 100, true);
                imgView.setImageBitmap(resized);

            } else {
                Toast.makeText(this, "Não escolheu uma imagem",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Algo está errado!", Toast.LENGTH_LONG)
                    .show();
        }


    }

    private boolean saveFood() {
        try {

            EditText aux = (EditText) findViewById(R.id.ETtempoecra);
            long viewtime = Integer.parseInt((aux.getText().toString().replaceAll("[^\\d]", "")));
            aux = (EditText) findViewById(R.id.ETnrstops);
            long stopnumber = (long) Integer.parseInt(aux.getText().toString().replaceAll("[^\\d]", ""));
            aux = (EditText) findViewById(R.id.ETocorrencias);
            long number = (long) Integer.parseInt(aux.getText().toString().replaceAll("[^\\d]", ""));
            aux = (EditText) findViewById(R.id.ETtempostop);
            long stoptime = (long) Integer.parseInt(aux.getText().toString().replaceAll("[^\\d]", ""));
            RadioButton aux2 = (RadioButton) findViewById(R.id.RBnaosaudavel);
            boolean bad = aux2.isChecked();


            aux = (EditText) findViewById(R.id.ETnome);
            String nome = aux.getText().toString();

            Bitmap bitmap = ((BitmapDrawable) imgView.getDrawable()).getBitmap();

            //new Food(nome, item_selected, bitmap, bad, viewtime, stoptime, number, stopnumber);
            return true;
        } catch (Exception e) {
            AlertDialog.Builder alert = new AlertDialog.Builder(CreateFood.this);
            alert.setTitle("Atenção");
            alert.setMessage("Ocorreu um erro ao tentar guardar as alterações, tente de novo por favor.");
            alert.setPositiveButton("OK", null);
            alert.show();
            return false;
        }
    }

    public void save(View v){
        if (saveFood()) {
            Intent i = new Intent(CreateFood.this, ParamFood.class);
            startActivity(i);
        }
    }
}
