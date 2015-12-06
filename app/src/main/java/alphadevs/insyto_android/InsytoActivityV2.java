package alphadevs.insyto_android;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.PrintStreamPrinter;
import android.util.Printer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import alphadevs.insyto_android.preferences.MainPrefs;

public class InsytoActivityV2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
            InsyteFragmentList.OnInsyteListInteractionListener,
            CreateInsyteFragment.CreateInsyteListener {

    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insyto_activity_v2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // FAB Create
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                CreateInsyteFragment createFragment = CreateInsyteFragment.newInstance();

                final FragmentManager supportFragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = supportFragmentManager.beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.insyte_list, createFragment);
                transaction.addToBackStack(null);
                fab.hide();
                supportFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                    @Override
                    public void onBackStackChanged() {
                        android.support.v4.app.Fragment list = supportFragmentManager.findFragmentById(R.id.insyte_list);
                        if (list != null) {
                            fab.show();
                        }
                    }
                });

                // Commit the transaction
                transaction.commit();
            }
        });

        // NAV
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.insyto_activity_v2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_nearby) {
            item.setChecked(!item.isChecked());
            navNearby(item.isChecked());

            // Reload list fragment
            android.support.v4.app.Fragment frag = getSupportFragmentManager().findFragmentById(R.id.insyte_list);
            if(frag instanceof InsyteFragmentList)
            {
                ((InsyteFragmentList) frag).reloadAll();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void navNearby(boolean active) {
        // TODO JUST EXAMPLE DO NOT USE
        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        final MainPrefs prefs = new MainPrefs(getApplicationContext());
        // Define a listener that responds to location updates
        if (active) {
            prefs.setNearbyActive(true);
            locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    // Called when a new location is found by the network location provider.
                    Printer outPrinter = new PrintStreamPrinter(System.out);
                    location.dump(outPrinter, "Insyto location");
                    prefs.setLastKnownLatitude(location.getLatitude());
                    prefs.setLastKnownLongitude(location.getLongitude());
                }

                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                public void onProviderEnabled(String provider) {
                }

                public void onProviderDisabled(String provider) {
                }
            };
            List<String> locationProviders = locationManager.getAllProviders();
            // Register the listener with the Location Manager to receive location updates
            try {
                if (locationProviders.contains("network")) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 5, locationListener);
                }
                if (locationProviders.contains("gps")) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, locationListener);
                }

            } catch (SecurityException e) {
                Toast.makeText(getApplicationContext(), "Insufficient permissions to request location", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        } else {
            prefs.setNearbyActive(false);
            try {
                locationManager.removeUpdates(locationListener);
            } catch (SecurityException e) {
                Toast.makeText(getApplicationContext(), "Insufficient permissions to request location", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void replaceInsyteFragment(Integer id)
    {
        InsyteFragment insyteFragment = InsyteFragment.newInstance(id);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.insyte_list, insyteFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void previousFragment() {
        getFragmentManager().popBackStack();
    }
}
