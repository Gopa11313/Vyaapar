package net.com.gopal.vyapar.database;

import android.content.Context;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import net.com.gopal.vyapar.database.dao.CompanyDao;
import net.com.gopal.vyapar.database.dao.CustomerDao;
import net.com.gopal.vyapar.database.dao.ProductDao;
import net.com.gopal.vyapar.database.entity.Company;
import net.com.gopal.vyapar.database.entity.Customer;
import net.com.gopal.vyapar.database.entity.Product;

@Database(version = 1, entities = {Customer.class, Product.class, Company.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract CustomerDao customerDao();

    public abstract ProductDao productDao();

    public abstract CompanyDao companyDao();

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