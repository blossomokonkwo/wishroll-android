package co.wishroll.views.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.wishroll.R;
import co.wishroll.databinding.FragmentNotificationsBinding;
import co.wishroll.models.domainmodels.UserNotification;
import co.wishroll.utilities.StateData;
import co.wishroll.viewmodel.NotificationsViewModel;
import co.wishroll.viewmodel.ProfileViewModelFactory;
import co.wishroll.views.tools.EndlessRecyclerViewScrollListener;
import co.wishroll.views.tools.NotisRecyclerViewAdapter;


public class NotificationsFragment extends Fragment implements NotisRecyclerViewAdapter.OnNotificationListener {
    int userId;
    ArrayList<UserNotification> notifsList = new ArrayList<>();
    private RecyclerView recyclerView;
    FragmentNotificationsBinding fragmentNotificationsBinding;
    NotificationsViewModel notificationsViewModel;
    View view;

    private ProgressBar progressBar;
    private TextView noResults;

    NotisRecyclerViewAdapter notifsRecyclerViewAdapter;
    LinearLayoutManager linearLayoutManager;



    public NotificationsFragment() {
        // Required empty public constructor

    }

    public NotificationsFragment(int userId){
        this.userId = userId;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentNotificationsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_notifications, container, false);
        view = fragmentNotificationsBinding.getRoot();
        recyclerView = view.findViewById(R.id.notificationsRecyclerView);
        notificationsViewModel = new ViewModelProvider(this, new ProfileViewModelFactory(userId)).get(NotificationsViewModel.class);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        fragmentNotificationsBinding.setViewmodel(notificationsViewModel);
        progressBar = view.findViewById(R.id.progressBarNotis);
        noResults = view.findViewById(R.id.noNotisText);

        //observeNotifsList();




        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if(totalItemsCount % notificationsViewModel.getNotificationsDataSetSize() == 0) {
                    notificationsViewModel.getMoreNotifications(totalItemsCount);

                }
            }
        });






        // Inflate the layout for this fragment
        return view;
    }

    public void observeNotifsList(){
        notificationsViewModel.observeNotifications().observe(getActivity(), new Observer<StateData<ArrayList<UserNotification>>>() {
            @Override
            public void onChanged(StateData<ArrayList<UserNotification>> arrayListStateData) {

                if(arrayListStateData != null) {

                    switch (arrayListStateData.status) {
                        case LOADING:
                            showProgress(true);
                            break;

                        case AUTHENTICATED:
                            showProgress(false);
                            notifsList.addAll(arrayListStateData.data);
                            //]gridRecyclerViewAdapter.notifyDataSetChanged();


                            break;

                        case ERROR:

                            if (arrayListStateData.data == null || arrayListStateData.data.size() == 0) {
                                noResults.setVisibility(View.VISIBLE);
                                showProgress(false);
                            } else {
                                noResults.setVisibility(View.INVISIBLE);
                                showProgress(true);

                            }

                            break;

                        case NOT_AUTHENTICATED:
                            showProgress(false);
                            noResults.setVisibility(View.INVISIBLE);
                            notifsList.clear();
                            //gridRecyclerViewAdapter.notifyDataSetChanged();
                            break;
                    }

                }
            }
        });

    }

    public void showProgress(boolean isVisible){
        if(isVisible){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNotificationClicked(int position, int notificationIntentPosition) {

        if(notificationIntentPosition == 1){ //going to this user's profile through the profile picture
//            Intent intent = new Intent(getContext(), ProfileViewActivity.class);
//            intent.putExtra("userId", notifsList.get(position).getActiveUser().getId());

        }else if(notificationIntentPosition == 2){ //wanting to see your post that they interacted with

            if(notifsList.get(position).getPost().getMediaUrl().contains(".mp4") || notifsList.get(position).getPost().getMediaUrl().contains(".mov")  ){
//                Intent intent = new Intent(getContext(), VideoActivity.class);
//                intent.putExtra("postId", notifsList.get(position).getPost().getId());
//                intent.putExtra("postItem", notifsList.get(position).getPost());


            }else{

//                Intent intent = new Intent(getContext(), ImageActivity.class);
//                intent.putExtra("postId", notifsList.get(position).getPost().getId());
//                intent.putExtra("postItem", notifsList.get(position).getPost());

            }

        }else if(notificationIntentPosition == 3){ //going to this user's profile through the username


        }
    }
}