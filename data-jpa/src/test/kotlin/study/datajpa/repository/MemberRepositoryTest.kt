package study.datajpa.repository

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import study.datajpa.entity.Member
import java.util.*

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Test
    fun testMember(){
        println("MemberRepositoryTest.testMember memberRepository:${memberRepository.javaClass}")
        val member = Member("memberA")
        val savedMember = memberRepository.save(member)

        val findMember = memberRepository.findById(savedMember.id!!).get()

        assertThat(findMember.id).isEqualTo(member.id)
        assertThat(findMember.username).isEqualTo(member.username)
        assertThat(findMember).isEqualTo(member)
    }

    @Test
    fun basicCRUD(){
        val member1 = Member("member1")
        val member2 = Member("member2")

        memberRepository.save(member1)
        memberRepository.save(member2)

        //단건 조회 검증.
        val findMember1 = memberRepository.findById(member1.id!!).get()
        val findMember2 = memberRepository.findById(member2.id!!).get()
        assertThat(findMember1).isEqualTo(member1)
        assertThat(findMember2).isEqualTo(member2)

        //리스트 조회 검증
        val all = memberRepository.findAll()
        assertThat(all.size).isEqualTo(2)

        //카운트 검증
        val count: Long = memberRepository.count()
        assertThat(count).isEqualTo(2L)

        //삭제 검증
        memberRepository.delete(member1)
        memberRepository.delete(member2)

        val deletedCount = memberRepository.count()
        assertThat(deletedCount).isEqualTo(0L)

    }

    @Test
    fun findByUsernameAndAgeGreaterThan(){
        val m1 = Member("AAA").apply {
            this.age = 10
        }

        val m2 = Member("AAA").apply {
            this.age = 20
        }
        memberRepository.save(m1)
        memberRepository.save(m2)

        val result = memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15)
        assertThat(result.get(0).username).isEqualTo("AAA")
        assertThat(result.get(0).age).isEqualTo(20)
        assertThat(result.size).isEqualTo(1)
    }

    @Test
    fun testQuery(){
        val m1 = Member("AAA").apply {
            this.age = 10
        }

        val m2 = Member("BBB").apply {
            this.age = 20
        }
        memberRepository.save(m1)
        memberRepository.save(m2)

        val result = memberRepository.findUser("AAA", 10)
        assertThat(result[0]).isEqualTo(m1)
    }
}