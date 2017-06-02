package netUtils;

import java.io.DataOutputStream;

/**
 * Created by Виктория on 31.03.2017.
 */
public interface MessageHandler {
    void setId(int id);
    void setDataOutputStream(DataOutputStream dataOutputStream);
    String handle(String message);
    void disconnect();
}
