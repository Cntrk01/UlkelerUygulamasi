package com.example.ulkelerinbayraklarmvvmcoroutineretrofit.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ulkelerinbayraklarmvvmcoroutineretrofit.model.Country

@Dao
interface CountryDao {

    @Insert
    suspend fun insertAll(vararg country:Country):List<Long>

    @Query("SELECT*FROM country")
    suspend fun getAllData() : List<Country>

    @Query("SELECT*FROM country WHERE uuid=:countryID")
    suspend fun getCountry(countryID:Int):Country

    @Query("DELETE FROM country")
    suspend fun deleteAllData()
}