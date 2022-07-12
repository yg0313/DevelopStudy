package hello.springCore_advanced.trace.callback;

public interface TraceCallback<T> {

    T call();
}
