// app/src/main/java/com/example/packingapp/PackingList.kt
package com.example.packingapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "packing_lists")
data class PackingList(
    @PrimaryKey(autoGenerate = true)
    val listId: Int = 0,
    val listName: String,
    val tripStartDate: Long?, // Store dates as timestamps (milliseconds)
    val tripEndDate: Long?    // Store dates as timestamps (milliseconds)
)