package com.hunterit.APMRabbit;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Handler;
import android.test.ApplicationTestCase;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import com.o3dr.android.client.ControlTower;
import com.o3dr.android.client.Drone;
import com.o3dr.android.client.interfaces.DroneListener;
import com.o3dr.android.client.interfaces.TowerListener;
import com.o3dr.services.android.lib.coordinate.LatLong;
import com.o3dr.services.android.lib.coordinate.LatLongAlt;
import com.o3dr.services.android.lib.drone.attribute.AttributeType;
import com.o3dr.services.android.lib.drone.connection.ConnectionParameter;
import com.o3dr.services.android.lib.drone.connection.ConnectionResult;

import com.o3dr.services.android.lib.drone.attribute.AttributeEvent;
import com.o3dr.services.android.lib.drone.connection.ConnectionType;
import com.o3dr.services.android.lib.drone.property.Altitude;
import com.o3dr.services.android.lib.drone.property.Gps;
import com.o3dr.services.android.lib.drone.property.Battery;
import com.o3dr.services.android.lib.drone.property.Home;
import com.o3dr.services.android.lib.drone.property.Speed;
import com.o3dr.services.android.lib.drone.property.State;
import com.o3dr.services.android.lib.drone.property.Type;
import com.o3dr.services.android.lib.drone.property.VehicleMode;

import java.util.List;


public class Rover extends Fragment implements DroneListener, TowerListener {

    private Drone drone;
    private int droneType = Type.TYPE_UNKNOWN;
    private ControlTower controlTower;
    private final Handler handler = new Handler();

    private final int DEFAULT_UDP_PORT = 14550;
    private final int DEFAULT_USB_BAUD_RATE = 57600;

    View rootView;

