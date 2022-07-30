package com.example.pixabay44.model

data class PixabayModel (

    val total:Int,
    val totalHits:Int,
    val hits:List<ImageModel>
        )

data class ImageModel(
   val largeImageURL:String
)