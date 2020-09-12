package co.wishroll.views.tools;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import co.wishroll.views.home.TrendingFragment;


public class MainViewPagerAdapter extends FragmentStateAdapter {

    private static final String TAG = "VIEW PAGER ADAPTER";

    public MainViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }




    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            /*case 0:
                return new TrendingFragment();*/
            /*case 1:
                return new MessagesFragment();*/
            default:
                return new TrendingFragment();
        }


    }

    @Override
    public int getItemCount() {

        return 1;
        //TODO(changed to 1 becuase Rolls is yet to come out for androud)
        //changed to 2 from three because of the exclusion of messages tab

    }








}
