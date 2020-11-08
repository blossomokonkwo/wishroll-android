package co.wishroll.views.profile;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.wishroll.R;
import co.wishroll.databinding.FragmentBookmarksBinding;
import co.wishroll.models.domainmodels.Post;
import co.wishroll.utilities.StateData;
import co.wishroll.viewmodel.BookmarksViewModel;
import co.wishroll.viewmodel.ProfileViewModelFactory;
import co.wishroll.views.reusables.ImageActivity;
import co.wishroll.views.reusables.VideoActivity;
import co.wishroll.views.tools.EndlessRecyclerViewScrollListener;
import co.wishroll.views.tools.GridRecyclerViewAdapter;


public class BookmarksFragment extends Fragment {

    FragmentBookmarksBinding fragmentBookmarksBinding;
    private RecyclerView recyclerView;
    ArrayList<Post> bookmarksList = new ArrayList<>();
    int userId;
    View view;
    BookmarksViewModel bookmarksViewModel;

    ProgressBar progressBar;
    private TextView noResults;

    GridRecyclerViewAdapter gridRecyclerViewAdapter;
    GridLayoutManager gridLayoutManager;


    public BookmarksFragment() {
        // Required empty public constructor
    }

    public BookmarksFragment(int userId){
        this.userId = userId;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentBookmarksBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_bookmarks, container, false);
        view = fragmentBookmarksBinding.getRoot();
        recyclerView = view.findViewById(R.id.bookmarksRecyclerView);
        bookmarksViewModel = new ViewModelProvider(this, new ProfileViewModelFactory(userId)).get(BookmarksViewModel.class);
        gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        fragmentBookmarksBinding.setViewmodel(bookmarksViewModel);
        progressBar = view.findViewById(R.id.progressBarBookmarks);
        noResults = view.findViewById(R.id.noBookmarksText);

        observeBookmarksList();
        gridRecyclerViewAdapter = new GridRecyclerViewAdapter(getContext(), bookmarksList);
        recyclerView.setAdapter(gridRecyclerViewAdapter);

        gridRecyclerViewAdapter.setOnGridItemClickListener(new GridRecyclerViewAdapter.OnGridItemClickListener() {
            @Override
            public void onGridItemClicked(int position) {
                if(bookmarksList.get(position).getMediaUrl().contains(".mp4") || bookmarksList.get(position).getMediaUrl().contains(".mov") ){
                    Intent i = new Intent(getActivity(), VideoActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    i.putExtra("mediaUrl", bookmarksList.get(position).getMediaUrl());
                    i.putExtra("postId", bookmarksList.get(position).getId());
                    i.putExtra("isBookmarked", bookmarksList.get(position).getBookmarked());
                    i.putExtra("post", bookmarksList.get(position));
                    startActivity(i);
                }else{
                    Intent i = new Intent(getActivity(), ImageActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    i.putExtra("mediaUrl", bookmarksList.get(position).getMediaUrl());
                    i.putExtra("postId", bookmarksList.get(position).getId());
                    i.putExtra("isBookmarked", bookmarksList.get(position).getBookmarked());
                    i.putExtra("post", bookmarksList.get(position));
                    startActivity(i);
                }
            }


        });

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if(totalItemsCount % bookmarksViewModel.getBookmarksDataSetSize() == 0) {
                    bookmarksViewModel.getMoreBookmarked(totalItemsCount);

                }
            }
        });




        // Inflate the layout for this fragment
        return view;
    }

    public void observeBookmarksList(){
        bookmarksViewModel.observeBookmarks().observe(getActivity(), new Observer<StateData<ArrayList<Post>>>() {
            @Override
            public void onChanged(StateData<ArrayList<Post>> arrayListStateData) {

                if(arrayListStateData != null) {

                    switch (arrayListStateData.status) {
                        case LOADING:
                            showProgress(true);
                            break;

                        case AUTHENTICATED:
                            showProgress(false);
                            bookmarksList.addAll(arrayListStateData.data);
                            gridRecyclerViewAdapter.notifyDataSetChanged();


                            break;

                        case ERROR:

                            if (arrayListStateData.data == null || arrayListStateData.data.size() == 0) {
                                noResults.setVisibility(View.VISIBLE);
                                showProgress(false);
                            } else {
                                noResults.setVisibility(View.INVISIBLE);
                                showProgress(true);

                            }

                            //ToastUtils.showToast(getContext(), arrayListStateData.message);
                            break;

                        case NOT_AUTHENTICATED:
                            showProgress(false);
                            noResults.setVisibility(View.INVISIBLE);
                            bookmarksList.clear();
                            gridRecyclerViewAdapter.notifyDataSetChanged();
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


}