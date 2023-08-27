package study.datajpa.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import study.datajpa.entity.Member

interface MemberRepository: JpaRepository<Member, Long> {

    fun findByUsername(username: String): List<Member>

    fun findByUsernameAndAgeGreaterThan(username: String, age: Int): List<Member>

    @Query("select m from Member m where m.username = :username and m.age = :age")
    fun findUser(@Param("username") username: String, @Param("age")age: Int): List<Member>
}