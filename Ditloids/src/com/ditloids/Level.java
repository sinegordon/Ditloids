package com.ditloids;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.Context;

import java.util.*;

public class Level {

    // Список дитлоидов
    private ArrayList<String> ditloids = null;

    // Список подсказок
    private ArrayList<String> help = null;
    
    private int levelIndex = 0;

    // Конструктор берущий информацию об уровне с номером levelIndex из ресурсов контекста context
    // и информацией приложения хранящейся в settings
    public Level(Context context, int _levelIndex){
        levelIndex = _levelIndex;
        Resources res = context.getResources();
        String[] levels = res.getStringArray(R.array.levels);
        ditloids = new ArrayList<String>();
        String str = Integer.toString(levelIndex);
        for(int i = 0; i < levels.length; i++){
            String[] parts = levels[i].split("_");
            if(parts[1].equals(str)){
                ditloids.add(parts[0]);
            }
        }
    }

    // Проверяем ответ пользователя  probablyAnswer на дитлоид с индексом ditloidIndex
    public boolean Verify(int ditloidIndex, String probablyAnswer){
        if(ditloidIndex > ditloids.size() || ditloidIndex < 0 ) return false;
        if(ditloids.get(ditloidIndex).equals(probablyAnswer)){
            return true;
        }else
            return false;
    }

    // Получить дитлоид с индексом ditloidIndex
    public String GetDitloid(int ditloidIndex){
        String ditloid = "";
        if(ditloidIndex < ditloids.size() && ditloidIndex > -1){
            String[] str = ditloids.get(ditloidIndex).trim().split(" ");
            for(int i=0; i<str.length; i++)
            	if(str[i].trim().substring(0,1).matches("[0-9]"))
            		ditloid = ditloid + " " + str[i].trim();
            	else
            		ditloid = ditloid + " " + str[i].trim().substring(0,1);
            return ditloid;
        }else
            return "";
    }

    // Получить ответ на дитлоид с индексом ditloidIndex, если он уже отгадан (иначе пустая строка)
    public String GetDitloidAnswer(int ditloidIndex){
        if(ditloidIndex > ditloids.size() || ditloidIndex < 0) return "";
        return ditloids.get(ditloidIndex);
    }

    // Общее количество дитлоидов
    public int GetDitloidsCount(){
        return ditloids.size();
    }
    
    public int GetLevelIndex(){
        return levelIndex;
    }
}
