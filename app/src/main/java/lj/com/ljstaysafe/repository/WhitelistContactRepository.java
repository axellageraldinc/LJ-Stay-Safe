package lj.com.ljstaysafe.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import lj.com.ljstaysafe.model.WhitelistContact;

@Dao
public interface WhitelistContactRepository {
    @Query(value = "SELECT * FROM contact")
    List<WhitelistContact> findAllWhitelistContact();
    @Insert
    void insertWhitelistContact(WhitelistContact whitelistContact);
    @Update
    void updateWhitelistContact(WhitelistContact whitelistContact);
    @Delete
    void deleteWhitelistContact(WhitelistContact whitelistContact);
}
