package com.lj.crud_supabase.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lj.crud_supabase.utilities.ORDER_TABLE

@Entity(tableName = ORDER_TABLE)
data class Order(
    @PrimaryKey
    @ColumnInfo(name = "orderId")
    val id: String,

    @ColumnInfo(name = "status")
    var status: String,

    @ColumnInfo(name = "address")
    var address: String
)
