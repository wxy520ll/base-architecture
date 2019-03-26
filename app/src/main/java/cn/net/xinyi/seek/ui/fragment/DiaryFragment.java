package cn.net.xinyi.seek.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xylibrary.base.BaseMvpFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.net.xinyi.seek.R;
import cn.net.xinyi.seek.presenter.LoginPresenter;
import cn.net.xinyi.seek.ui.fragment.adapter.ContentPagerAdapter;


/**
 * 设置
 */
public class DiaryFragment extends BaseMvpFragment<LoginPresenter> {
    @Bind(R.id.tvFouce)
    TextView tvFouce;
    @Bind(R.id.tvRecommand)
    TextView tvRecommand;
    @Bind(R.id.tvNews)
    TextView tvNews;
    @Bind(R.id.mViewPager)
    ViewPager mViewPager;
    @Bind(R.id.mRadioGroup)
    RadioGroup mRadioGroup;

    private List<Fragment> fragments = new ArrayList<>();
    private ContentPagerAdapter adapter;

    @Override
    public LoginPresenter inject() {
        return new LoginPresenter();
    }


    @Override
    public void initView() {
        fragments.add(new FocusFragment());
        fragments.add(new RecommandFragment());
        fragments.add(new NewFragment());
        adapter = new ContentPagerAdapter(getFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
               RadioButton radioButton= (RadioButton) mRadioGroup.getChildAt(i);
               radioButton.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public View inflaterView() {
        return View.inflate(getActivity(), R.layout.fragment_diary, null);
    }

}
