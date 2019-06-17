package com.vivek.nytimes.data.model

import com.google.gson.annotations.SerializedName

data class Story(
    @SerializedName("abstract")
    val `abstract`: String,
    @SerializedName("byline")
    val byline: String,
    @SerializedName("created_date")
    val createdDate: String,
    @SerializedName("des_facet")
    val desFacet: List<String>,
    @SerializedName("geo_facet")
    val geoFacet: List<Any>,
    @SerializedName("item_type")
    val itemType: String,
    @SerializedName("kicker")
    val kicker: String,
    @SerializedName("material_type_facet")
    val materialTypeFacet: String,
    @SerializedName("multimedia")
    val multimedia: List<Multimedia>,
    @SerializedName("org_facet")
    val orgFacet: List<String>,
    @SerializedName("per_facet")
    val perFacet: List<Any>,
    @SerializedName("published_date")
    val publishedDate: String,
    @SerializedName("section")
    val section: String,
    @SerializedName("short_url")
    val shortUrl: String,
    @SerializedName("subsection")
    val subsection: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated_date")
    val updatedDate: String,
    @SerializedName("url")
    val url: String
)