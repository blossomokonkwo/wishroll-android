package co.wishroll.views.tools;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import co.wishroll.views.profile.BookmarksFragment;
import co.wishroll.views.profile.LikesFragment;
import co.wishroll.views.profile.NotificationsFragment;
import co.wishroll.views.profile.UploadsFragment;

public class ProfileViewPagerAdapter extends FragmentStateAdapter {

    private static final String TAG = "VIEW PAGER ADAPTER";

    boolean isCurrentUser;

    public ProfileViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.isCurrentUser = true;
    }

    public ProfileViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, boolean isCurrentUser){
        super(fragmentActivity);
        this.isCurrentUser = isCurrentUser;
    }




    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if(isCurrentUser) {
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
        }else {
            switch (position) {
                case 0:
                    return new UploadsFragment();
                case 1:
                    return new LikesFragment();

                default:
                    return new BookmarksFragment();
            }
        }


    }

    @Override
    public int getItemCount() {

        if(isCurrentUser) {
            return 4;
        }else{
            return 3;
        }

    }








}
