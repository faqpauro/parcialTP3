package com.example.parcialtp3.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
@Dao
interface adoptionDao {

    @Query("INSERT INTO adoption (owner_id, dog_id, date) VALUES (:ownerId, :dogId, CURRENT_TIMESTAMP)")
    fun registerNewAdoption(ownerId: Int, dogId: Int)

}