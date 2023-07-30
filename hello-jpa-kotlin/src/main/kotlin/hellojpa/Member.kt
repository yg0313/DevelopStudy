package hellojpa

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Member(
    @Id
    val id: Long
) {
    constructor() : this(0L)

    var name: String = ""
}