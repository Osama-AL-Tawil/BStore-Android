package com.os_tec.store.Model

import java.io.Serializable

data class ItemsDataModel(val title:String,
                          val description:String,
                          val price:String,
                          val rate:Double,
                          val reviews:Int,
                          val evaluation:String,
                          val image:Int,
                          val sizeArray:ArrayList<SizeDataModel>):Serializable
