package com.example.wizardspotionshop;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class Piano extends BaseMainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.tela_piano);

    }

    @Override
    protected void onViewCreated() {
        
    }

    private int dp2px(double dpValue) {
      final float scale = getResources().getDisplayMetrics().density;
      return (int) (dpValue * scale + 0.5f);
    }

    public void clickC(View v) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cnote);
        mp.start();
    }
    
    public void clickCSust(View v) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.csustnote);
        mp.start();
    }

    public void clickD(View v) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.dnote);
        mp.start();
    }

    public void clickDSust(View v) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.dsustnote);
        mp.start();
    }

    public void clickE(View v) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.enote);
        mp.start();
    }

    public void clickF(View v) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.fnote);
        mp.start();
    }

    public void clickFSust(View v) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.fsustnote);
        mp.start();
    }

    public void clickG(View v) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.gnote);
        mp.start();
    }

    public void clickGSust(View v) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.gsustnote);
        mp.start();
    }

    public void clickA(View v) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.anote);
        mp.start();
    }

    public void clickASust(View v) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.asustnote);
        mp.start();
    }

    public void clickB(View v) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.bnote);
        mp.start();
    }
}
