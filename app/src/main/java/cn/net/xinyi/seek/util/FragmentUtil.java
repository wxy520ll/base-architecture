package cn.net.xinyi.seek.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import cn.net.xinyi.seek.R;
import cn.net.xinyi.seek.ui.fragment.DiaryFragment;
import cn.net.xinyi.seek.ui.fragment.MapFragment;
import cn.net.xinyi.seek.ui.fragment.MessageFragment;
import cn.net.xinyi.seek.ui.fragment.MineFragment;

public class FragmentUtil {

    public static Fragment switchFragment(FragmentManager f, String tag,Fragment selectFragment) {
        Fragment mFragment = f.findFragmentByTag(tag);
        FragmentTransaction mFragmentTransaction = f.beginTransaction();
        if (selectFragment != null) {
            mFragmentTransaction.hide(selectFragment);
        }
        if (mFragment == null) {
            switch (tag) {
                case "map":
                    selectFragment = new MapFragment();
                    break;
                case "diary":
                    selectFragment = new DiaryFragment();
                    break;
                case "notices":
                    selectFragment = new MessageFragment();
                    break;
                case "setting":
                    selectFragment = new MineFragment();
                    break;
            }
            mFragmentTransaction.add(R.id.mFrameLayout, selectFragment, tag);
        } else {
            selectFragment = mFragment;
            mFragmentTransaction.show(mFragment);
        }
        mFragmentTransaction.commit();
        return selectFragment;
    }
}
