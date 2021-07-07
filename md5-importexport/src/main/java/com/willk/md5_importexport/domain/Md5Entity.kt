package com.willk.md5_importexport.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Md5Entity (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var md5: String? = null)
