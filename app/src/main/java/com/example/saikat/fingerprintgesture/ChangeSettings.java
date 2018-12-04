package com.example.saikat.fingerprintgesture;

import android.accessibilityservice.FingerprintGestureController;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class ChangeSettings extends AppCompatActivity {

    FingerprintGestureController fingerprintGestureController;

    public void setFingerprintGestureController(FingerprintGestureController fingerprintGestureController) {
        this.fingerprintGestureController = fingerprintGestureController;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_settings);
        getIntent();


        Switch notificationSwitch = findViewById(R.id.notification);
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){

                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

                        Toast.makeText(ChangeSettings.this, "Oreo", Toast.LENGTH_SHORT).show();

                    } else{
                        Toast.makeText(ChangeSettings.this, "Less than Oreo", Toast.LENGTH_SHORT).show();
                    }



//                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//
//                        try {
//                            if (fingerprintGestureController.isGestureDetectionAvailable()== true){
//                                Toast.makeText(ChangeSettings.this, "Turned On", Toast.LENGTH_SHORT).show();
//                            }else{
//                                Toast.makeText(ChangeSettings.this, "Not available", Toast.LENGTH_SHORT).show();
//                            }
//
//                        }catch (Exception e){
//                            Toast.makeText(ChangeSettings.this, "Error"+e.toString(), Toast.LENGTH_SHORT).show();
//                            Log.i("Errpr"," "+e.toString());
//                        }
//
//                    } else{
//                        // do something for phones running an SDK before lollipop
//                    }



                }else {
                    Toast.makeText(ChangeSettings.this, "Off", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
