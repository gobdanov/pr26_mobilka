package classes;

import android.app.Activity;
import android.content.Context;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import android.util.Log;
import androidx.core.content.ContextCompat;
import java.util.concurrent.Executor;

import androidx.biometric.BiometricPrompt.AuthenticationCallback;
import androidx.biometric.BiometricPrompt.AuthenticationResult;

import presentations.LoginActivity;
import presentations.MainActivity;


public class BiometricHelper {

    public static Boolean canAutentificate = false;

    Activity activity;
    Executor executor;
    BiometricPrompt biometricPrompt;
    BiometricPrompt.AuthenticationCallback callback;

    BiometricPrompt.PromptInfo promptInfo;
    public BiometricHelper(LoginActivity activity, AuthenticationCallback callback){
        this.activity = activity;
        this.callback = callback;
        this.executor = ContextCompat.getMainExecutor(activity);

        biometricPrompt = new BiometricPrompt(activity, this.executor, this.callback);

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("авторизация по отпечатку")
                .setSubtitle("приложите палец к сканеру")
                .setDescription("подвердите личность для входа в приложение")
                .setNegativeButtonText("ОТМЕНА")
                .build();
    }

    public void show(){
        if (isBiometricAvaiable()){
            biometricPrompt.authenticate(promptInfo);
        }
        else{
            Log.d("Biometric helper", "биометрия недоступна на устройстве");
        }
    }

    boolean isBiometricAvaiable(){
        BiometricManager biometricManager = androidx.biometric.BiometricManager.from(activity);
        return biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS;
    }

}
