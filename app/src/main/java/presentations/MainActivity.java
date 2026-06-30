package presentations;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pr4_x3.R;

import classes.BiometricHelper;
import datas.common.CheckInternet;
import domains.apis.UserLogin;
import domains.apis.UserReg;
import domains.callbacks.MyResponseCallback;
import domains.models.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView bthOpenRegIn = findViewById(R.id.bt_reg_in_slave_page); // находим кнопку "зарегистрироваться"
        // подписываемся на событие нажатия
        bthOpenRegIn.setOnClickListener(v -> {
            // создаем интент, который запустит активность регистрации
            TextView etEmail = findViewById(R.id.et_email_slave_page); // находим текстовое поле почты
            TextView etSurname = findViewById(R.id.ed_surname_slave_page);
            TextView etName =  findViewById(R.id.et_name_slave_page);
            TextView etLastname = findViewById(R.id.et_lastname_slave_page);
            Spinner spSex =  findViewById(R.id.sp_sex_slave_page);
            TextView etPassword =  findViewById(R.id.et_password_slave_page);


            String email = etEmail.getText().toString(); // получаем введенное значение почты
            String surname = etSurname.getText().toString();
            String name = etName.getText().toString();
            String lastname = etLastname.getText().toString();
            String sex =  spSex.getSelectedItem().toString();
            String password = etPassword.getText().toString(); // получаем введенное значение пароля

            if(email.isEmpty()) { // проверяем почту на пустое значение
                // выводим уведомление о том что почта не заполнена
                Toast.makeText(this, "не указана почта пользователя", Toast.LENGTH_SHORT).show();
                return;
            }

            else if(surname.isEmpty()) { // проверяем почту на пустое значение
                // выводим уведомление о том что почта не заполнена
                Toast.makeText(this, "не указана фамилия пользователя", Toast.LENGTH_SHORT).show();
                return;
            }

            else if(name.isEmpty()) { // проверяем почту на пустое значение
                // выводим уведомление о том что почта не заполнена
                Toast.makeText(this, "не указано имя пользователя", Toast.LENGTH_SHORT).show();
                return;
            }

            else if(lastname.isEmpty()) { // проверяем почту на пустое значение
                // выводим уведомление о том что почта не заполнена
                Toast.makeText(this, "не указано отчество пользователя", Toast.LENGTH_SHORT).show();
                return;
            }

            else if(sex.isEmpty()) { // проверяем почту на пустое значение
                // выводим уведомление о том что почта не заполнена
                Toast.makeText(this, "не указан пол пользователя", Toast.LENGTH_SHORT).show();
                return;
            }

            else if(password.isEmpty()) { // проверяем пароль на пустое значение
                // выводим уведомление о том что пароль не заполнен
                Toast.makeText(this, "не указан пароль пользователя", Toast.LENGTH_SHORT).show();
                return;
            }
            Integer sex1 = 0;
            if (sex == "мужской"){
                sex1 = 0;
            }
            else{
                sex1 = 1;
            }
            RequestUserReg(email,password,"firstname",lastname,surname,sex1);
        });
    }
    public void perehod(View view){
        setContentView(R.layout.activity_login);
    }

    public void RequestUserReg(String email, String password, String firstname, String lastname, String surname, Integer gender){
        Context context = this;
        CheckInternet checkInternet = new CheckInternet(this);
        User user = new User();
        user.email = email;
        user.password = password;
        user.firstname = firstname;
        user.lastname = lastname;
        user.surname = surname;
        user.gender = gender;
        UserReg RequestUserReg = new UserReg(
                user,
                checkInternet,
                new MyResponseCallback() {
                    @Override
                    public void OnCompile(String result) {
                        Log.d("USER LOGIN", result);
                        Toast.makeText(context, "!!ПОЛЬЗОВАТЕЛЬ ЗАРЕГИСТРИРОВАН!!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void OnError(String error) {
                        Log.d("USER LOGIN", error);
                        Toast.makeText(context, "ошибка регистрации...", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        RequestUserReg.execute();
    }
}