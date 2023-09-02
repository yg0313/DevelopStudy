package study.datajpa.dto

class MemberDto (
        val id: Long,
        val username: String,
        val teamName: String?
){

    override fun toString(): String {
        return "MemberDto(id=$id, username='$username', teamName='$teamName')"
    }
}