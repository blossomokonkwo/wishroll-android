package co.wishroll.views.tools;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import co.wishroll.views.profile.UploadsFragment;

public class ProfileViewPagerAdapter extends FragmentStateAdapter {

    private static final String TAG = "VIEW PAGER ADAPTER";

    boolean isCurrentUser;
    int userId;

    public ProfileViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.isCurrentUser = true;
    }

    public ProfileViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, boolean isCurrentUser, int userId){
        super(fragmentActivity);
        this.isCurrentUser = isCurrentUser;
        this.userId = userId;
    }




    @NonNull
    @Override
    public Fragment createFragment(int position) {

       /* if(isCurrentUser) {
            switch (position) {
                case 0:
                    return new UploadsFragment(userId);

                default:
                    return new LikesFragment(userId);


            }
        }else {*/


                    return new UploadsFragment(userId);





    }

    @Override
    public int getItemCount() {

        if(isCurrentUser) {
            return 2;
        }else{
            return 1;
        }

    }








}