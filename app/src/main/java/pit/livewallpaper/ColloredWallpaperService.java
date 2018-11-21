package pit.livewallpaper;

import android.content.SharedPreferences;
import android.opengl.GLSurfaceView;
import android.preference.PreferenceManager;

import pit.opengles.GLESPlaneAnimatedRenderer;

/**
 * Created by paulh on 16.10.2017.
 */

public class ColloredWallpaperService extends OpenGLESWallpaperService {

    private GLESPlaneAnimatedRenderer _mRenderer;


    @Override
    GLSurfaceView.Renderer getGLESRenderer() {
        _mRenderer = new GLESPlaneAnimatedRenderer(this);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String color = prefs.getString("color", "COLORFUL");
        _mRenderer.switchColors(color);
        Float animSpeed = prefs.getInt("animSpeed1", 20) * 0.01f;
        _mRenderer.changeAnimationSpeed(animSpeed);
        String motion = prefs.getString("motion", "random");
        _mRenderer.changeMotion(motion);
        boolean sensors = prefs.getBoolean("sensors", true);
        _mEngine.activateSensors(sensors);
        return  _mRenderer;
    }
}
