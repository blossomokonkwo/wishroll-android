package co.wishroll.views.tools;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import co.wishroll.views.reusables.MoreLikeThisFragment;

public class MoreLikeThisPagerAdapter extends FragmentStateAdapter {

    int postId = 0;

    public MoreLikeThisPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);

    }

    public MoreLikeThisPagerAdapter(@NonNull FragmentActivity fragmentActivity, int postId) {
        super(fragmentActivity);
        this.postId = postId;
    }



    @NonNull
    @Override
    public Fragment createFragment(int position) {

        return new MoreLikeThisFragment(postId);


    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
