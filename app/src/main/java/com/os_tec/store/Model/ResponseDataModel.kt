package com.os_tec.store.Model

import com.google.gson.annotations.SerializedName
import org.json.JSONObject
import java.io.Serializable
data class ResponseModel(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String
) : Serializable


data class ProductsResponseModel(
    @SerializedName("status") val status: Boolean,
    @SerializedName("failureMessage") val failureMessage: String, //from app
    @SerializedName("data") val data: DataObject,
    ) : Serializable


data class SliderResponseModel(
    @SerializedName("status") val status: Boolean,
    @SerializedName("data") val data: ArrayList<SliderModel>
    ) : Serializable


data class SearchResponseModel(
    @SerializedName("status") val status: Boolean,
    @SerializedName("data") val data: ArrayList<ProductsModel>
):Serializable

data class FavoriteResponseModel(
    @SerializedName("status") val status: Boolean,
    @SerializedName("data") val data: ArrayList<ProductsModel>,
    @SerializedName("user") val user: JSONObject
):Serializable


data class MyOrderResponseModel(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: ArrayList<MyOrderModel>,

):Serializable


data class DetailsResponseModel(
    @SerializedName("status") val status: Boolean,
    @SerializedName("data") val data: ProductsModel
):Serializable


data class CartResponseModel(
    @SerializedName("status") val status: Boolean,
    @SerializedName("data") val data: ArrayList<CartModel>
):Serializable


data class CategoriesResponseModel(
    @SerializedName("status") val status: Boolean,
    @SerializedName("data") val data: ArrayList<CategoryModel>,
    ) : Serializable

//--------------------------------------------------------------------------------------------------

data class DataObject(
    @SerializedName("categories") val categories: ArrayList<CategoryModel>,
    @SerializedName("featured_products") val featured_products: ArrayList<ProductsModel>,
    @SerializedName("best_sell_products") val best_sell_products: ArrayList<ProductsModel>,
) : Serializable


//----------------------------------------------------------------
data class ProductsModel(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: Int,
    @SerializedName("stock") val stock: Int,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("is_fav") val is_fav: Boolean,
    @SerializedName("attachments") val attachments: ArrayList<AttachmentsModel>,
    @SerializedName("category") val category: CategoryModel,

    ):Serializable

data class MyOrderModel(
    @SerializedName("id") val id: Int,
    @SerializedName("product") val product: ArrayList<ProductsModel>,
    @SerializedName("price") val price: Int,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("created_at") val created_at: String,

    ):Serializable

data class CartModel(
    @SerializedName("cart_id") val cart_id: Int,
    @SerializedName("product") val product: ProductsModel,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: Int,
    @SerializedName("rate") val rate: Int,
    @SerializedName("quantity") var quantity: Int,
    @SerializedName("review_count") val review_count: Int,
    @SerializedName("attachments") val attachments: ArrayList<AttachmentsModel>,
    ) : Serializable

data class AttachmentsModel(
    @SerializedName("id") val id: Int,
    @SerializedName("image") val image: String,
    @SerializedName("real_image") val real_image: String,
) : Serializable

//----------------------------------------------------------------
data class CategoryModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String
) : Serializable

data class SliderModel(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("image") val image: String,
) : Serializable