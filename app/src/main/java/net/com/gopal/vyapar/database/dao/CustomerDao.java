package net.com.gopal.vyapar.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import net.com.gopal.vyapar.database.entity.Customer;

import java.util.List;

@Dao
public interface CustomerDao {
    @Query("SELECT * FROM customers")
    List<Customer> getAll() ;

//    @Query("SELECT * FROM customers WHERE uid IN (:userIds)")
//    List<User> loadAllByIds(int[] userIds);
//
//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    User findByName(String first, String last);

    @Insert
    void insertAll(Customer... customers);

    @Delete
    void delete(Customer customer);
}
