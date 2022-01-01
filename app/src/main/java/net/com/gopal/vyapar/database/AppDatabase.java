package net.com.gopal.vyapar.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import net.com.gopal.vyapar.database.dao.CustomerDao;
import net.com.gopal.vyapar.database.entity.Customer;

@Database(entities = {Customer.class}, version = 1, exportSchema = false)
public abstract  class AppDatabase extends RoomDatabase {

    public abstract CustomerDao customerDao();

    private static AppDatabase INSTANCE;

   public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "vyaapar")
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}