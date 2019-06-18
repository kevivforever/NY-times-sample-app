package com.vivek.nytimes.data.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.vivek.nytimes.data.local.Converter

@Entity(tableName = "stories")
data class Story(
    @PrimaryKey
    @NonNull
    @SerializedName("url")
    var url: String = "",
    @SerializedName("abstract")
    var `abstract`: String = "",
    @SerializedName("byline")
    var byline: String = "",
    @SerializedName("created_date")
    var createdDate: String = "",
    @SerializedName("des_facet")
    var desFacet: ArrayList<String> = arrayListOf(),
    @SerializedName("geo_facet")
    var geoFacet: ArrayList<String> = arrayListOf(),
    @SerializedName("item_type")
    var itemType: String = "",
    @SerializedName("kicker")
    var kicker: String = "",
    @SerializedName("material_type_facet")
    var materialTypeFacet: String = "",
    @SerializedName("multimedia")
    var multimedia: ArrayList<Multimedia> = arrayListOf(),
    @SerializedName("org_facet")
    var orgFacet: ArrayList<String> = arrayListOf(),
    @SerializedName("per_facet")
    var perFacet: ArrayList<String> = arrayListOf(),
    @SerializedName("published_date")
    var publishedDate: String = "",
    @SerializedName("section")
    var section: String = "",
    @SerializedName("short_url")
    var shortUrl: String = "",
    @SerializedName("subsection")
    var subsection: String = "",
    @SerializedName("title")
    var title: String = "",
    @SerializedName("updated_date")
    var updatedDate: String = ""
)