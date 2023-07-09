package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class CheckedTest {

    Service service = null;

    @BeforeEach
    void setup(){
        service = new Service();
    }

    @Test
    @DisplayName("checked예외 처리")
    void checked_catch(){
        service.callCatch();
    }

    @Test
    @DisplayName("예외 던지기")
    void checked_throw() {
        Assertions.assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf(MyCheckedException.class);
    }

    /**
     * Exception을 상속받은 예외는 체크 예외가 된다.
     */
    static class MyCheckedException extends Exception{
        public MyCheckedException(String message) {
            super(message);
        }
    }

    /**
     * Checked 예외는
     * 예외를 잡아서 처리하거나, 던지거나 둘중 하나를 필수로 해야한다.
     */
    static class Service{
        Repository repository = new Repository();

        /**
         * 예외를 잡아서 처리하는 코드.
         */
         public void callCatch(){
             try {
                 repository.call();
             }catch (MyCheckedException e){
                 log.info("예외처리, message={}", e.getMessage(), e);
             }
         }

        /**
         * 체크 예외를 밖으로 던지는 코드.
         * 체크 예외는 예욀르 잡지 않고 밖으로 던지려면 throws 예외를 메소드에 필수로 선언해야한다.
         * @throws MyCheckedException
         */
         public void callThrow() throws MyCheckedException{
             repository.call();
         }
    }

    static class Repository{
        public void call() throws MyCheckedException{ //컴파일이 체크해주기때문에 던지든 처리를 하든 로직을 구성해야함.
            throw new MyCheckedException("ex");
        }
    }
}
