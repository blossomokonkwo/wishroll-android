package co.wishroll.views.tools;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class SearchViewPagerAdapter extends FragmentStateAdapter {

    String query;

    public SearchViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public SearchViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, String query) {
        super(fragmentActivity);
        this.query = query;
    }



    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
               return null;//new VideoSearchFragment();
            default:
                return null; /*new ImageSearchFragment()*/
            /*default:
                return new GifSearchFragment();*/

        }

    }



    @Override
    public int getItemCount() {

        return 2;

    }

}









