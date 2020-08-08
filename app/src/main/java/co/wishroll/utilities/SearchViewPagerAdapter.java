package co.wishroll.utilities;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;


import co.wishroll.views.home.PostsFragment;
import co.wishroll.views.home.ProfilesFragment;


public class SearchViewPagerAdapter extends FragmentStateAdapter {


    public SearchViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                return new PostsFragment();
            default:
                return new ProfilesFragment();

        }

    }



    @Override
    public int getItemCount() {

        return 2;

    }

}









