package co.wishroll.utilities;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import co.wishroll.views.BookmarksFragment;
import co.wishroll.views.LikesFragment;
import co.wishroll.views.NotificationsFragment;
import co.wishroll.views.UploadsFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private static final String TAG = "VIEW PAGER ADAPTER";

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }




    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                return new UploadsFragment();
            case 1:
                return new LikesFragment();
            case 2:
                return new BookmarksFragment();
            default:
                return new NotificationsFragment();
        }


    }

    @Override
    public int getItemCount() {

        return 4;

    }








}
