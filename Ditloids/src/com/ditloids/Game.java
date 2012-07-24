package com.ditloids;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;


public class Game {
	// Количество уровней
	private int countLevels = 0;
	
	// Массив уровней
	private Level[] levels = null;
	
    // Текущий уровень
    private Level currentLevel = null;
    
    // Индекс текущего дитлоида на текущем уровне
    private int currentDitloidIndex = -1;

    // Массив флагов ответов на текущий уровень
    private boolean[] answers = null;
    
    // Имеющееся количество подсказок
    private int countHints = 0;
    
    // Количество правильно отвеченных дитлоидов после получения предыдущей подсказки
    private int countRight = 0; 

    // Объект настроек приложения
    private SharedPreferences settings = null;

    // Объект контекста игры
    private Context context = null;

    // Конструктор
    public Game(Context context, int _countLevels){
        Resources res = context.getResources();
        countLevels = _countLevels;
        String prefsName = res.getString(R.string.prefs_name);
        settings = context.getSharedPreferences(prefsName, 0);
        countHints = settings.getInt("hints", 0);
        countRight = settings.getInt("right", 0);
        levels = new Level[countLevels];
        for (int i = 1; i <= countLevels; ++i) {
        	levels[i-1] = new Level(context, i);
        }       
    }
    
    // Количество правильных ответов на уровень levelIndex сохраненных в настройках
    public int AnswersCount(int levelIndex){
    	if(levelIndex <= countLevels){
    		String ans_str = settings.getString("level" + Integer.toString(levelIndex), "");
    		if(ans_str.equals(""))
    			return 0;
    		else{
    			String[] ans = settings.getString("level" + Integer.toString(levelIndex), "").split("_");
    			return ans.length;
    		}
    	}
    	else
    		return 0;
    }

    // Загружаем уровень с индексом levelIndex
    public void LoadLevel(int levelIndex){
        currentLevel = levels[levelIndex-1];
        answers = new boolean[currentLevel.GetDitloidsCount()];
        String ans_str = settings.getString("level" + Integer.toString(levelIndex), "");
        if(!ans_str.equals("")){
        	String[] ans = ans_str.split("_");
        	for(int i = 0; i < ans.length; i++){
                int ind = Integer.parseInt(ans[i]);
                answers[ind] = true;
            }
        };
    }

    // Сохраняем текущий уровень
    public void SaveLevel(){
        String ans = "";
        for(int i=0; i < answers.length; i++){
            if(answers[i])
                ans = ans + Integer.toString(i) + "_";
        }
        if(!ans.equals(""))
        	ans = ans.substring(0, ans.length()-1);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("level" + Integer.toString(currentLevel.GetLevelIndex()), ans);
        editor.commit();
    }
    
    public Level GetCurrentLevel(){
    	return currentLevel;
    }
    
    public int GetCountHints(){
    	return countHints;
    }

    public void IncrementCountHints(){
    	countHints += 1;
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("hints", countHints);
        editor.commit();
    }

    public void DecrementCountHints(){
    	if(countHints > 0){
    		countHints -= 1;
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("hints", countHints);
            editor.commit();
    	}
    }

    public int GetCurrentDitloidIndex(){
    	return currentDitloidIndex;
    } 
    
    public void SetCurrentDitloidIndex(int currentDitloidIndex){
    	if(currentDitloidIndex > -1 || currentDitloidIndex < answers.length)
    		this.currentDitloidIndex = currentDitloidIndex;
    }
    
    public boolean GetAnswer(int ditloidIndex){
    	if(ditloidIndex > -1 || ditloidIndex < answers.length)
    		return answers[ditloidIndex];
    	else
    		return false;
    }

    public void SetAnswer(int ditloidIndex, boolean answer){
    	if(ditloidIndex > -1 || ditloidIndex < answers.length)
    		answers[ditloidIndex] = answer;
    }
    
    public int GetCountRight(){
    	return countRight;
    }
    
    public void IncrementCountRight(){
    	countRight += 1;
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("right", countRight);
        editor.commit();
    }
    
}
