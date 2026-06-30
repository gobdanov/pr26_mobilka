package domains.callbacks;

public interface MyResponseCallback {
    void OnCompile(String result);
    void OnError(String error);
}
