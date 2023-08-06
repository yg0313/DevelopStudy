package study.datajpa.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Member(
    val username: String
) {

    @Id @GeneratedValue
    var id: Long = 1L

    constructor() : this("")

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


}