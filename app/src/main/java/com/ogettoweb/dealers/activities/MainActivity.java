package com.ogettoweb.dealers.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ogettoweb.dealers.R;
import com.ogettoweb.dealers.databinding.ActivityMainBinding;
import com.ogettoweb.dealers.fragments.CartFragment;
import com.ogettoweb.dealers.fragments.HomeFragment;
import com.ogettoweb.dealers.handlers.MainActivityHandlers;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;

    public ActivityMainBinding getBinding() {
        return binding;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        //binding.toolbar.setNavigationOnClickListener(this);

        pushFragment(HomeFragment.newInstance(), false, HomeFragment.class.toString());

        binding.setClick(new MainActivityHandlers() {
            @Override
            public void onHomeClick(View view) {
                pushFragment(HomeFragment.newInstance(), false, HomeFragment.class.toString());
            }

            @Override
            public void onCartClick(View view) {
                pushFragment(CartFragment.newInstance(), false, CartFragment.class.toString());
            }

            @Override
            public void onMenuClick(View view) {
                Toast.makeText(MainActivity.this, "Menu click", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search: {
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                break;
            }
            case android.R.id.home: {
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                if (!fragmentManager.popBackStackImmediate()) {
                    finish();
                }
                break;
            }
            default: {
                throw new IllegalArgumentException("No handler for this menu item");
            }
        }
        return true;
    }

}
