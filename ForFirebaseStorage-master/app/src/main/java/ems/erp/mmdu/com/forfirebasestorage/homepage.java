package ems.erp.mmdu.com.forfirebasestorage;

import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import ems.erp.mmdu.com.forfirebasestorage.helper.BottomNavigationBehavior;
import java.util.ArrayList;
import java.util.List;

public class homepage extends AppCompatActivity {
    Fragment first,second,third,fourth;

    private int nvId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage2);

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        first = new AmmarFragment();
        second = new tusharFragment();
        third = new PageTree();
        fourth = new DevaFragment();


        // attaching bottom sheet behaviour - hide / show on scroll

        try{
            String goToThisFragment = getIntent().getExtras().getString("fragment");
            if(goToThisFragment.equals("profile")){
                loadFragment(fourth);
            }
            else if(goToThisFragment.equals("bids")){
                loadFragment(third);
            }
            else{
                loadFragment(first);
            }
        }
        catch (Exception exp){
            loadFragment(new AmmarFragment());
        }
    }

        private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bottom_ammar:
                    loadFragment(first);
                    nvId = 1;
                    return true;
                case R.id.bottom_himan:
                    loadFragment(third);
                    nvId = 0;
                    return true;
                case R.id.bottom_deva:
                    loadFragment(fourth);
                    nvId = 0;
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed()
    {
        if(nvId == 0) {
            nvId = 1;
            loadFragment(new AmmarFragment());
        }
        else{
            finish();
        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
//        SearchManager searchManager =
//                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView =
//                (SearchView) menu.findItem(R.id.search).getActionView();
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(getComponentName()));
//
//        return super.onCreateOptionsMenu(menu);
//    }


    public void mobilesCategory (View view) {

        Fragment fragment = CategoryFragment.newInstance("Mobiles");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commit();
    }

    public void computersCategory (View view) {
        Fragment fragment = CategoryFragment.newInstance("Computers");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commit();
    }

    public void electronicCategory (View view) {
        Fragment fragment = CategoryFragment.newInstance("Electronic");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commit();
    }

    public void booksCategory (View view) {
        Fragment fragment = CategoryFragment.newInstance("Books");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commit();
    }

    public void menCategory (View view) {
        Fragment fragment = CategoryFragment.newInstance("Men's Fashion");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commit();
    }

    public void womenCategory (View view) {
        Fragment fragment = CategoryFragment.newInstance("Women's Fashion");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commit();
    }

    public void sportsCategory (View view) {
        Fragment fragment = CategoryFragment.newInstance("Sports");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commit();
    }

    public void bagsCategory (View view) {
        Fragment fragment = CategoryFragment.newInstance("Bags");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commit();
    }
}