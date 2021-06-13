package com.example.graphql

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = []
)

abstract class DataBase: RoomDatabase() {
}