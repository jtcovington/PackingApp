package com.example.packingapp // Replace with your actual package name

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "packing_items")
data class PackingItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val category: String,
    var packed: Boolean = false,  //  MUST be var
    var quantity: Int = 0,       //  MUST be var, and initialize to 0
    val tripType: String,
    val tripDuration: Int,     //  Keep this for filtering in the DAO
    var baseQuantity: Int,       //  MUST be var
    var quantityPerDay: Int     //  MUST be var
)