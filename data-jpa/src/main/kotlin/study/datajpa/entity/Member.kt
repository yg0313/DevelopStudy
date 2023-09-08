package study.datajpa.entity

import javax.persistence.*

@Entity
class Member(
    var username: String,
): JpaBaseEntity() {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    var id: Long? = null
    var age: Int = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    lateinit var team: Team

    fun changeTeam(team: Team){
        this.team = team
        team.members.add(this)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Member

        if (username != other.username) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }

    override fun toString(): String {
        return "Member(username='$username', age=$age, id=$id)"
    }

}