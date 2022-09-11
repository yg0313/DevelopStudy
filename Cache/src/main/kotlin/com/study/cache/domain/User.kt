package com.study.cache.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("user")
data class User(val name:String , val phone:String){

    @JsonIgnore
    private lateinit var id : ObjectId
    var userId : String = UUID.randomUUID().toString().substring(0,10)
}
