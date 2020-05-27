package co.WishRoll.Profile;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class PagerAdapter extends FragmentStatePagerAdapter {

    int numTabs;

    public PagerAdapter(FragmentManager fm, int numTabs){
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.numTabs = numTabs;
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                UploadsFragment uploadsFragment = new UploadsFragment();
                return uploadsFragment;



            case 1:
                LikesFragment likesFragment = new LikesFragment();
                return likesFragment;



            case 2:
                BookmarksFragment bookmarksFragment = new BookmarksFragment();
                return bookmarksFragment;
            default:
                return null;


        }


    }

    @Override
    public int getCount() {

        return numTabs;
    }
}
