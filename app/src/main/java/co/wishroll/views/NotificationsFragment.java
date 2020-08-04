package co.wishroll.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.wishroll.R;
import co.wishroll.model.Notification;
import co.wishroll.utilities.NotificationRecyclerViewAdapter;

public class NotificationsFragment extends Fragment {

    View view;

    private RecyclerView myRecyclerView;
    private List<Notification> notificationsList;

    public NotificationsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notifications, container, false);
        myRecyclerView = (RecyclerView) view.findViewById(R.id.notificationsRecyclerView);
        NotificationRecyclerViewAdapter notificationRecyclerViewAdapter = new NotificationRecyclerViewAdapter(getContext(), notificationsList);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(notificationRecyclerViewAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notificationsList = new ArrayList<>();

        //TODO(Right now we have to create the users but I'm sure they will be fetched through requests very soon)
        Notification basicNotification = new Notification();


        for(int i = 0; i < 200; i++){
            notificationsList.add(basicNotification);
        }


    }
}
