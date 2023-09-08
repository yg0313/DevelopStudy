package study.datajpa.entity

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist
import javax.persistence.PreUpdate

@MappedSuperclass
class JpaBaseEntity {
    @Column(updatable = false)
    lateinit var createDate: LocalDateTime
    lateinit var updateDate: LocalDateTime

    @PrePersist //persist 전 이벤트 발생
    fun prePersist(){
        val now = LocalDateTime.now()
        createDate = now
        updateDate = now
    }

    @PreUpdate
    fun preUpdate (){
        updateDate = LocalDateTime.now()
    }
}