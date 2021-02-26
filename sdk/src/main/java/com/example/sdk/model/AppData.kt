package com.example.sdk.model

import android.graphics.drawable.Drawable

data class AppData(
    var app_name: String,
    var package_name: String,
    var icon: Drawable?,
    var main_activity_name: String?,
    var version_code: String,
    var version: String,
    var install_data: String?,
    var flags: Int
) {
    constructor() : this(
        "", "", null,
        "", "", "", "",0
    )
}

