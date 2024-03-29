package study.datajpa.repository

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import study.datajpa.entity.Member

@SpringBootTest
@Transactional
@Rollback(false)
internal class MemberJpaRepositoryTest{

    @Autowired
    lateinit var memberJpaRepository: MemberJpaRepository

    @Test
    fun testMember(){
        val member = Member("memberA")

        val saveMember = memberJpaRepository.save(member)

        val findMember = memberJpaRepository.find(saveMember.id!!)

        assertThat(findMember.id).isEqualTo(member.id)
        assertThat(findMember.username).isEqualTo(member.username)
        assertThat(findMember).isEqualTo(member)
    }

    @Test
    fun basicCRUD(){
        val member1 = Member("member1")
        val member2 = Member("member2")

        memberJpaRepository.save(member1)
        memberJpaRepository.save(member2)

        //단건 조회 검증.
        val findMember1 = memberJpaRepository.findById(member1.id!!).get()
        val findMember2 = memberJpaRepository.findById(member2.id!!).get()
        assertThat(findMember1).isEqualTo(member1)
        assertThat(findMember2).isEqualTo(member2)

        //리스트 조회 검증
        val all = memberJpaRepository.findAll()
        assertThat(all.size).isEqualTo(2)

        //카운트 검증
        val count: Long = memberJpaRepository.count()
        assertThat(count).isEqualTo(2L)

        //삭제 검증
        memberJpaRepository.delete(member1)
        memberJpaRepository.delete(member2)

        val deletedCount = memberJpaRepository.count()
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
        memberJpaRepository.save(m1)
        memberJpaRepository.save(m2)

        val result = memberJpaRepository.findByUsernameAndAgeGreaterThan("AAA", 15)
        assertThat(result?.get(0)?.username).isEqualTo("AAA")
        assertThat(result?.get(0)?.age).isEqualTo(20)
        assertThat(result?.size).isEqualTo(1)
    }

    @Test
    fun paging() {
        val m1 = Member("AAA").apply {
            this.age = 10
        }
        val m2 = Member("AAA").apply {
            this.age = 10
        }
        val m3 = Member("AAA").apply {
            this.age = 10
        }
        val m4 = Member("AAA").apply {
            this.age = 10
        }
        val m5 = Member("AAA").apply {
            this.age = 10
        }

        memberJpaRepository.save(m1)
        memberJpaRepository.save(m2)
        memberJpaRepository.save(m3)
        memberJpaRepository.save(m4)
        memberJpaRepository.save(m5)

        val age = 10;
        val offset = 0;
        val limit = 3;

        val members = memberJpaRepository.findByPage(age, offset, limit)
        val totalCount = memberJpaRepository.totalCount(age)

        assertThat(members.size).isEqualTo(3)
        assertThat(totalCount).isEqualTo(5)
    }

    @Test
    fun bulkUpdate(){
        val m1 = Member("member1").apply {
            this.age = 10
        }
        val m2 = Member("member2").apply {
            this.age = 19
        }
        val m3 = Member("member3").apply {
            this.age = 20
        }
        val m4 = Member("member4").apply {
            this.age = 21
        }
        val m5 = Member("member5").apply {
            this.age = 40
        }

        memberJpaRepository.save(m1)
        memberJpaRepository.save(m2)
        memberJpaRepository.save(m3)
        memberJpaRepository.save(m4)
        memberJpaRepository.save(m5)

        val resultCount = memberJpaRepository.bulkAgePlus(20)

        assertThat(resultCount).isEqualTo(3)
    }
}