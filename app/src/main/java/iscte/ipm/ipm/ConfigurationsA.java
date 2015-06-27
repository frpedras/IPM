package iscte.ipm.ipm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

/**
 * Created by Filipe on 19/05/2015.
 */

public class ConfigurationsA extends Activity {

    SeekBar volume;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    private Button bmedium;
    private Button beasy;
    private Button bhard;
    private ImageView b1;
    private ImageView b2;
    private ImageView b3;
    private ImageView b4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurations_a);
        volume = (SeekBar)findViewById(R.id.SBvolume);
        volumeControl();


        beasy = (Button)findViewById(R.id.Beasy);
        bmedium = (Button)findViewById(R.id.Bmedium);
        bhard = (Button)findViewById(R.id.Bhard);
        bmedium.setText("Médio ✓");

        b1 = (ImageView)findViewById(R.id.IVbackground1);
        b2 = (ImageView)findViewById(R.id.IVbackground2);
        b3 = (ImageView)findViewById(R.id.IVbackground3);
        b4 = (ImageView)findViewById(R.id.IVbackground4);

        b1.setBackgroundColor(Color.GREEN);

    }

    public void setDificulty(View v){

        switch(v.getId()) {
            case R.id.Beasy:
                beasy.setText("Fácil ✓");
                bmedium.setText("Médio");
                bhard.setText("Difícil");
                break;

            case R.id.Bmedium:
                beasy.setText("Fácil");
                bmedium.setText("Médio ✓");
                bhard.setText("Difícil");
                break;

            case R.id.Bhard:
                beasy.setText("Fácil");
                bmedium.setText("Médio");
                bhard.setText("Difícil ✓");
                break;
        }
    }

    public void changeBackground(View v){
        switch(v.getId()){
            case R.id.IVbackground1:
                b1.setBackgroundColor(Color.GREEN);
                b2.setBackgroundColor(Color.GRAY);
                b3.setBackgroundColor(Color.GRAY);
                b4.setBackgroundColor(Color.GRAY);
                break;
            case R.id.IVbackground2:
                b1.setBackgroundColor(Color.GRAY);
                b2.setBackgroundColor(Color.GREEN);
                b3.setBackgroundColor(Color.GRAY);
                b4.setBackgroundColor(Color.GRAY);
                break;
            case R.id.IVbackground3:
                b1.setBackgroundColor(Color.GRAY);
                b2.setBackgroundColor(Color.GRAY);
                b3.setBackgroundColor(Color.GREEN);
                b4.setBackgroundColor(Color.GRAY);
                break;
            case R.id.IVbackground4:
                b1.setBackgroundColor(Color.GRAY);
                b2.setBackgroundColor(Color.GRAY);
                b3.setBackgroundColor(Color.GRAY);
                b4.setBackgroundColor(Color.GREEN);
                break;
        }
    }

    private void volumeControl(){

        try
        {
            final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            volume.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volume.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));


            volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void save(View v){
        if (v.getId() == R.id.Bsave){
            Intent i = new Intent(ConfigurationsA.this, MenuPrincipal.class);
            startActivity(i);
        }
    }

    public void selectFood(View v){
        if (v.getId() == R.id.Bselectfood){
            Intent i = new Intent(ConfigurationsA.this, SelectFood.class);
            startActivity(i);
        }
    }

    public void selectAlerts(View v){
        if (v.getId() == R.id.Balerts){
            Intent i = new Intent(ConfigurationsA.this, Alerts.class);
            startActivity(i);
        }
    }
    public void findImageButton(View v){
        lookForImage();
    }

    public void lookForImage(){
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
        changeBackground(findViewById(R.id.IVbackground4));
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

                ImageView imgView = (ImageView) findViewById(R.id.IVbackground4);
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

}
