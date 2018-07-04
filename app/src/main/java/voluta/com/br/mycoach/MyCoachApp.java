package voluta.com.br.mycoach;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import voluta.com.br.mycoach.Enum.Modality;
import voluta.com.br.mycoach.Model.Aluno;
import voluta.com.br.mycoach.Model.User;

public class MyCoachApp extends Application {

    private static final String GOOGLE_API_KEY = "AIzaSyDdXv36Adgj-GRN1jvzTzgedWM6FLXtejc";
    public static boolean editingTrain;

    public static String getGoogleApiKey() {
        return GOOGLE_API_KEY;
    }
    public static User userLogado;
    public static Aluno alunoVisualizado;

    public static Modality modality;
    public static int getAppPrimaryColorId() {
        if (modality == Modality.swimming)
        {
            return R.color.colorAppPrimary;
        }
        else if (modality == Modality.running)
        {
            return R.color.colorAppRunningPrimary;
        }
        else
        {
            return R.color.colorAppBodyWorkoutPrimary;
        }
    }

    public static Map getAutomaticLoginData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("HSHSharedPreferences",MODE_PRIVATE);
        Map automaticLoginData = new HashMap();
        for (Map.Entry entry: sharedPreferences.getAll().entrySet()) {
            automaticLoginData.put(entry.getKey(), entry.getValue());
        }
        return automaticLoginData;
    }

    public static void setAutomaticLoginData(Context context, Map loginAutomaticoData) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("HSHSharedPreferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (Map.Entry<String,String> entry: (Collection<Map.Entry>)loginAutomaticoData.entrySet()) {
            editor.putString(entry.getKey(),entry.getValue());
        }
        editor.apply();
    }

    public static void clearAutomaticLoginData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("HSHSharedPreferences",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
