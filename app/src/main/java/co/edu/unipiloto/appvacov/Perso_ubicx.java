package co.edu.unipiloto.appvacov;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Perso_ubicx extends AppCompatActivity {

    Button button;
    TextView text_view1,text_view2,text_view3,text_view4,text_view5,location_view;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perso_ubicx);

        button = findViewById(R.id.button);
        text_view1 = findViewById(R.id.text_viewIN1);
        text_view2 = findViewById(R.id.text_viewIN2);
        text_view3 = findViewById(R.id.text_viewIN3);
        text_view4 = findViewById(R.id.text_viewIN4);
        text_view5 = findViewById(R.id.text_viewIN5);
        location_view = findViewById(R.id.location_view);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location_view.setText("Check permission");
                if(ActivityCompat.checkSelfPermission(Perso_ubicx.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                    location_view.setText("when permission granted");
                    getLocation();
                }else{
                    location_view.setText("when permission denied");
                    ActivityCompat.requestPermissions(Perso_ubicx.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                }
            }
        });
    }
    public void getLocation(){
        if(ActivityCompat.checkSelfPermission(Perso_ubicx.this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null){
                        Geocoder geocoder =new Geocoder(Perso_ubicx.this, Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            text_view1.setText("Latitud: "+(double) addresses.get(0).getLatitude());
                            text_view2.setText("Longitud: "+(double) addresses.get(0).getLongitude());
                            text_view3.setText(addresses.get(0).getCountryName());
                            text_view4.setText(addresses.get(0).getLocality());
                            text_view5.setText(addresses.get(0).getAddressLine(0));

                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        if(ActivityCompat.checkSelfPermission(Perso_ubicx.this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                            getLocation();
                        }
                    }
                }
            });
        }
    }

}

