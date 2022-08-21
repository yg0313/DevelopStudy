package com.junit.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest // DB와 관련된 컴포넌트만 메모리에 로딩
public class BookRepositoryTest{

    @Autowired // DI
    private BookRepository bookRepository;

    // 1. 책 등록
    @Test
    @DisplayName("책 등록 테스트")
    public void saveTest(){
        System.out.println("책 등록 테스트 실행");
    }

    // 2. 책 목록조회

    // 3. 책 한권조회

    // 4. 책 수정

    // 5. 책 삭제
}
