package study.datajpa.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import study.datajpa.entity.Member
import study.datajpa.repository.MemberRepository
import javax.annotation.PostConstruct

@RestController
class MemberController(private val memberRepository: MemberRepository) {

    @GetMapping("/members/{id}")
    fun findMember(@PathVariable("id")id: Long): String{
        val member = memberRepository.findById(id).get()
        return member.username
    }

    @GetMapping("/members2/{id}")
    fun findMember2(@PathVariable("id")member: Member): String{
        return member.username
    }

    @PostConstruct
    fun init(){
        memberRepository.save(Member("member1"))
    }
}