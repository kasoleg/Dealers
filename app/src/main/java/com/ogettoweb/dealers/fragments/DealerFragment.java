package com.ogettoweb.dealers.fragments;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ogettoweb.dealers.R;
import com.ogettoweb.dealers.databinding.FragmentDealerBinding;
import com.ogettoweb.dealers.models.Dealer;
import com.ogettoweb.dealers.utils.PlayServicesUtils;

import java.util.ArrayList;

public class DealerFragment extends BaseFragment implements OnMapReadyCallback, DirectionCallback {
    private static final String EXTRA_DEALER = "extra_dealer";
    private static final String EXTRA_LOCATION = "extra_location";

    private FragmentDealerBinding binding;
    private Dealer dealer;
    private Location currentLocation;
    private GoogleMap googleMap;

    public static DealerFragment newInstance(Dealer dealer, Location currentLocation) {
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_DEALER, dealer);
        args.putParcelable(EXTRA_LOCATION, currentLocation);
        DealerFragment fragment = new DealerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dealer, container, false);
        title = getString(R.string.dealers_list);

        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey(EXTRA_DEALER)) {
                dealer = bundle.getParcelable(EXTRA_DEALER);
            }
            if (bundle.containsKey(EXTRA_LOCATION)) {
                currentLocation = bundle.getParcelable(EXTRA_LOCATION);
            }
        }

        binding.setDealer(dealer);

        Location dealerLocation = new Location("");
        dealerLocation.setLatitude(dealer.getLatitude());
        dealerLocation.setLongitude(dealer.getLongitude());
        binding.distance.setText(String.format("%.2f", currentLocation.distanceTo(dealerLocation) / 1000) + " км");

        if (PlayServicesUtils.checkPlayServices(getActivity())) {
            binding.map.getMapAsync(this);
            binding.map.onCreate(savedInstanceState);
            binding.map.onResume();
        }

        title = "";
        back_title = getString(R.string.dealers_list);

        return binding.getRoot();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        GoogleDirection.withServerKey("AIzaSyBwVlhb1_cVoGgVMtR6b2WE6EcFbpsumTI")
                .from(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()))
                .to(new LatLng(dealer.getLatitude(), dealer.getLongitude()))
                .execute(this);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 13));
    }


    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
        if (direction.isOK()) {
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(dealer.getLatitude(), dealer.getLongitude()))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_pin)));

            ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
            googleMap.addPolyline(DirectionConverter.createPolyline(getContext(), directionPositionList, 5, Color.RED));
        }
    }

    @Override
    public void onDirectionFailure(Throwable t) {

    }
}
