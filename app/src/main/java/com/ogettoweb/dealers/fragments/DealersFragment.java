package com.ogettoweb.dealers.fragments;

import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ogettoweb.dealers.R;
import com.ogettoweb.dealers.adapters.DealersAdapter;
import com.ogettoweb.dealers.databinding.FragmentDealersBinding;
import com.ogettoweb.dealers.handlers.DealersFragmentHandlers;
import com.ogettoweb.dealers.models.Dealer;
import com.ogettoweb.dealers.utils.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DealersFragment extends BaseFragment {
    private static final String EXTRA_DEALERS = "extra_dealers";
    private static final String EXTRA_LOCATION = "extra_location";

    private FragmentDealersBinding binding;
    private List<Dealer> dealers;
    private boolean alphabet = false;
    private boolean distance = false;
    private Location myLocation;

    public List<Dealer> getDealers() {
        return dealers;
    }

    public static DealersFragment newInstance(List<Dealer> dealers, Location location) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(EXTRA_DEALERS, (ArrayList<? extends Parcelable>) dealers);
        args.putParcelable(EXTRA_LOCATION, location);
        DealersFragment fragment = new DealersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dealers, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey(EXTRA_DEALERS)) {
                dealers = bundle.getParcelableArrayList(EXTRA_DEALERS);
            }
            if (bundle.containsKey(EXTRA_LOCATION)) {
                myLocation = bundle.getParcelable(EXTRA_LOCATION);
            }
        }

        final DealersAdapter adapter = new DealersAdapter(dealers);
        binding.list.setAdapter(adapter);
        binding.list.addItemDecoration(new DividerItemDecoration(getContext(), null));
        binding.list.setLayoutManager(new LinearLayoutManager(getContext()));

        title = getString(R.string.on_map);

        binding.setClick(new DealersFragmentHandlers() {
            @Override
            public void onAlphabetClick(View view) {
                sortByAlphabet();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onDistanceClick(View view) {
                sortByDistance();
                adapter.notifyDataSetChanged();
            }
        });

        return binding.getRoot();
    }

    private void sortByAlphabet() {
        binding.alphabet.setTypeface(null, Typeface.BOLD_ITALIC);
        binding.distance.setTypeface(null, Typeface.ITALIC);
        binding.distanceArrow.setVisibility(View.INVISIBLE);
        binding.alphabetArrow.setVisibility(View.VISIBLE);
        if (dealers.size() > 0) {
            if (alphabet) {
                Collections.sort(dealers, new Comparator<Dealer>() {
                    @Override
                    public int compare(final Dealer object1, final Dealer object2) {
                        return object1.getCompany().compareTo(object2.getCompany());
                    }
                });
                binding.alphabetArrow.setImageResource(R.drawable.ic_up);
                alphabet = false;
            } else {
                Collections.sort(dealers, new Comparator<Dealer>() {
                    @Override
                    public int compare(final Dealer object1, final Dealer object2) {
                        return object2.getCompany().compareTo(object1.getCompany());
                    }
                });
                binding.alphabetArrow.setImageResource(R.drawable.ic_down);
                alphabet = true;
            }
        }
    }

    private void sortByDistance() {
        binding.distance.setTypeface(null, Typeface.BOLD_ITALIC);
        binding.alphabet.setTypeface(null, Typeface.ITALIC);
        binding.alphabetArrow.setVisibility(View.INVISIBLE);
        binding.distanceArrow.setVisibility(View.VISIBLE);
        if (dealers.size() > 0) {
            if (distance) {
                Collections.sort(dealers, new Comparator<Dealer>() {
                    @Override
                    public int compare(final Dealer object1, final Dealer object2) {
                        Location locationA = new Location("point A");
                        locationA.setLatitude(object1.getLatitude());
                        locationA.setLongitude(object1.getLongitude());
                        Location locationB = new Location("point B");
                        locationB.setLatitude(object2.getLatitude());
                        locationB.setLongitude(object2.getLongitude());
                        float distanceToA = myLocation.distanceTo(locationA);
                        float distanceToB = myLocation.distanceTo(locationB);
                        if (distanceToA > distanceToB) {
                            return 1;
                        } else if (distanceToA < distanceToB) {
                            return -1;
                        }
                        return 0;
                    }
                });
                binding.distanceArrow.setImageResource(R.drawable.ic_up);
                distance = false;
            } else {
                Collections.sort(dealers, new Comparator<Dealer>() {
                    @Override
                    public int compare(final Dealer object1, final Dealer object2) {
                        Location locationA = new Location("point A");
                        locationA.setLatitude(object1.getLatitude());
                        locationA.setLongitude(object1.getLongitude());
                        Location locationB = new Location("point B");
                        locationB.setLatitude(object2.getLatitude());
                        locationB.setLongitude(object2.getLongitude());
                        float distanceToA = myLocation.distanceTo(locationA);
                        float distanceToB = myLocation.distanceTo(locationB);
                        if (distanceToA < distanceToB) {
                            return 1;
                        } else if (distanceToA > distanceToB) {
                            return -1;
                        }
                        return 0;
                    }
                });
                binding.distanceArrow.setImageResource(R.drawable.ic_down);
                distance = true;
            }
        }
    }
}
