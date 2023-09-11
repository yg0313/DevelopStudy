package study.datajpa.dto


data class MemberDto (
        val id: Long,
        val username: String,
        val teamName: String?
)
//TODO 자바의 메소드 레퍼런스 식처럼 만들어보기.