package study.datajpa.entity

import java.util.ArrayList
import javax.persistence.*

@Entity
class Team(
    val name: String
) {

    @Id @GeneratedValue
    @Column(name="team_id")
    var id: Long? = null

    /**
     * 리스트 타입 문제.
     * https://medium.com/@SungMinLee/spring-boot-kotlin-%EC%97%90%EC%84%9C-onetomany-relationship-%EC%97%90%EC%84%9C-%EB%B0%9C%EC%83%9D%ED%95%98%EB%8A%94-%EB%AC%B8%EC%A0%9C-4d84e4875986
     */
    @OneToMany(mappedBy="team") //foreignKey가 없는 부분 mappedBy 권장.
    var members: MutableList<Member> = ArrayList()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Team

        if (name != other.name) return false
        if (members != other.members) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + members.hashCode()
        result = 31 * result + (id?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Team(name='$name', id=$id)"
    }

}