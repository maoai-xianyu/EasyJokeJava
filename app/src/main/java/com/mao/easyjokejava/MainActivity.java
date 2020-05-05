package com.mao.easyjokejava;

import com.mao.baselibrary.ioc.OnClick;
import com.mao.easyjokejava.fragment.FindFragment;
import com.mao.easyjokejava.fragment.HomeFragment;
import com.mao.easyjokejava.fragment.MessageFragment;
import com.mao.easyjokejava.fragment.NewFragment;
import com.mao.framelibrary.BaseSkinActivity;
import com.mao.framelibrary.DefaultNavigationBar;

public class MainActivity extends BaseSkinActivity {

    private HomeFragment mHomeFragment;
    private FindFragment mFindFragment;
    private NewFragment mNewFragment;
    private MessageFragment mMessageFragment;

    private FragmentManagerHelper mFragmentHelper;

    @Override
    protected void initData() {

        mFragmentHelper = new FragmentManagerHelper(getSupportFragmentManager(), R.id.main_tab_fl);
        mHomeFragment = new HomeFragment();
        mFragmentHelper.add(mHomeFragment);


    }


    @Override
    protected void initView() {

    }


    @Override
    protected void initTitle() {

        DefaultNavigationBar navigationBar = new
                DefaultNavigationBar.Builder(this)
                .setTitle("首页")
                .hideBackIcon()
                .builder();

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }


    @OnClick(R.id.home_rb)
    private void homeRbClick() {
        if (mHomeFragment == null) {
            mHomeFragment = new HomeFragment();
        }
        mFragmentHelper.switchFragment(mHomeFragment);
    }

    @OnClick(R.id.find_rb)
    private void findRbClick() {
        if (mFindFragment == null) {
            mFindFragment = new FindFragment();
        }
        mFragmentHelper.switchFragment(mFindFragment);
    }

    @OnClick(R.id.new_rb)
    private void newRbClick() {
        if (mNewFragment == null) {
            mNewFragment = new NewFragment();
        }
        mFragmentHelper.switchFragment(mNewFragment);
    }

    @OnClick(R.id.message_rb)
    private void messageRbClick() {
        if (mMessageFragment == null) {
            mMessageFragment = new MessageFragment();
        }
        mFragmentHelper.switchFragment(mMessageFragment);
    }


}
