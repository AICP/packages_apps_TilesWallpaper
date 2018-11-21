package pit.livewallpaper;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.preference.PreferenceManager;
import android.view.SurfaceHolder;
import pit.opengles.GLESPlaneAnimatedRenderer;

/**
 * Created by paulh on 13.10.2017.
 */

public abstract class OpenGLESWallpaperService extends GLESWallpaperService
{
    GLESEngine _mEngine;
    @Override
    public  Engine onCreateEngine()
    {
        _mEngine =  new OpenGLESEngine();
        return _mEngine;
    }


    class OpenGLESEngine extends GLESEngine implements SharedPreferences.OnSharedPreferenceChangeListener
    {
        SharedPreferences _mPrefs;
        GLSurfaceView.Renderer _mRenderer;


        @Override
        public void onCreate(SurfaceHolder sH)
        {
            super.onCreate(sH);

            _mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            _mPrefs.registerOnSharedPreferenceChangeListener(this);

            if(isValidGLES())
            {
                setEGLContextClientVersion(2);
                setPreserveEGLContextOnPause(true);
                _mRenderer = getGLESRenderer();
                setRenderer(_mRenderer);
            }
            else
                return;
        }

        private boolean isValidGLES()
        {
            ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            assert am != null;
            ConfigurationInfo info = am.getDeviceConfigurationInfo();
            return info.reqGlEsVersion >= 0x2000;
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences prefs, String key)
        {
            if(isValidGLES())
            {
                String color = prefs.getString("color", "COLORFUL");
                ((GLESPlaneAnimatedRenderer) _mRenderer).switchColors(color);
                Float animSpeed = prefs.getInt("animSpeed1", 20) * 0.01f;
                ((GLESPlaneAnimatedRenderer) _mRenderer).changeAnimationSpeed(animSpeed);
                String motion = prefs.getString("motion", "random");
                ((GLESPlaneAnimatedRenderer) _mRenderer).changeMotion(motion);
                boolean sensors = prefs.getBoolean("sensors", true);
                this.activateSensors(sensors);
            }
        }
    }
    abstract GLSurfaceView.Renderer getGLESRenderer();
}
