package com.anthonycraigkakatera.tasktracker;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.anthonycraigkakatera.tasktracker.model.StaffMember;
import com.anthonycraigkakatera.tasktracker.tabs.CompleteTasksTab;
import com.anthonycraigkakatera.tasktracker.tabs.HomeTab;
import com.anthonycraigkakatera.tasktracker.tabs.IncompleteTasksTab;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //holds an object of your login details
    public static StaffMember yourLoginDetails;
    //bottom navigation tab
    private TabLayout tabLayout;
    //used to swap fragments in an activity
    private ViewPager viewPager;
    //the main url of the backend
    public static String mainUrl ="";
    //creating tabs
    //Default tabs to swap out the fragments upon clicking the nav bar atop the screen
    HomeTab homeTab = new HomeTab();
    IncompleteTasksTab incompleteTasksTab = new IncompleteTasksTab();
    CompleteTasksTab completeTasksTab = new CompleteTasksTab();
    //used to receive the details from the login activity
    private String userName = "", id = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //adding tabs to control view pager
        addTabs();
        //getting the login details from login activity
        //getting intent values
        userName = (String) getIntent().getStringExtra("name");
        id = (String) getIntent().getStringExtra("id");
        //creating login object
        yourLoginDetails = new StaffMember(userName, id);
    }

    private void addTabs() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        //adding icons to the tabs
        //add icon to home tab
        Drawable iconImage1 = getResources().getDrawable(R.drawable.baseline_home_white_36);
        //add icon to incomplete tab
        Drawable iconImage2 = getResources().getDrawable(R.drawable.baseline_highlight_off_white_36);
        //add icon to complete tab
        Drawable iconImage3 = getResources().getDrawable(R.drawable.baseline_check_circle_outline_white_36);

        //setting image icons for all tabs
        tabLayout.getTabAt(0).setIcon(iconImage1);
        tabLayout.getTabAt(1).setIcon(iconImage2);
        tabLayout.getTabAt(2).setIcon(iconImage3);

    }

    private void setupViewPager(ViewPager viewPager) {
        //creating a viewpager adapter and adding each tab to it
        ViewPagerAdaptor adaptor = new ViewPagerAdaptor(getSupportFragmentManager());
        //the empty "" remove visible text from the tab menu which acts as a title
        adaptor.addFragment(homeTab, "");
        adaptor.addFragment(incompleteTasksTab, "");
        adaptor.addFragment(completeTasksTab, "");
        //setting the adapter
        viewPager.setAdapter(adaptor);
        //add listener to the adapter
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){

                }
                else if(position == 1){

                }
                else if(position == 2) {

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //here we call a special method that will help with navigation
        homeTab.loadViewPager(viewPager);
    }

    private class ViewPagerAdaptor  extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<Fragment>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdaptor(FragmentManager manager){
            super(manager);
        }


        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}