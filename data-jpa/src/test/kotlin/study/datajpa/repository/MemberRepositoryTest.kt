package study.datajpa.repository

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.data.domain.Sort
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import study.datajpa.dto.MemberDto
import study.datajpa.entity.Member
import study.datajpa.entity.Team
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

    @Autowired
    lateinit var memberRepository: MemberRepository
    @Autowired
    lateinit var teamRepository: TeamRepository
    @PersistenceContext
    lateinit var entityManager: EntityManager

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

    @Test
    fun findUsernameList(){
        val m1 = Member("AAA").apply {
            this.age = 10
        }

        val m2 = Member("BBB").apply {
            this.age = 20
        }
        memberRepository.save(m1)
        memberRepository.save(m2)

        val result = memberRepository.findUsernameList()
        result.forEach {member ->
            println("member: $member")
        }
    }

    @Test
    fun findMemberDto(){
        val team = Team("teamA")
        teamRepository.save(team)

        val m1 = Member("AAA").apply {
            this.age = 10
            this.team = team
        }
        memberRepository.save(m1)


        val memberDto = memberRepository.findMemberDto()
        memberDto.forEach { dto ->
            println("dto: $dto")
        }
    }

    @Test
    @DisplayName("컬렉션을 파라미터로 넘겨서 멤버 리스트 조회하기")
    fun findByNames(){
        val m1 = Member("AAA").apply {
            this.age = 10
        }

        val m2 = Member("BBB").apply {
            this.age = 20
        }
        memberRepository.save(m1)
        memberRepository.save(m2)

        val result = memberRepository.findByNames(listOf("AAA", "BBB"))
        result.forEach {member ->
            println("member: $member")
        }
    }

    @Test
    fun returnType(){
        val m1 = Member("AAA").apply {
            this.age = 10
        }

        val m2 = Member("BBB").apply {
            this.age = 20
        }
        memberRepository.save(m1)
        memberRepository.save(m2)

        val aaa = memberRepository.findListByUsername("AAA")
        println("aaa = ${aaa}")
        val findMember = memberRepository.findMemberByUsername("AAA")
        println("findMember = ${findMember}")
        val optionalMember = memberRepository.findOptionalByUsername("AAA")
        println("optionalMember = ${optionalMember}")
    }

    @Test
    fun paging() {
        val m1 = Member("member1").apply {
            this.age = 10
        }
        val m2 = Member("member2").apply {
            this.age = 10
        }
        val m3 = Member("member3").apply {
            this.age = 10
        }
        val m4 = Member("member4").apply {
            this.age = 10
        }
        val m5 = Member("member5").apply {
            this.age = 10
        }

        memberRepository.save(m1)
        memberRepository.save(m2)
        memberRepository.save(m3)
        memberRepository.save(m4)
        memberRepository.save(m5)

        val age  = 10
        val pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"))

        val page: Page<Member> = memberRepository.findByAge(age, pageRequest)

        val toMap: Page<MemberDto> = page.map { member ->
            MemberDto(member.id!!, member.username, null)
        }

        val content = page.content
        val totalElements = page.totalElements //totalCount

        assertThat(content.size).isEqualTo(3)
        assertThat(page.totalElements).isEqualTo(totalElements)
        assertThat(page.number).isEqualTo(0)
        assertThat(page.totalPages).isEqualTo(2)
        assertThat(page.isFirst).isTrue()
        assertThat(page.hasNext()).isTrue()
    }

    @Test
    fun slice() {
        val m1 = Member("member1").apply {
            this.age = 10
        }
        val m2 = Member("member2").apply {
            this.age = 10
        }
        val m3 = Member("member3").apply {
            this.age = 10
        }
        val m4 = Member("member4").apply {
            this.age = 10
        }
        val m5 = Member("member5").apply {
            this.age = 10
        }

        memberRepository.save(m1)
        memberRepository.save(m2)
        memberRepository.save(m3)
        memberRepository.save(m4)
        memberRepository.save(m5)

        val age  = 10
        val pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"))

        val page: Slice<Member> = memberRepository.findByAge(age, pageRequest)

        val content = page.content

        assertThat(content.size).isEqualTo(3)
        assertThat(page.number).isEqualTo(0)
        assertThat(page.isFirst).isTrue()
        assertThat(page.hasNext()).isTrue()
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

        memberRepository.save(m1)
        memberRepository.save(m2)
        memberRepository.save(m3)
        memberRepository.save(m4)
        memberRepository.save(m5)

        val resultCount = memberRepository.bulkAgePlus(20)
        var result = memberRepository.findByUsername("member5")
        var member5 = result[0]
        println("member5 = ${member5}") // age가 벌크연산이 일어나서 업데이트 되지 않은 값(40)으로 그대로 나옴 -> 영속성 컨텍스트에서 관리하는 m5의 객체값은 그대로이기때문.


        entityManager.flush()
        entityManager.clear()

        result = memberRepository.findByUsername("member5")
        member5 = result[0]
        //entityManager를 통해 영속성 컨텍스트를 clear 이후 다시 가져옴으로써 수정된 데이터 확인가능.
        //mybatis와 같이 쓸때도 마찬 가지 작업 필요.
        println("member5 = ${member5}") 

        assertThat(resultCount).isEqualTo(3)
    }
}