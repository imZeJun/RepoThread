package browser.android.com.repothread;

/**
 * @author lizejun
 * @version 1.0 2016/11/17
 */
public class ThreadLocalObject extends ThreadLocal<String> {
    @Override
    protected String initialValue() {
        return "Thread Name=";
    }
}
