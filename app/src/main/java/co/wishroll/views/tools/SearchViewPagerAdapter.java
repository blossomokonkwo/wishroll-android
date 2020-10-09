package co.wishroll.views.tools;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import co.wishroll.views.search.GifSearchFragment;
import co.wishroll.views.search.ImageSearchFragment;
import co.wishroll.views.search.VideoSearchFragment;


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
                return new VideoSearchFragment(query);
            case 1:
                return new ImageSearchFragment(query);
            default:
                return new GifSearchFragment(query);

        }

    }



    @Override
    public int getItemCount() {

        return 3;

    }

}









