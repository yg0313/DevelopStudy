package com.hello.aop.exam;

import com.hello.aop.exam.annotation.Trace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.annotation.Target;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;

    @Trace
    public void request(String itemId){
        examRepository.save(itemId);
    }
}
