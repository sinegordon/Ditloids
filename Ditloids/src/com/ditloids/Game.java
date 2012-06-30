package com.ditloids;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Game {
    // Текущий уровень
    private Level currentLevel = null;

    // Массив флагов ответов на текущий уровень
    private boolean[] answers = null;

    // Объект настроек приложения
    private SharedPreferences settings = null;

    // Объект контекста игры
    private Context context = null;

    // Конструктор
    public Game(Context context){
        Resources res = context.getResources();
        String prefsName = res.getString(R.string.prefs_name);
        settings = context.getSharedPreferences(prefsName, 0);
    }

    // Загружаем уровень с индексом levelIndex
    public void LoadLevel(int levelIndex){
        currentLevel = new Level(context, levelIndex);
        String[] ans = settings.getString("level" + Integer.toString(levelIndex), "").split("_");
        answers = new boolean[currentLevel.GetDitloidsCount()];
        for(int i = 0; i < answers.length; i++)
            answers[i] = false;
        for(int i = 0; i < ans.length; i++){
            int ind = Integer.parseInt(ans[i]);
            answers[ind] = true;
        }
    }

    // Сохраняем уровень с индексом levelIndex
    public void SaveLevel(){
        String ans = "";
        for(int i=0; i < answers.length; i++){
            if(answers[i])
                ans = ans + Integer.toString(i) + "_";
            ans = ans.substring(0, ans.length()-2);
        }
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("level" + Integer.toString(currentLevel.GetLevelIndex()), ans);
        editor.commit();
    }
}
