package com.yapp.buddycon.domain.model

data class MakeConDetail(
    val id  : Int =  -1,
    val imageUrl : String = "",
    val barcode: String = "",
    val name: String = "",
    val expireDate: String = "",
    val storeName: String = "",
    val sentMemberName: String = "",
    val memo: String = ""
)