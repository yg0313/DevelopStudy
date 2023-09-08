package study.datajpa.entity

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
class BaseEntity {

    @CreatedDate
    @Column(updatable = false)
    lateinit var createDate: LocalDateTime

    @LastModifiedDate
    lateinit var lastModifiedDate: LocalDateTime

    @CreatedBy
    @Column(updatable = false)
    lateinit var createdBy: String

    @LastModifiedBy
    lateinit var lastModifiedBy: String
}