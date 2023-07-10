package hello.jdbc.repository;

import hello.jdbc.domain.Member;

public interface MemberRepositoryEx {
    Member save(Member member);
    Member findById(String memberId);
    void update(String memberId, int moneoy);
    void delete(String memberId);
}
