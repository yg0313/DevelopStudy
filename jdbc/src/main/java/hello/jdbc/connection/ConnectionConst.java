package hello.jdbc.connection;

/**
 * 상수를 모아둔것이기 때문에 객체 생성 막기위해 추상클래스 선언.
 */
public abstract class ConnectionConst {
    static final String URL = "jdbc:h2:tcp://localhost/~/test";
    static final String USERNAME = "sa";
    static final String PASSWORD = "";
}
