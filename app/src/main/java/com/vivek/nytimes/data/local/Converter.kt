package com.vivek.nytimes.data.local

import androidx.room.TypeConverter
import java.util.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vivek.nytimes.data.model.Multimedia
import kotlin.collections.ArrayList


class Converter {
    var gson = Gson()

    @TypeConverter
    fun stringToList(data: String?): ArrayList<String> {
        if (data == null) {
            return arrayListOf()
        }

        val listType = object : TypeToken<ArrayList<String>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun ListToString(data: ArrayList<String>): String {
        return gson.toJson(data)
    }

    @TypeConverter
    fun toMultimedia(s: String?): ArrayList<Multimedia>? {
        if (s == null) {
            return null
        }
        val listType = object : TypeToken<ArrayList<Multimedia>>() {

        }.type
        return gson.fromJson(s, listType)
    }

    @TypeConverter
    fun fromMultimedia(list: ArrayList<Multimedia>?): String? {
        if (list == null) {
            return null
        }
        return gson.toJson(list)
    }
}