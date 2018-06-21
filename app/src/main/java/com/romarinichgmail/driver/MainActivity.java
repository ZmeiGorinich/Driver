package com.romarinichgmail.driver;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.romarinichgmail.driver.Fragment.FragmentHistory;
import com.romarinichgmail.driver.Fragment.FragmentMap;
import com.romarinichgmail.driver.Fragment.FragmentOnlineOrder;
import com.romarinichgmail.driver.Fragment.FragmentOrder;
import com.romarinichgmail.driver.Fragment.FragmentProfile;
import com.romarinichgmail.driver.LoginReg.Login;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new FragmentOrder()).commit();
            setTitle("Заказы");
            navigationView.setCheckedItem(R.id.order);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.order:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentOrder()).commit();
                setTitle("Заказы");
                break;
            case R.id.map:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentMap()).commit();
                setTitle("Карта");
                break;
            case R.id.history:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentHistory()).commit();
                setTitle("История перевозок");
                break;
            case R.id.online:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentOnlineOrder()).commit();
                setTitle("Нынешний заказ");
                break;
            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentProfile()).commit();
                setTitle("Мой профиль");
                break;


            case R.id.singOut:
                FirebaseAuth.getInstance().signOut();
                Intent intent_p = new Intent(MainActivity.this, Login.class);
                startActivity(intent_p);
                finish();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
