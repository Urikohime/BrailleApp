package com.example.abyteofbraille.methods;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DBA {
    @Query("SELECT * FROM skill")
    List<Skill> getAll();

    @Query("SELECT * FROM skill WHERE symbol LIKE :symb")
    Skill findBySymbol(String symb);

    @Query("UPDATE Skill SET counter = :skill WHERE id = :id")
    int updateSkill(int id, int skill);

    @Query("INSERT INTO Skill VALUES (:id, :symbol, :skill)")
    void insert(int id, String symbol, int skill);
}
