package toby;

import kotlin.properties.ObservableProperty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ob {

    /**
     * 과거의 observable
     * 1. complete -> 알림이 없음.
     * 2. error -> 예외처리 방식이 가이드되어 있지않았음.
     */

    @Test
    @DisplayName("observable")
    public void test(){
        Observer ob = new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println(Thread.currentThread().getName() + " " + arg);
            }
        };

        IntObservable io = new IntObservable();
        io.addObserver(ob);

        ExecutorService ex = Executors.newSingleThreadExecutor();
        ex.execute(io);
        System.out.println(Thread.currentThread().getName() + " exit");

        io.run();
    }


    static class IntObservable extends Observable implements Runnable{

        @Override
        public void run() {
            for(int i = 1; i<=10; i++){
                setChanged();
                notifyObservers(i); //데이터 전송  //push
                // int i =  Iterator().next()    //pull
            }
        }
    }
}
