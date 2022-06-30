package hello.springCore_advanced.trace;

import lombok.Getter;

import java.util.UUID;

@Getter
public class TraceId {

    private String id; //트랜잭션ID
    private int level; //로직 깊이

    public TraceId() {
        this.id = createId();
        this.level = 0;
    }

    private TraceId(String id, int level) {
        this.id = id;
        this.level = level;
    }

    /**
     * @return 생성된 UUID 앞 8자리
     */
    private String createId(){
        return UUID.randomUUID().toString().substring(0,8); 
    }

    /**
     * 로직의 레벨 증가.
     * ex) controller level 1 -> service level 2.
     */
    public TraceId createNextId(){
        return new TraceId(id, level+1);
    }

    public TraceId createPreviousId(){
        return new TraceId(id, level -1);
    }

    public boolean isFirstLevel(){
        return level == 0;
    }

}
