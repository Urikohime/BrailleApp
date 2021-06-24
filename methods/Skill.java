package com.example.abyteofbraille.methods;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Skill {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "symbol")
    public String symbol;

    @ColumnInfo(name = "counter")
    public int skill;
}
