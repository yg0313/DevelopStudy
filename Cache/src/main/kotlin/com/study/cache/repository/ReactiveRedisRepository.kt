package com.study.cache.repository

import com.study.cache.domain.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class ReactiveRedisRepository(private val template : ReactiveMongoTemplate)  {

    fun addUser(user:User) = template.save(user)

}