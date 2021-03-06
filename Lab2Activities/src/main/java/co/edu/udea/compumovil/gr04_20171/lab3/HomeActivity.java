package co.edu.udea.compumovil.gr04_20171.lab3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import co.edu.udea.compumovil.gr04_20171.lab3.about.AboutFragment;
import co.edu.udea.compumovil.gr04_20171.lab3.configuration.ConfigurationFragment;
import co.edu.udea.compumovil.gr04_20171.lab3.event.addEdit.AddEventFragment;
import co.edu.udea.compumovil.gr04_20171.lab3.event.data.EventDbHelper;
import co.edu.udea.compumovil.gr04_20171.lab3.event.eventList.EventFragment;
import co.edu.udea.compumovil.gr04_20171.lab3.user.login.LoginActivity;
import co.edu.udea.compumovil.gr04_20171.lab3.user.profile.ProfileFragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AddEventFragment.OnFragmentInteractionListener {

    private Session session;
    private FragmentManager fragmentManager;
    private EventDbHelper eventDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        eventDbHelper = new EventDbHelper(HomeActivity.this);

        // Iniciamos el fragment manager
        fragmentManager = getSupportFragmentManager();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment addEventFragment = new AddEventFragment();
                fragmentManager.beginTransaction().replace(R.id.content_home,addEventFragment).commit();
                /*AddEventFragment addEventFragment = (AddEventFragment)
                        getSupportFragmentManager().findFragmentById(R.id.content_home);
                if (addEventFragment == null) {
                    addEventFragment = AddEventFragment.newInstance();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.content_home, addEventFragment)
                            .commit();
                }*/
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        session = new Session(this);
        if(!session.loggedin()){
            logout();
        }

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
        getMenuInflater().inflate(R.menu.home, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getText(R.string.action_search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(HomeActivity.this, query, Toast.LENGTH_SHORT).show();
                // TODO: BUSCAR EVENTOS POR NOMBRE
                /*
                Cursor cursor = eventDbHelper.getEventByName(query);
                cursor.moveToPosition(0);
                Event event = null;
                try {
                    event = new Event(cursor);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Toast.makeText(HomeActivity.this, event.getName(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(HomeActivity.this, event.get..., Toast.LENGTH_SHORT).show();
                */
                searchView.setQuery("", false);
                searchView.setIconified(true);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                //Toast.makeText(HomeActivity.this, newText, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment = null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_events) {
            fragment = new EventFragment();
        } else if (id == R.id.nav_profile) {
            fragment = new ProfileFragment();
        } else if (id == R.id.nav_config) {
            fragment = new ConfigurationFragment();
        } else if (id == R.id.nav_about) {
            fragment = new AboutFragment();
        } else if (id == R.id.nav_logout) {
            logout();
        }

        if(fragment != null) {
            fragmentManager.beginTransaction().replace(R.id.content_home,fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        session.setLoggedin(false);
        finish();
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
