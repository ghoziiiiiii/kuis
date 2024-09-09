package com.ghoziakbar.uts.kuis.model;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mhs")
public class Mhs {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "nama")
    public String nama;

    @ColumnInfo(name = "nim")
    public String nim;

    @ColumnInfo(name = "prodi")
    public String prodi;
}