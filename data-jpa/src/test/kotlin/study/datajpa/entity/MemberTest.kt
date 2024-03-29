package study.datajpa.entity

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import study.datajpa.repository.MemberRepository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@SpringBootTest
@Transactional
@Rollback(false)
class MemberTest{

    @PersistenceContext
    lateinit var em: EntityManager

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Test
    fun testEntity(){
        val teamA = Team("teamA")
        val teamB = Team("teamB")
        em.persist(teamA)
        em.persist(teamB)

        val member1 = Member("member1").apply {
            this.age = 10
            this.team = teamA
        }
        val member2 = Member("member1").apply {
            this.age = 20
            this.team = teamA
        }
        val member3 = Member("member1").apply {
            this.age = 30
            this.team = teamB
        }
        val member4 = Member("member1").apply {
            this.age = 40
            this.team = teamB
        }

        em.persist(member1)
        em.persist(member2)
        em.persist(member3)
        em.persist(member4)

        //초기화
        em.flush()
        em.clear()

        //확인
        val members: List<Member> = em.createQuery("select m from Member m", Member::class.java)
                .resultList

        members.forEach {member ->
            println("MemberTest.testEntity member:$member")
            println("MemberTest.testEntity member.team:${member.team}")
        }
    }

    @Test
    fun jpaEventBaseEntity(){
        //given
        val member = Member("member1")
        memberRepository.save(member) //@PrePersist 실행

        Thread.sleep(100)
        member.username = "member2"

        em.flush() //@PreUpdate
        em.clear()
        //when
        val findMember = memberRepository.findById(member.id!!).get()

        //then
        println("findMember.createDate = ${findMember.createDate}")
        println("findMember.updateDate = ${findMember.lastModifiedDate}")
        println("findMember.createdBy = ${findMember.createdBy}")
        println("findMember.lastModifiedBy = ${findMember.lastModifiedBy}")
    }
}