package study.datajpa.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.*
import org.springframework.data.repository.query.Param
import study.datajpa.dto.MemberDto
import study.datajpa.entity.Member
import java.util.*
import javax.persistence.LockModeType
import javax.persistence.QueryHint

interface MemberRepository: JpaRepository<Member, Long>, MemberRepositoryCustom {

    fun findByUsername(username: String): List<Member>

    fun findByUsernameAndAgeGreaterThan(username: String, age: Int): List<Member>

    @Query("select m from Member m where m.username = :username and m.age = :age")
    fun findUser(@Param("username") username: String, @Param("age")age: Int): List<Member>

    @Query("select m.username from Member m")
    fun findUsernameList(): List<String>

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    fun findMemberDto(): List<MemberDto>

    @Query("select m from Member m where m.username in :names")
    fun findByNames(@Param("names") names: Collection<String>):List<Member>

    fun findListByUsername(username: String): List<Member>
    fun findMemberByUsername(username: String): Member
    fun findOptionalByUsername(username: String): Optional<Member>

    /**
     * 데이터의 전체 갯수를 가져올때, join이 필요없는 경우 query와 countQuery를 
     * 분리하여 작성함으로 성능 최적화를 만들수있다.
     */
    @Query(value = "select m from Member m left join m.team t",
            countQuery = "select count(m) from Member m")
    fun findByAge(age: Int, pageable: Pageable): Page<Member>

    @Modifying(clearAutomatically = true) //update 실행시 필요. clearAutomatically -> 영속성 컨텍스트 클리어.
    @Query("update Member m set m.age = m.age + 1 where m.age>= :age")
    fun bulkAgePlus(@Param("age") age: Int): Int

    @Query("select m from Member m left join fetch m.team")
    fun findMemberFetchJoin(): List<Member>

    @EntityGraph(attributePaths = ["team"])
    override fun findAll(): MutableList<Member>

    /**
     * override fun findAll()와 결과값 동일.
     */
    @EntityGraph(attributePaths = ["team"])
    @Query("select m from Member m")
    fun findMemberEntityGraph(): MutableList<Member>

    @QueryHints(value = [QueryHint(name = "org.hibernate.readOnly", value = "true")])
    fun findReadOnlyByUsername(username: String): Member

    //비관적 락 jpa 버전
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findLockByUsername(username: String): List<Member>
}