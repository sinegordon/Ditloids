package com.ditloids;

import java.io.IOException;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.content.Intent;


public class Game {
	// Количество уровней
	private int countLevels = 0;
	
	// Массив уровней
	private Level[] levels = null;
	
    // Текущий уровень
    private Level currentLevel = null;
    
    // Индекс текущего дитлоида на текущем уровне
    private int currentDitloidIndex = -1;

    // Массив флагов отвеченных дитлоидов на текущий уровень
    private boolean[] answers = null;
    
    // Имеющееся количество подсказок
    private int countHints = 0;

    // Массив флагов взятых подсказок на текущий уровень
    private boolean[] hints = null;

    // За сколько ответов дается подсказка
    private int divisor = 3;
    
    // Количество правильно отвеченных дитлоидов после получения предыдущей подсказки
    private int countRight = 0; 

    // Объект настроек приложения
    private SharedPreferences settings = null;

    // Объект контекста игры
    private Context context = null;
    
    // Пул звуков
    private SoundPool sounds = null;
    
    // Число звуков
    private int countSounds = 2;
    
    // Есть ли звук в игре
    private boolean isMuteSound = false;
    
    // Проигрыватель
    private MediaPlayer mediaPlayer = null;
    
    // Есть ли музыка в игре
    private boolean isMuteMusic = false;

    // Конструктор
    public Game(Context context, int countLevels) throws IllegalStateException, IOException{
        Resources res = context.getResources();
        this.context = context;
        this.countLevels = countLevels;
        String prefsName = res.getString(R.string.prefs_name);
        settings = context.getSharedPreferences(prefsName, 0);
        countHints = settings.getInt("hints", 0);
        countRight = settings.getInt("right", 0);
        isMuteSound = settings.getBoolean("isMuteSound", false);
        isMuteMusic = settings.getBoolean("isMuteMusic", false);
        levels = new Level[countLevels];
        for (int i = 1; i <= countLevels; ++i) {
        	levels[i-1] = new Level(context, i);
        };
        sounds = new SoundPool(countSounds, AudioManager.STREAM_MUSIC, 0);
        sounds.load(context, R.raw.right, 1);
        sounds.load(context, R.raw.wrong, 1);
        
        mediaPlayer = MediaPlayer.create(context, R.raw.music);
//        mediaPlayer.prepare();
        if(isMuteMusic)
        	mediaPlayer.pause();
        else
        	mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.start();				
			}
		});
		
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

    // Возвращаем уровень с индексом levelIndex    
    public Level GetLevel(int levelIndex){
    	if(levelIndex > 0 && levelIndex <= countLevels && !levels.equals(null))
    		return levels[levelIndex-1];
    	else
    		return null;
    }
    
    // Загружаем уровень с индексом levelIndex
    public void LoadLevel(int levelIndex){
        currentLevel = levels[levelIndex-1];
        answers = new boolean[currentLevel.GetDitloidsCount()];
        hints = new boolean[currentLevel.GetDitloidsCount()];
        String ans_str = settings.getString("level" + Integer.toString(levelIndex), "");
        String hints_str = settings.getString("hints" + Integer.toString(levelIndex), "");
        if(!ans_str.equals("")){
        	String[] ans = ans_str.split("_");
        	for(int i = 0; i < ans.length; i++){
                int ind = Integer.parseInt(ans[i]);
                answers[ind] = true;
            }
        };
        if(!hints_str.equals("")){
        	String[] hints_mas = hints_str.split("_");
        	for(int i = 0; i < hints_mas.length; i++){
                int ind = Integer.parseInt(hints_mas[i]);
                hints[ind] = true;
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
        String hints_str = "";
        for(int i=0; i < hints.length; i++){
            if(hints[i])
            	hints_str = hints_str + Integer.toString(i) + "_";
        }
        if(!hints_str.equals(""))
        	hints_str = hints_str.substring(0, hints_str.length()-1);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("level" + Integer.toString(currentLevel.GetLevelIndex()), ans);
        editor.putString("hints" + Integer.toString(currentLevel.GetLevelIndex()), hints_str);
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

    public boolean GetHint(int ditloidIndex){
    	if(ditloidIndex > -1 || ditloidIndex < hints.length)
    		return hints[ditloidIndex];
    	else
    		return false;
    }
      
    public void SetAnswer(int ditloidIndex, boolean answer){
    	if(ditloidIndex > -1 || ditloidIndex < answers.length)
    		answers[ditloidIndex] = answer;
    }

    public void SetHint(int ditloidIndex, boolean hint){
    	if(ditloidIndex > -1 || ditloidIndex < hints.length)
    		hints[ditloidIndex] = hint;
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
    
    public int GetDivisor(){
    	return divisor;
    }
    
    public void PlaySound(int soundId){
    	if(soundId < 0 || soundId > countSounds || isMuteSound) return;
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        float actualVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume = actualVolume / maxVolume;
    	sounds.play(soundId, volume, volume, 1, 0, 1.0f);
    }
    
    public String GetLastWrongAnswer(int levelIndex, int ditloidIndex){
        String wrong_ans = settings.getString("wrong_" + Integer.toString(levelIndex) + "_" + Integer.toString(ditloidIndex), "");
        return wrong_ans;
    }
    
    public void SetLastWrongAnswer(String answer, int levelIndex, int ditloidIndex){
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("wrong_" + Integer.toString(levelIndex) + "_" + Integer.toString(ditloidIndex), answer);
        editor.commit();   	
    }
    
    public void ClearAllSettings(){ 	
    	SharedPreferences.Editor editor = settings.edit();
    	editor.clear();
    	editor.commit();
        Resources res = context.getResources();
        String prefsName = res.getString(R.string.prefs_name);
        settings = context.getSharedPreferences(prefsName, 0);
        countHints = settings.getInt("hints", 0);
        countRight = settings.getInt("right", 0);
        isMuteSound = settings.getBoolean("isMuteSound", false);
        isMuteMusic = settings.getBoolean("isMuteMusic", false);
       	mediaPlayer.start();
    }
    
    public void SetMuteSound(boolean isMute){
    	this.isMuteSound = isMute;
    }

    public boolean GetMuteSound(){
    	return isMuteSound;
    }
    
    public void SetMuteMusic(boolean isMute){
    	this.isMuteMusic = isMute;
        if(isMuteMusic)
        	mediaPlayer.pause();
        else
        	mediaPlayer.start();
    }

    public boolean GetMuteMusic(){
    	return isMuteMusic;
    }
    
    public void SaveMuteSound(){
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("isMuteSound", isMuteSound);
        editor.commit();   	   	
    }
    
    public void SaveMuteMusic(){
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("isMuteMusic", isMuteMusic);
        editor.commit();   	   	
    }
}
