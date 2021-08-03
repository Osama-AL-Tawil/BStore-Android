package com.os_tec.store.Model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProductsResponseModel(
    @SerializedName("status") val status: Boolean,
    @SerializedName("data") val data: DataObject,
    ) : Serializable


data class SearchResponseModel(
    @SerializedName("status") val status: Boolean,
    @SerializedName("data") val data: ArrayList<ProductsDataModel>
):Serializable


data class DetailsResponseModel(
    @SerializedName("status") val status: Boolean,
    @SerializedName("data") val data: ProductsDataModel
):Serializable


data class CategoriesResponseModel(
    @SerializedName("status") val status: Boolean,
    @SerializedName("data") val data: ArrayList<CategoryObject>,
    ) : Serializable

//--------------------------------------------------------------------------------------------------

data class DataObject(
    @SerializedName("categories") val categories: ArrayList<CategoryObject>,
    @SerializedName("featured_products") val featured_products: ArrayList<ProductsDataModel>,
    @SerializedName("best_sell_products") val best_sell_products: ArrayList<ProductsDataModel>,
) : Serializable


//----------------------------------------------------------------
data class ProductsDataModel(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: Int,
    @SerializedName("stock") val stock: Int,
    @SerializedName("is_fav") val is_fav: Boolean,
    @SerializedName("attachments") val attachments: ArrayList<AttachmentsData>,
    @SerializedName("category") val category: CategoryObject,


    ) : Serializable

data class AttachmentsData(
    @SerializedName("id") val id: Int,
    @SerializedName("image") val image: String,
    @SerializedName("real_image") val real_image: String,
) : Serializable

//----------------------------------------------------------------
data class CategoryObject(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String
) : Serializable