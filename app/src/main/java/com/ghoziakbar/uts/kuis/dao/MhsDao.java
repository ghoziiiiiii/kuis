package com.ghoziakbar.uts.kuis.dao;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ghoziakbar.uts.kuis.model.Mhs;

import java.util.List;

@Dao
public interface MhsDao {
    @Insert
    void insert(Mhs mhs);

    @Update
    void update(Mhs mhs);

    @Delete
    void delete(Mhs mhs);

    @Query("SELECT * FROM mhs")
    List<Mhs> getAll();
}
