import com.albertik.thread.MainThread;
import com.albertik.utils.MessageManager;
import com.albertik.utils.yandex.SpellChecker;
import com.perm.kate.api.*;
import org.json.JSONException;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by Beta on 2/1/15.
 */
public class runner {
    private static Api api;
    private static String accessToken;
    private static String updateToken;
    private static long updateTime;
    private static ArrayList<Message> messages = null;
    private static Message message = null;

    public static void propertyLoad() throws Exception {
        Properties prop = new Properties();
        File propertieFile = new File("./settings.ini");
        InputStream inputStream = new FileInputStream(propertieFile);
        prop.load(inputStream);
        accessToken = prop.getProperty("access_token");
        updateTime = Long.parseLong(prop.getProperty("update_time"));
        updateToken = prop.getProperty("update_token");
    }

    public static void showDialogAllert(String title, String text) {
        System.out.println(title+"<>"+text);
    }

    public static void checkSettingFile() {
        File file = new File("./settings.ini");
        try {
            file.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }



    public static void main(String[] args) throws InterruptedException {
        try {
            propertyLoad();
        } catch (Exception e) {
            checkSettingFile();
        }

        api = new Api(accessToken, "5.26");
        MessageManager manager = new MessageManager(api);
        MainThread mainThread = new MainThread(manager,50000,20000);
        mainThread.start();



    }
}
