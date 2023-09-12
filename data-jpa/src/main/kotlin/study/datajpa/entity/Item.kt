package study.datajpa.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.domain.Persistable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.Id

@Entity
@EntityListeners(AuditingEntityListener::class)
class Item(
        @Id
        @get:JvmName("getItemId")
        var id: String? = null
): Persistable<String>{

        @CreatedDate
        var createdDate: LocalDateTime? = null

        override fun getId(): String? {
                return id
        }

        override fun isNew(): Boolean {
                return createdDate == null
        }
}