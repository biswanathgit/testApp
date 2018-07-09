package biswanath.com.myapplication.test;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import biswanath.com.myapplication.R;

public class ToolbarActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_SMS = 0;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Button btnSend;

    MyBroadCast myBroadCast;
    LocalBroadcastManager localBroadcastManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);
        btnSend = findViewById(R.id.btn_send);
        setupToolBar();


        requestPermission();

        myBroadCast = new MyBroadCast();
        localBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setAction("custom_broad");
                //sendBroadcast(intent);
                localBroadcastManager.sendBroadcast(intent);
            }
        });


    }
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "recived", Toast.LENGTH_SHORT).show();
        }
    };

    public void requestPermission() {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECEIVE_SMS)) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();

            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(ToolbarActivity.this, new String[]{Manifest.permission.RECEIVE_SMS}, PERMISSION_REQUEST_SMS);

                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // BEGIN_INCLUDE(onRequestPermissionsResult)
        if (requestCode == PERMISSION_REQUEST_SMS) {
            // Request for camera permission.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "onRequestPermissionsResult granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "onRequestPermissionsResult denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setupToolBar() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        IntentFilter intentFilter = new IntentFilter("custom_broad");
        //registerReceiver(myBroadCast,intentFilter);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,intentFilter);
    }
    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
}
