package study.datajpa.repository

import org.springframework.data.jpa.domain.Specification
import org.springframework.util.StringUtils
import study.datajpa.entity.Member
import study.datajpa.entity.Team
import javax.persistence.criteria.Join
import javax.persistence.criteria.JoinType

class MemberSpec {

    companion object{
        fun teamName(teamName: String): Specification<Member>{
            return Specification { root, query, builder ->
                if (StringUtils.isEmpty(teamName)) {
                    null
                } else {
                    val t: Join<Member, Team> = root.join("team", JoinType.INNER)
                    builder.equal(t.get<String>("name"), teamName)
                }
            }
        }

        fun username(username: String): Specification<Member>{
            return Specification { root, query, builder ->
                builder.equal(root.get<String>("username"), username)
            }
        }
    }
}