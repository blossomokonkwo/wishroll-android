package co.wishroll.localdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = DiscoverPostEntity.class, version = 1, exportSchema = false ) //version is used when database os changed, tracks migration
public abstract class DiscoverDB extends RoomDatabase {

    private static DiscoverDB instance;

    public abstract DiscoverPostDao discoverPostDao();

    public static synchronized DiscoverDB getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DiscoverDB.class, "discover_database") //name is the name of this particular file
                    .fallbackToDestructiveMigration() //tells room how to go about things when the databse migrated
                    .build();
        }
        return instance;
    }
}
