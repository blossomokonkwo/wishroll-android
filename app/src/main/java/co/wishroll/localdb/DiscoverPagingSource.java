package co.wishroll.localdb;

import androidx.paging.rxjava2.RxPagingSource;

import co.wishroll.models.domainmodels.Post;

public class DiscoverPagingSource extends RxPagingSource<Integer, Post> {
    //identifies the data source
    //includes the load() method, which you must override to indicate how to retrieve paged data from the corresponding data source.
}
