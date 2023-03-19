package toby;


import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.TimeUnit;

/**
 * Reactive Streams.
 */
public class PubSub {
    public static void main(String[] args) throws InterruptedException {
        Iterable<Integer> itr = Arrays.asList(1, 2, 3, 4, 5);

        //비동기 처리를 위한 쓰레드 생성.
        ExecutorService es = Executors.newCachedThreadPool();

        //Publisher <- Observable
        //데이터를 주는쪽.
        Flow.Publisher p = new Flow.Publisher() {
            @Override
            public void subscribe(Flow.Subscriber subscriber) {
                Iterator<Integer> it = itr.iterator();

                subscriber.onSubscribe(new Flow.Subscription() {
                    //backpressure 데이터 요청 속도 조절.
                    //n개 만큼의 데이터수를 요청.
                    @Override
                    public void request(long n) {
                        es.execute(() -> {
                            int i = 0;
                            try{
                                while(i++ < n){
                                    if(it.hasNext()){
                                        subscriber.onNext(it.next());
                                    }else{
                                        subscriber.onComplete();
                                        break;
                                    }
                                }
                            }catch (RuntimeException e){
                                subscriber.onError(e); // throw 대신 사용.
                            }
                        });

                    }

                    @Override
                    public void cancel() {

                    }
                });
            }
        };

        //Subscriber <- Observer
        Flow.Subscriber<Integer> s = new Flow.Subscriber<Integer>() {
            Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                System.out.println("PubSub.onSubscribe");

                this.subscription = subscription;
                //publisher에게 데이터 요청
                subscription.request(1);
            }

            //데이터를 받아서 처리하는 메소드.
            @Override
            public void onNext(Integer item) {
                System.out.println(Thread.currentThread().getName() + " PubSub.onNext " + item);

                //onSubscribe의 요청이 끝난 경우 재요청
                subscription.request(1);
            }

            /**
             * 에러 처리, try-catch 역할.
             * throw 사용 X.
             */

            @Override
            public void onError(Throwable throwable) {
                System.out.println("PubSub.onError: " + throwable.getMessage());
            }

            //publisher의 데이터 발행 완료.
            @Override
            public void onComplete() {
                System.out.println("PubSub.onComplete");
            }
        };

        p.subscribe(s); //구독

        es.awaitTermination(10, TimeUnit.HOURS);
        es.shutdown();
    }
}
