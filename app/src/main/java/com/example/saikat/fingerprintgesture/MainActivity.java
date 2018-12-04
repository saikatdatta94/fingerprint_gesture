package com.example.saikat.fingerprintgesture;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DevicePolicyManager deviceManager;
    ActivityManager activityManager;
    ComponentName compName;
    static final int RESULT_ENABLE = 1;
//    MobileAds.initialize(this,"ca-app-pub-3071217201148083/8754271156");


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //ad setting

//        MobileAds.initialize(this, "ca-app-pub-3071217201148083~1033948837");

        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adView.loadAd(adRequest);


        FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

        deviceManager = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
        activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        compName = new ComponentName(this,Darclass.class);




        //Enable Admin

        Intent intent = new Intent(DevicePolicyManager
                .ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                compName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "Additional text explaining why this needs to be added.");
        startActivityForResult(intent, RESULT_ENABLE);


        //hasd

        if (fingerprintManager.isHardwareDetected()!= true){
            ImageView lockImage = findViewById(R.id.fingerPrint_on);
            lockImage.setEnabled(false);
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
            lockImage.setColorFilter(cf);
            lockImage.setImageAlpha(128);
            TextView text = findViewById(R.id.active_state);
            text.setText("Your device doesn't support this feature");

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Warning")
                    .setMessage("Your device doesn't support fingerprint")
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .create();
            dialog.show();
        }





        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        //noinspection SimplifiableIfStatement


        switch (item.getItemId()) {
            case R.id.action_report:
                bugMailIntent();
                return true;

            case R.id.settings:
                settingsView();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.rate_us) {
            try {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("https://play.google.com/store/apps/details?id=com.innovacorp.fingerprintgesture"));
                startActivity(viewIntent);
            }catch(Exception e) {
                Toast.makeText(getApplicationContext(),"Unable to Connect Try Again...",
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        } else if (id == R.id.share_app) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Download the awesome Fingerprint Gesture App from Google Play Store: https://play.google.com/store/apps/details?id=com.innovacorp.fingerprintgesture");
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, "Share"));


        } else if (id == R.id.report_bug) {

            String[] addresses = {"developersaikatdatta94@gmail.com"};

            Intent reportIntent = new  Intent(Intent.ACTION_SENDTO);
            reportIntent.setData(Uri.parse("mailto:"));
            reportIntent.putExtra(Intent.EXTRA_EMAIL, addresses);
            reportIntent.putExtra(Intent.EXTRA_SUBJECT, "Fingerprint Gesture Bug");
            if (reportIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(reportIntent);
            }

        } else if (id == R.id.nav_share_whatsapp) {
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.whatsapp");
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Download the awesome Fingerprint Gesture App from Google Play Store: https://play.google.com/store/apps/details?id=com.innovacorp.fingerprintgesture");
            try {
                startActivity(whatsappIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "Whatsapp is not installed.", Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.facebook) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void buttonTapped(View view){
        TextView text = findViewById(R.id.active_state);

        if (detectState()==0){
            try {

                //disable sensor
                deviceManager.lockNow();
                deviceManager.setKeyguardDisabledFeatures(compName,DevicePolicyManager.KEYGUARD_DISABLE_FINGERPRINT);
                text.setText("Touch to Enable Fingerprint");

            }catch (Exception e){

                //Enable Sensor
                Toast.makeText(this,e.getMessage().toString(), Toast.LENGTH_SHORT).show();

            }
        }else {
            text.setText("Touch to Disable Fingerprint");
            deviceManager.setKeyguardDisabledFeatures(compName,DevicePolicyManager.KEYGUARD_DISABLE_FEATURES_NONE);
        }


        detectState();

    }





    public int detectState(){

        ImageView lockImage = findViewById(R.id.fingerPrint_on);

        int isEnabled =  deviceManager.getKeyguardDisabledFeatures(compName);
        if (isEnabled==0){
            lockImage.setImageResource(R.drawable.fingerprint_on);
        }else {
            lockImage.setImageResource(R.drawable.fingerprint_off);
        }

        return isEnabled;
    }

    public void bugMailIntent(){
        String[] addresses = {"developersaikatdatta94@gmail.com"};

        Intent reportIntent = new  Intent(Intent.ACTION_SENDTO);
        reportIntent.setData(Uri.parse("mailto:"));
        reportIntent.putExtra(Intent.EXTRA_EMAIL, addresses);
        reportIntent.putExtra(Intent.EXTRA_SUBJECT, "Fingerprint Gesture Issue");
        if (reportIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(reportIntent);
        }

    }

    public void settingsView(){
       Intent settingsIntent = new Intent(this,ChangeSettings.class);
       startActivity(settingsIntent);
    }



}
