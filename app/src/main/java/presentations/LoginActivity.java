package presentations;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;

import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.pr4_x3.R;

import classes.BiometricHelper;
import datas.common.CheckInternet;
import domains.apis.UserLogin;
import domains.callbacks.MyResponseCallback;
import domains.models.User;

public class LoginActivity extends AppCompatActivity {

    LoginActivity activity;
    public Context context;
    BiometricPrompt.AuthenticationCallback callback = new

            BiometricPrompt.AuthenticationCallback() {

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                }

                @Override
                public void onAuthenticationError(int errorCode, CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                }

                @Override
                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    Toast.makeText(activity.getApplicationContext(), "авторизация пройдена", Toast.LENGTH_SHORT).show();
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        activity = this;

            TextView bthOpenSingIn = findViewById(R.id.btn_reg_in_main_page); // находим кнопку "зарегистрироваться"
            // подписываемся на событие нажатия
            bthOpenSingIn.setOnClickListener(v -> {
                // создаем интент, который запустит активность регистрации
                Intent SingIn = new Intent(this, MainActivity.class);
                startActivity(SingIn); // запускаем созданный интент
            });

            Button bthLogIn = findViewById(R.id.btn_log_in_main_page); // находим кнопку "avtorizirovatsya"
            // подписываемся на событие нажатия
            bthLogIn.setOnClickListener(v -> {
                TextView etEmail = findViewById(R.id.et_email_main_page); // находим текстовое поле почты
                TextView etPassword = findViewById(R.id.et_password_main_page); // находим текстовое поле пароля

                if (BiometricHelper.canAutentificate){
                    activity = this;
                    Log.d("act", activity.toString());
                    BiometricHelper biometricHelper = new BiometricHelper(this, callback);
                    biometricHelper.show();
                }
                else{
                    String email = etEmail.getText().toString(); // получаем введенное значение почты
                    String password = etPassword.getText().toString(); // получаем введенное значение пароля

                    if(email.isEmpty()) { // проверяем почту на пустое значение
                        // выводим уведомление о том что почта не заполнена
                        Toast.makeText(this, "не указана почта пользователя", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(password.isEmpty()) { // проверяем пароль на пустое значение
                        // выводим уведомление о том что пароль не заполнен
                        Toast.makeText(this, "не указан пароль пользователя", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // выводим уведомление о том что пользователь авторизован
                    RequestUserLogin(email, password);
                }
            });
    }
    public void perehod_slave(View view){
        setContentView(R.layout.activity_main);
    }

    public void RequestUserLogin(String email, String password){
        Context context = this;
        CheckInternet checkInternet = new CheckInternet(this);
        User user = new User();
        user.email = email;
        user.password = password;
        UserLogin RequestUserLogin = new UserLogin(
                user,
                checkInternet,
                new MyResponseCallback() {
                    @Override
                    public void OnCompile(String result) {
                        Log.d("USER LOGIN", result);
                        Toast.makeText(activity.getApplicationContext(), "!!ПОЛЬЗОВАТЕЛЬ АВТОРИЗОВАН!!", Toast.LENGTH_SHORT).show();
                        BiometricHelper.canAutentificate = true;
                    }

                    @Override
                    public void OnError(String error) {
                        Log.d("USER LOGIN", error);
                        Toast.makeText(activity.getApplicationContext(), "ошибка авторизации...", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        RequestUserLogin.execute();
    }
}