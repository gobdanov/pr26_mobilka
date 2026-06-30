package domains.apis;

import datas.common.CheckInternet;
import domains.callbacks.MyResponseCallback;
import domains.models.User;

import com.google.gson.GsonBuilder;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;


public class UserLogin extends MyAsyncTask{
    private CheckInternet checkInternet;
    private User user;
    public UserLogin(User user, CheckInternet checkInternet, MyResponseCallback callback)
    {
        super(checkInternet, callback);
        this.user = user;
        this.checkInternet = checkInternet;
    }

    @Override
    protected String doInBackground(Void... voids){
        if(!checkInternet.isWiFiConnection() && !checkInternet.isMobileConnection())
            return "Error : no internet connection";
        String rawData = new GsonBuilder().create().toJson(this.user);

        try{
            Connection.Response response = Jsoup.connect("https://bloop-me.ru/student/api/user/login")
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .method(Connection.Method.POST)
                    .header("Content-type", "application/json")
                    .requestBody(rawData)
                    .execute();
            return response.statusCode() == 200? response.body() : "Error: " + response.body();
        }
        catch (IOException ex){
            return "Error: "+ ex.getMessage();
        }
    }
}
