package co.wishroll.localdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface DiscoverPostDao {


    @Insert
    void insert(DiscoverPostEntity discoverPostEntity);

    @Update
    void update(DiscoverPostEntity discoverPostEntity);

    @Delete
    void delete(DiscoverPostEntity discoverPostEntity);

    /*@Query( "SELECT * FROM discover_posts_table ORDER BY id ASC")
    LiveData<PagedList<DiscoverPostEntity>> getDiscoverPosts();*/
}
