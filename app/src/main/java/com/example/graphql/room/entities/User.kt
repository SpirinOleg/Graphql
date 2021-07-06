package com.example.graphql.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.graphql.room.tables.USER

@Entity(tableName = USER)
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var email: String,
    var token: String,
    var password: String
)
