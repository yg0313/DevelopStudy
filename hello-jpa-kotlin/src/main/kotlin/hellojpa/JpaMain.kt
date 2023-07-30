package hellojpa

import javax.persistence.Persistence

fun main(){
    val emf = Persistence.createEntityManagerFactory("hello") //persistence.xml persistent-unit name
    val entityManager = emf.createEntityManager()

    val tx = entityManager.transaction
    tx.begin()
    try{
        
        //멤버 등록
//        val member = Member(2L).apply {
//            this.name = "helloB"
//        }

        /**
         * 멤버 수정.
         * 자바 컬렉션 다루듯 수정 가능.
         * JPA가 관리, 트랜잭션 커밋 시점에 확인을 함.
         */
//        val member = entityManager.find(Member::class.java, 1L)
//            .apply {
//                this.name = "멤버 수정"
//            }

        /**
         * jpql -> 엔티티 객체를 대상으로 쿼리.
         * sql -> 데이터베이스 테이블을 대상으로 쿼리.
         * 테이블이 아닌 객체를 대상으로 검색하는 개체 지향 쿼리
         *
         */
        val findMembers = entityManager.createQuery("select m from Member as m", Member::class.java)
            .setFirstResult(5)
            .setMaxResults(8)
            .resultList

        findMembers.forEach { member->
            println("<top>.main member:${member.name}")
        }

//        entityManager.persist(member)

        tx.commit()
    }catch (e: Exception){
        tx.rollback()
    }finally {
        entityManager.close()
    }

    emf.close()
}
