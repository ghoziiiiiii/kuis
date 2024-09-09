package com.ghoziakbar.uts.kuis;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.ghoziakbar.uts.kuis.dao.MhsDao;
import com.ghoziakbar.uts.kuis.model.Mhs;

@Database(entities = {Mhs.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MhsDao mhsDao();
}