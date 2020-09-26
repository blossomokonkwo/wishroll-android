package co.wishroll.localdb;

import androidx.paging.PagingSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FeedPostDao {


        @Insert
        void insert(List<FeedPostEntity> feedPostEntityList);

        @Update
        void update(List<FeedPostEntity> feedPostEntityList); //reeeallly not sure about this one

        @Delete
        void delete(List<FeedPostEntity> feedPostEntityList);

    /*@Query( "SELECT * FROM discover_posts_table ORDER BY id ASC")
    LiveData<PagedList<FeedPostEntity>> getDiscoverPosts();*/

    //is the room database in the context of the Post object or the DiscoverPost Entitiy becuase im about to start swinging
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insertAll(List<FeedPostEntity> discoverPostEntities);

        @Query("SELECT * FROM feed_posts_table ")
        PagingSource<Integer, FeedPostEntity> pagingSource(); //int offset     replaced String query to int offset because we actually didn't use query

        @Query("DELETE FROM feed_posts_table")
        int clearAll();

}
