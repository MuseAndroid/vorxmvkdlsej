package kr.co.genexon.factFinder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class IntroduceCompanyActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout cb_drawLayout1;
    TextView cb_searchBohum1;
    TextView cb_com_introduce1;
    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_introduce_company);

        cb_drawLayout1 = (DrawerLayout) findViewById(R.id.cb_drawerLayout1);
        cb_searchBohum1 = (TextView) findViewById(R.id.cb_searchBohum1);
        cb_com_introduce1 = (TextView) findViewById(R.id.cb_com_introduce1);

        cb_searchBohum1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationItemClick(v);
            }
        });

        cb_com_introduce1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationItemClick(v);
            }
        });

//        Toast.makeText(getBaseContext(), "뒤로 버튼을 누르면 메인 화면으로 이동합니다.",Toast.LENGTH_SHORT).show();

        setNavigationView();
    }

    public void setNavigationView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, cb_drawLayout1, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        cb_drawLayout1.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView1 = (NavigationView) findViewById(R.id.cb_navigationView1);
        navigationView1.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.cb_searchBohum) {
            onClick(0);
        } else if (id == R.id.cb_com_introduce) {
            onClick(1);
        }

        cb_drawLayout1.closeDrawer(GravityCompat.START);
        return true;
    }

    public void navigationItemClick(View v) {
        int id = v.getId();

        if (id == R.id.cb_searchBohum1) {
            onClick(0);
        } else if (id == R.id.cb_com_introduce1) {
            onClick(1);
        }

        cb_drawLayout1.closeDrawer(GravityCompat.START);
    }

    public void onClick(int onClick) {
        switch (onClick) {
            case 0:
                Log.d("testApp", "보장분석 페이지 - 눌렸다!!! : 보장분석");
                mIntent = new Intent(IntroduceCompanyActivity.this, MainViewActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//                onBackPressed();
                break;
            case 1:
                Log.d("testApp", "보장분석 페이지 - 눌렸다!!! : 회사소개");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
