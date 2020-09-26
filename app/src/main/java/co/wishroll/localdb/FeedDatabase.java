package co.wishroll.localdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = FeedPostEntity.class, version = 1, exportSchema = false ) //version is used when database os changed, tracks migration, export schema is for json mapping of db
public abstract class FeedDatabase extends RoomDatabase {

    private static FeedDatabase instance;

    public abstract FeedPostDao discoverPostDao();

    //Singleton Pattern, Dagger Soon? Maybe
    public static synchronized FeedDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FeedDatabase.class, "feed_database") //name is the name of this particular file
                    .fallbackToDestructiveMigration() //tells room how to go about things when the databse migrated
                    .build();
        }
        return instance;
    }
}