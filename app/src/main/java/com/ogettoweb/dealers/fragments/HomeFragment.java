package com.ogettoweb.dealers.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ogettoweb.dealers.R;
import com.ogettoweb.dealers.activities.BaseActivity;
import com.ogettoweb.dealers.databinding.FragmentHomeBinding;
import com.ogettoweb.dealers.dialogs.DealersProgressDialog;
import com.ogettoweb.dealers.handlers.HomeFragmentHandlers;
import com.ogettoweb.dealers.models.Dealer;
import com.ogettoweb.dealers.network.DealersAsyncTask;
import com.ogettoweb.dealers.utils.PlayServicesUtils;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends BaseFragment implements LocationListener, OnMapReadyCallback {
    final int PERMISSION_LOCATION = 0;

    private FragmentHomeBinding binding;
    private LocationManager locationManager;
    private List<Dealer> dealersList;
    private GoogleMap map;
    private DealersProgressDialog progressDialog;
    private LatLng currentPosition;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        SpannableString spanString = new SpannableString(binding.dealers.getText());
        spanString.setSpan(new UnderlineSpan(), 0, spanString.length() - 2, 0);
        binding.dealers.setText(spanString);

        if (PlayServicesUtils.checkPlayServices(getActivity())) {
            binding.map.getMapAsync(this);
            binding.map.onCreate(savedInstanceState);
            binding.map.onResume();
            progressDialog = new DealersProgressDialog(getContext(), "Loading geo location...");
            progressDialog.show();
        }

        binding.setClick(new HomeFragmentHandlers() {
            @Override
            public void onDealersClick(View view) {
                Location myLocation = new Location("");
                myLocation.setLatitude(currentPosition.latitude);
                myLocation.setLongitude(currentPosition.longitude);
                ((BaseActivity) getActivity()).pushFragment(DealersFragment.newInstance(dealersList, myLocation), true, DealersFragment.class.toString());
            }
        });

        title = getString(R.string.dealers);
        back_title = "";

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

        boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!enabled) {
            new AlertDialog.Builder(getContext())
                    .setTitle(R.string.app_name)
                    .setMessage(R.string.coordinats_message)
                    .setPositiveButton(R.string.enable, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getActivity().finish();
                        }
                    }).show();
        } else {
            askForLocation();
        }
    }

    private void askForLocation() {
        if (isLocationAllowed(getContext())) {
            String provider = locationManager.getBestProvider(new Criteria(), false);
            locationManager.requestLocationUpdates(provider, 10000, 100, this);
        } else {
            requestLocationPermission();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (isLocationAllowed(getContext())) {
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (map != null) {
            progressDialog.hide();
            currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
            map.addMarker(new MarkerOptions()
                    .position(currentPosition));
            CameraPosition cameraPosition = new CameraPosition.Builder().target(currentPosition).zoom(12.5f).build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            map.animateCamera(cameraUpdate);
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(currentPosition.latitude, currentPosition.longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            binding.city.setText(addresses.get(0).getAddressLine(1));
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    private boolean isLocationAllowed(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        if (!isLocationAllowed(getContext())) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) && !ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_LOCATION: {
                if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(getActivity(), R.string.location_permission_denied, Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        new DealersAsyncTask(getActivity()) {
            @Override
            protected void onPostExecute(List<Dealer> dealers) {
                super.onPostExecute(dealers);
                dealersList = dealers;
                for (Dealer dealer : dealers) {
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(dealer.getLatitude(), dealer.getLongitude()))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin)));
                }
                map = googleMap;
            }
        }.execute();
    }

}
