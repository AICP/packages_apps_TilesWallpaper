package com.aicp.livewallpaper;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ConfigurationInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;

import pit.opengles.GLESPlaneAnimatedRenderer;
import pit.opengles.GLESPlaneAnimatedSurfaceView;

public class PreviewActivity extends Activity
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    private GLESPlaneAnimatedSurfaceView _mGLSurfaceView;
    private GLESPlaneAnimatedRenderer _mRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_activity);

        _mGLSurfaceView = (GLESPlaneAnimatedSurfaceView) findViewById(R.id.surfaceView);
        _mRenderer = new GLESPlaneAnimatedRenderer(this);

        if(isValidGLES()) {
            _mGLSurfaceView.setEGLContextClientVersion(2);
            _mGLSurfaceView.setRenderer(_mRenderer);
        } else {
            throw new RuntimeException("Error OpenGL ES 2.0 not found");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        prefs.registerOnSharedPreferenceChangeListener(this);
        onSharedPreferenceChanged(prefs, null);
    }

    @Override
    protected void onPause() {
        super.onPause();

        PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key)
    {
        if(isValidGLES())
        {
            String color = prefs.getString("color", "COLORFUL");
            _mRenderer.switchColors(color);
            Float animSpeed = prefs.getInt("animSpeed1", 20) * 0.01f;
            _mRenderer.changeAnimationSpeed(animSpeed);
            String motion = prefs.getString("motion", "random");
            _mRenderer.changeMotion(motion);
            boolean sensors = prefs.getBoolean("sensors", true);
            _mGLSurfaceView.activateSensors(sensors);
        }
    }

    private boolean isValidGLES()
    {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        assert am != null;
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return info.reqGlEsVersion >= 0x2000;
    }
}
