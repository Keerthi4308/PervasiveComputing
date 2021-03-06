package com.pervasive_computing.bactrackappv2;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*
  Created by Pratik on 11/06/2017.
 */

public class SplashActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        SharedPreferences.OnSharedPreferenceChangeListener {


    private static final int TIME_DELAY = 2000;
    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    private static final long UPDATE_INTERVAL = 10 * 1000;
    /**
     * The fastest rate for active location updates. Updates will never be more frequent
     * than this value, but they may be less frequent.
     */
    private static final long FASTEST_UPDATE_INTERVAL = UPDATE_INTERVAL / 2;
    /**
     * The max time before batched results are delivered by location services. Results may be
     * delivered sooner than this interval.
     */
    private static final long MAX_WAIT_TIME = UPDATE_INTERVAL * 3;
    private static long back_pressed;
    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    private LocationRequest mLocationRequest;
    /**
     * The entry point to Google Play Services.
     */
    private GoogleApiClient mGoogleApiClient;
    // UI Widgets.
    private Button mRequestUpdatesButton;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser user;
    String TAG = "Splash";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if(user == null)
        {
            startActivity(new Intent(this, LogInActivity.class));
            finish();
        }

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Logger");


        mRequestUpdatesButton = findViewById(R.id.request_updates_button);
        setContentView(R.layout.splash);
        askPermissions(0);
    }


    @Override
    protected void onStart() {
        super.onStart();
        myRef.child(user.getUid().toString()).push().setValue(new LogSnip("User is on Splash screen"));
        mRequestUpdatesButton = findViewById(R.id.request_updates_button);
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mRequestUpdatesButton = findViewById(R.id.request_updates_button);
        updateButtonsState(LocationRequestHelper.getRequesting(this));
    }

    @Override
    protected void onStop() {
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
        super.onStop();
    }

    public void get_started(View view) {
        myRef.child(user.getUid().toString()).push().setValue(new LogSnip("User starting Going Out Setup"));
        startActivity(new Intent(getApplicationContext(), AskCircles.class));
        finish();
    }

    public void skip_for_later(View view) {
        myRef.child(user.getUid().toString()).push().setValue(new LogSnip("User went straight to BAC check"));
        startActivity(new Intent(getApplicationContext(), FirstPageActivity.class));
        finish();
    }


    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        mLocationRequest.setInterval(UPDATE_INTERVAL);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // Sets the maximum time when batched location updates are delivered. Updates may be
        // delivered sooner than this interval.
        mLocationRequest.setMaxWaitTime(MAX_WAIT_TIME);
    }


    private void buildGoogleApiClient() {
        if (mGoogleApiClient != null) {
            return;
        }
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .enableAutoManage(this, this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            myRef.child(user.getUid().toString()).push().setValue(new LogSnip("User exited the app"));
            mAuth.signOut();
            finish();
            System.exit(0);
        } else {
            myRef.child(user.getUid().toString()).push().setValue(new LogSnip("User pressed back button"));
            Toast.makeText(getBaseContext(), "Press once again to exit!",
                    Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "GoogleApiClient connected");
    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(this, LocationUpdatesBroadcastReceiver.class);
        intent.setAction(LocationUpdatesBroadcastReceiver.ACTION_PROCESS_UPDATES);
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    @Override
    public void onConnectionSuspended(int i) {
        final String text = "Connection suspended";
        Log.w(TAG, text + ": Error code: " + i);
        showSnackbar("Connection suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        final String text = "Exception while connecting to Google Play services";
        Log.w(TAG, text + ": " + connectionResult.getErrorMessage());
        showSnackbar(text);
    }

    /**
     * Shows a {@link Snackbar} using {@code text}.
     *
     * @param text The Snackbar text.
     */
    private void showSnackbar(final String text) {
        View container = findViewById(R.id.activity_main);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals(LocationRequestHelper.KEY_LOCATION_UPDATES_REQUESTED)) {
            updateButtonsState(LocationRequestHelper.getRequesting(this));
        }
    }

    /**
     * Handles the Request Updates button and requests start of location updates.
     */
    public void requestLocationUpdates(View view) {
        if (isAllowed(Manifest.permission.ACCESS_FINE_LOCATION)) {
            buildGoogleApiClient();
            if (!LocationRequestHelper.getRequesting(this)) {
                try {
                    Log.i(TAG, "Starting location updates");
                    LocationRequestHelper.setRequesting(this, true);
                    LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, getPendingIntent());
                    //LocationServices.FusedLocationApi.requestLocationUpdates(
                    //        mGoogleApiClient, mLocationRequest, getPendingIntent());
                } catch (SecurityException e) {
                    LocationRequestHelper.setRequesting(this, false);
                    e.printStackTrace();
                }
            } else {
                removeLocationUpdates(view);
            }
        } else {
            askPermissions(2);
        }
    }

    /**
     * Handles the Remove Updates button, and requests removal of location updates.
     */
    public void removeLocationUpdates(View view) {
        Log.i(TAG, "Removing location updates");
        LocationRequestHelper.setRequesting(this, false);
        LocationServices.getFusedLocationProviderClient(this).removeLocationUpdates(getPendingIntent());
        //LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,
        //      getPendingIntent());
    }

    /**
     * Ensures that only one button is enabled at any time. The Start Updates button is enabled
     * if the user is not requesting location updates. The Stop Updates button is enabled if the
     * user is requesting location updates.
     */
    private void updateButtonsState(boolean requestingLocationUpdates) {

        if (requestingLocationUpdates) {
            myRef.child(user.getUid().toString()).push().setValue(new LogSnip("toggled location to OFF"));
            mRequestUpdatesButton.setEnabled(true);
            mRequestUpdatesButton.setText(R.string.turn_off);

        } else {
            myRef.child(user.getUid().toString()).push().setValue(new LogSnip("toggled location to ON"));
            mRequestUpdatesButton.setEnabled(true);
            mRequestUpdatesButton.setText(R.string.turn_on);

        }
    }

}