    Spinner modeSelector;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_main, container, false);

        final Context context = getActivity().getApplicationContext();
        this.controlTower = new ControlTower(context);
        this.drone = new Drone();

        this.modeSelector = (Spinner)rootView.findViewById(R.id.modeSelect);
        this.modeSelector.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onFlightModeSelected(view);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        Button button= (Button) rootView.findViewById(R.id.btnConnect);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnConnectTap();
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.controlTower.connect(this);
        updateVehicleModesForType(this.droneType);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (this.drone.isConnected()) {
            this.drone.disconnect();
            updateConnectedButton(false);
        }
        this.controlTower.unregisterDrone(this.drone);
        this.controlTower.disconnect();
    }

    // 3DR Services Listener
    // ==========================================================

    @Override
    public void onTowerConnected() {
        this.controlTower.registerDrone(this.drone, this.handler);
        this.drone.registerDroneListener(this);
    }

    @Override
    public void onTowerDisconnected() {

    }

    // Drone Listener
    // ==========================================================

    @Override
    public void onDroneEvent(String event, Bundle extras) {

        switch (event) {
            case AttributeEvent.STATE_CONNECTED:
                alertUser("Rover Connected");
                updateConnectedButton(this.drone.isConnected());

                break;

            case AttributeEvent.STATE_DISCONNECTED:
                alertUser("Rover Disconnected");
                updateConnectedButton(this.drone.isConnected());
                break;

            case AttributeEvent.STATE_UPDATED:
            case AttributeEvent.STATE_ARMING:
                break;

            case AttributeEvent.TYPE_UPDATED:
                Type newDroneType = this.drone.getAttribute(AttributeType.TYPE);
                if (newDroneType.getDroneType() != this.droneType) {
                    this.droneType = newDroneType.getDroneType();
                    updateVehicleModesForType(this.droneType);
                }
                break;

            case AttributeEvent.STATE_VEHICLE_MODE:
                updateVehicleMode();
                break;


            case AttributeEvent.SPEED_UPDATED:
                updateAltitude();
                updateSpeed();
                break;

            case AttributeEvent.HOME_UPDATED:
                updateDistanceFromHome();
                break;



            default:
//                Log.i("DRONE_EVENT", event); //Uncomment to see events from the drone
                break;
        }

    }

    @Override
    public void onDroneConnectionFailed(ConnectionResult result) {
        alertUser("Connection Failed:" + result.getErrorMessage());
    }

    @Override
    public void onDroneServiceInterrupted(String errorMsg) {

    }

    // UI Events
    // ==========================================================

    public void onBtnConnectTap() {
        if(this.drone.isConnected()) {
            this.drone.disconnect();
        } else {
            Bundle extraParams = new Bundle();
            extraParams.putInt(ConnectionType.EXTRA_USB_BAUD_RATE, DEFAULT_USB_BAUD_RATE); // Set default baud rate to 57600
            ConnectionParameter connectionParams = new ConnectionParameter(ConnectionType.TYPE_USB, extraParams, null);
            this.drone.connect(connectionParams);
        }

    }

    public void onFlightModeSelected(View view) {
        VehicleMode vehicleMode = (VehicleMode) this.modeSelector.getSelectedItem();
        this.drone.changeVehicleMode(vehicleMode);
    }


    // UI updating
    // ==========================================================

    protected void updateConnectedButton(Boolean isConnected) {
        Button connectButton = (Button)rootView.findViewById(R.id.btnConnect);
        if (isConnected) {
            connectButton.setText("Disconnect");
        } else {
            connectButton.setText("Connect");
        }
    }


    protected void updateAltitude(){
        TextView altitudeTextView = (TextView)rootView.findViewById(R.id.AltitudeValueView);
        Altitude droneAltitude = this.drone.getAttribute(AttributeType.ALTITUDE);
        altitudeTextView.setText(String.format("%3.1f", droneAltitude.getAltitude()) + "m");
    }


    protected void updateSpeed() {
        TextView speedView= (TextView)rootView.findViewById(R.id.speedValueView);
        Speed rabbitSpeed = this.drone.getAttribute(AttributeType.SPEED);
        speedView.setText(String.format("%3.1f", rabbitSpeed.getGroundSpeed()) + "m/s");
    }


    protected void updateDistanceFromHome() {
        TextView distanceTextView = (TextView)rootView.findViewById(R.id.DistanceValueView);
        TextView location = (TextView)rootView.findViewById(R.id.LocationValueView);
        Altitude droneAltitude = this.drone.getAttribute(AttributeType.ALTITUDE);
        double vehicleAltitude = droneAltitude.getAltitude();
        Gps droneGps = this.drone.getAttribute(AttributeType.GPS);
        LatLong vehiclePosition = droneGps.getPosition();

        double distanceFromHome = 0;
        double latitude = 0;
        double longitude = 0;

        if (droneGps.isValid()) {
            LatLongAlt vehicle3DPosition = new LatLongAlt(vehiclePosition.getLatitude(), vehiclePosition.getLongitude(), vehicleAltitude);
            Home droneHome = this.drone.getAttribute(AttributeType.HOME);
            distanceFromHome = distanceBetweenPoints(droneHome.getCoordinate(), vehicle3DPosition);
            latitude = vehiclePosition.getLatitude();
            longitude = vehiclePosition.getLongitude();
        } else {
            distanceFromHome = 0;
        }

        distanceTextView.setText(String.format("%3.1f", distanceFromHome) + "m");
        location.setText("(" + String.format("%3.1f", latitude) + "," + String.format("%3.1f", longitude) + ",");

    }



    protected void updateVehicleModesForType(int droneType) {

        List<VehicleMode> vehicleModes =  VehicleMode.getVehicleModePerDroneType(droneType);
        ArrayAdapter<VehicleMode> vehicleModeArrayAdapter = new ArrayAdapter<VehicleMode>(getActivity(), android.R.layout.simple_spinner_item, vehicleModes);
        vehicleModeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.modeSelector.setAdapter(vehicleModeArrayAdapter);
    }

    protected void updateVehicleMode() {
        State vehicleState = this.drone.getAttribute(AttributeType.STATE);
        VehicleMode vehicleMode = vehicleState.getVehicleMode();
        ArrayAdapter arrayAdapter = (ArrayAdapter)this.modeSelector.getAdapter();
        this.modeSelector.setSelection(arrayAdapter.getPosition(vehicleMode));
    }

    // Helper methods
    // ==========================================================

    protected void alertUser(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    protected double distanceBetweenPoints(LatLongAlt pointA, LatLongAlt pointB) {
        if (pointA == null || pointB == null) {
            return 0;
        }
        double dx = pointA.getLatitude() - pointB.getLatitude();
        double dy  = pointA.getLongitude() - pointB.getLongitude();
        double dz = pointA.getAltitude() - pointB.getAltitude();
        return Math.sqrt(dx*dx + dy*dy + dz*dz);
    }
}
