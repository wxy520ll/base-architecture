package cn.net.xinyi.seek.ui.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.xylibrary.utils.StateBarUtil;

import cn.net.xinyi.seek.R;
import cn.net.xinyi.seek.ui.fragment.MapFragment;
import cn.net.xinyi.seek.util.FragmentUtil;

public class MainActivity extends AppCompatActivity {
    public FragmentTransaction mFragmentTransaction;
    public Fragment selectFragment;
    private TextView mTextMessage;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_map:
                    selectFragment=FragmentUtil.switchFragment(getSupportFragmentManager(),"map",selectFragment);
                    return true;
               case R.id.navigation_diary:
                   selectFragment=FragmentUtil.switchFragment(getSupportFragmentManager(),"diary",selectFragment);
                    return true;
                case R.id.navigation_notices:
                    selectFragment=FragmentUtil.switchFragment(getSupportFragmentManager(),"notices",selectFragment);
                    return true;
                case R.id.navigation_setting:
                    selectFragment=FragmentUtil.switchFragment(getSupportFragmentManager(),"setting",selectFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StateBarUtil.setStatusBarColor(this, R.color.colorAccent);
        setContentView(R.layout.activity_main);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        selectFragment=new MapFragment();
        mFragmentTransaction.add(R.id.mFrameLayout,selectFragment , "home");
        mFragmentTransaction.commit();
    }



}
