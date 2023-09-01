package study.datajpa.repository

import org.springframework.stereotype.Repository
import study.datajpa.entity.Member
import java.util.Optional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class MemberJpaRepository {

    @PersistenceContext
    private lateinit var em:EntityManager

    fun save(member: Member): Member{
        em.persist(member)
        return member
    }

    fun delete(member: Member){
        em.remove(member)
    }

    fun findAll(): List<Member>{
        //jpql 사용
        return em.createQuery("select m from Member m", Member::class.java)
                .resultList
    }

    fun findById(id: Long): Optional<Member>{
        val member = em.find(Member::class.java, id)
        return Optional.ofNullable(member)
    }

    fun count(): Long{
        return em.createQuery("select count(m) from Member m", Long::class.javaObjectType)
                .singleResult
    }

    fun find(id: Long): Member{
        return em.find(Member::class.java, id)
    }

    fun findByUsernameAndAgeGreaterThan(username: String, age: Int): MutableList<Member>? {
        return em.createQuery("select m from Member m where m.username = :username and m.age > :age")
                .setParameter("username", username)
                .setParameter("age", age)
                .resultList as MutableList<Member>?
    }

    fun findByPage(age: Int, offset: Int, limit: Int): MutableList<Member>{
        return em.createQuery("select m from Member m where m.age = :age order by m.username desc")
                .setParameter("age", age)
                .setFirstResult(offset) //페이지
                .setMaxResults(limit) //가져올 데이터 갯수
                .resultList as MutableList<Member>
    }

    fun totalCount(age: Int): Long{
        return em.createQuery("select count(m) from Member m where m.age = :age", Long::class.javaObjectType)
                .setParameter("age", age)
                .singleResult
    }
}