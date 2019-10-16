package com.example.fernandoapp.ui.mapa;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.fernandoapp.R;
import com.example.fernandoapp.data.dao.UsuarioDAO;
import com.example.fernandoapp.data.model.Usuario;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.SphericalUtil;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        OnMapClickListener,
        OnMarkerClickListener,
        GoogleMap.OnPolylineClickListener {

    private static final String TAG = MapsActivity.class.getSimpleName();
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;
    private boolean mLocationPermissionGranted;
    private boolean isSalvandoCasa;
    private TextView mTapTextView;

    private Marker mCasa, pontoA, pontoB;
    private boolean isCriandoLinha;
    private boolean isCriandoPercurso;
    private Usuario usuario;
    private Location oldLocation;
    private Polyline percursoLinha;
    LatLng lat;
    List<LatLng> points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        isSalvandoCasa = false;

        mTapTextView = (TextView) findViewById(R.id.tapText);
        Intent i = getIntent();
        usuario = i.getParcelableExtra("usuario");
        Log.i("usr","no Mapa id é "+usuario.getId());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnPolylineClickListener(this);

        getLocationPermission();

        updateLocationUI();

        getDeviceLocation();


        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

    public void getNomeMarcacao() {
        getDeviceLocation();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nova marcação atual");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Salvar", (dialog, which) -> {
            LatLng pos = new LatLng(mLastKnownLocation.getLatitude(),
                    mLastKnownLocation.getLongitude());

            mMap.addMarker(new MarkerOptions().position(pos).title(input.getText().toString()));
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void mapaButton1Action(View view) {
        getNomeMarcacao();
    }

    public void mapaButton2Action(View view) {
        Toast.makeText(this, "Clique no mapa para salvar a posição da sua casa.", Toast.LENGTH_LONG).show();

        isSalvandoCasa = true;
        if (mCasa != null)
            mCasa.remove();
    }

    public void mapaButton3Action(View view) {
        isCriandoLinha = true;
        Toast.makeText(this, "Clique no mapa para adicionar os pontos A e B para medição.", Toast.LENGTH_LONG).show();

    }

    public void mapaButton4Action(View view) {
        isCriandoPercurso = true;
        getDeviceLocation();
        oldLocation = mLastKnownLocation;
        percursoLinha = mMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(new LatLng(oldLocation.getLatitude(), oldLocation.getLongitude())));
        Toast.makeText(this, "Iniciando Percurso.", Toast.LENGTH_LONG).show();

        new Thread() {
            public void run() {
                while (isCriandoPercurso) {
                    try {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                getDeviceLocation();
                                if (oldLocation.getLatitude() != mLastKnownLocation.getLatitude() || oldLocation.getLongitude() != mLastKnownLocation.getLongitude()) {
                                    List<LatLng> points = percursoLinha.getPoints();
                                    lat = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                                    points.add(lat);
                                    percursoLinha.setPoints(points);
                                    oldLocation = mLastKnownLocation;
                                }
                            }
                        });
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }

    public void mapaButton5Action(View view) {
        isCriandoPercurso = false;
        Toast.makeText(this, "Fim de  Percurso.", Toast.LENGTH_LONG).show();
        usuario.setPercurso(percursoLinha.getPoints());
        UsuarioDAO usuarioDAO = new UsuarioDAO(getApplicationContext());
        usuarioDAO.updateUsuario(usuario);
        usuarioDAO.getPercurso(usuario);
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    /**
     * Prompts the user for permission to use the device location.
     */
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
                updateLocationUI();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    @Override
    public void onMapClick(LatLng point) {
        if (isSalvandoCasa) {
            mCasa = mMap.addMarker(new MarkerOptions().position(point).title("Casa"));
            isSalvandoCasa = false;
        } else if (isCriandoLinha) {

            if (pontoA == null)
                pontoA = mMap.addMarker(new MarkerOptions().position(point).title("Ponto A"));
            else
                pontoB = mMap.addMarker(new MarkerOptions().position(point).title("Ponto B"));

            if (pontoA != null && pontoB != null) {
                Polyline linha = mMap.addPolyline(new PolylineOptions()
                        .clickable(true)
                        .add(pontoA.getPosition(),
                                pontoB.getPosition()
                        ));
                double dist = SphericalUtil.computeLength(linha.getPoints());
                double dist_round = (double) Math.round(dist * 100) / 100;
                linha.setTag(dist_round);

                isCriandoLinha = false;
                pontoA.remove();
                pontoB.remove();
                pontoA = null;
                pontoB = null;
            }
        } else
            mTapTextView.setText("tapped, point=" + point);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        // Markers have a z-index that is settable and gettable.

        Toast.makeText(this, "clicou no mark " + marker.getTitle(),
                Toast.LENGTH_SHORT).show();

        // We return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }

    @Override
    public void onPolylineClick(Polyline polyline) {

        Toast.makeText(this, "Distância: " + polyline.getTag().toString() + " metros",
                Toast.LENGTH_SHORT).show();
    }

}
