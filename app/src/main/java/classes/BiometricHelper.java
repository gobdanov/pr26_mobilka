package classes;

import android.content.Context;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import android.util.Log;
import androidx.core.content.ContextCompat;
import com.example.pr26_mobilka.MainActivity;
import java.util.concurrent.Executor;

import androidx.biometric.BiometricPrompt.AuthenticationCallback;
import androidx.biometric.BiometricPrompt.AuthenticationResult;

public class BiometricHelper {
    Context context;
    Executor executor;
    BiometricPrompt biometricPrompt;
    BiometricPrompt.AuthenticationCallback callback;

    BiometricPrompt.PromptInfo promptInfo;
    public BiometricHelper(MainActivity activity, AuthenticationCallback callback){
        this.context = activity.getApplicationContext();
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
        BiometricManager biometricManager = androidx.biometric.BiometricManager.from(context);
        return biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS;
    }
}
