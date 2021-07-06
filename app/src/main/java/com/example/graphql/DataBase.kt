package com.example.graphql

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.graphql.room.entities.User

@Database(
    version = 1,
    entities = [
        User::class
    ]
)


abstract class DataBase: RoomDatabase() {

}