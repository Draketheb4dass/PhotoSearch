package com.jephte.photosearch.util

import java.text.DateFormat

fun Long.miliToDateMMDDYYYY() = DateFormat.getDateInstance().format(this)