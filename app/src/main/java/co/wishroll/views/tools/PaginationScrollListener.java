package co.wishroll.views.tools;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

    GridLayoutManager gridLayoutManager;
    LinearLayoutManager linearLayoutManager;
    //RecyclerView.LayoutManager layoutManager;

   /* public PaginationScrollListener(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }
*/
    /**
     * Supporting only LinearLayoutManager for now.
     *
     * @param layoutManager
     */


    public PaginationScrollListener(GridLayoutManager layoutManager) {
        this.gridLayoutManager = layoutManager;
    }

    public PaginationScrollListener(LinearLayoutManager layoutManager) {
        this.linearLayoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = linearLayoutManager.getChildCount();
        int totalItemCount = linearLayoutManager.getItemCount();
        int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= getTotalPageCount()) {
                loadMoreItems();
            }
        }

    }


   /* @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int[] firstVisibleItemPositions = layoutManager.fi;
        if (!isLoading() && !isLastPage()) {
            if ((firstVisibleItemPositions[0] + visibleItemCount) >= totalItemCount
                    && firstVisibleItemPositions[0] >= 0
                    && totalItemCount >= getTotalPageCount()) {
                loadMoreItems();
            }
        }
    }*/

        /*@Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = mGridLayoutManager.getChildCount();
            int totalItemCount = mGridLayoutManager.getItemCount();
            int[] firstVisibleItemPositions = mGridLayoutManager.findFirstVisibleItemPositions(null);
            if (!mIsLoading && !mIsLastPage) {
                if ((firstVisibleItemPositions[0] + visibleItemCount) >= totalItemCount
                        && firstVisibleItemPositions[0] >= 0
                        && totalItemCount >= Config.PAGE_SIZE) {
                    loadMorePosts();
                }
            }
        }
    });*/

    protected abstract void loadMoreItems();

    public abstract int getTotalPageCount();

    public abstract boolean isLastPage();

    public abstract boolean isLoading();

}
