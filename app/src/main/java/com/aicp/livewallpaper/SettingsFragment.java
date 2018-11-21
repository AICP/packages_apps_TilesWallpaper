package com.aicp.livewallpaper;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import java.util.Arrays;

import pit.opengles.R;

public class SettingsFragment extends PreferenceFragment
        implements Preference.OnPreferenceChangeListener {

    private ListPreference mMotionPreference;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        mMotionPreference = (ListPreference) findPreference("motion");
        mMotionPreference.setOnPreferenceChangeListener(this);
        mMotionPreference.setSummary(mMotionPreference.getEntry());
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue)
    {
        if (preference == mMotionPreference) {
            mMotionPreference.setSummary(mMotionPreference
                    .getEntries()[Arrays.asList(mMotionPreference.getEntryValues()).indexOf(newValue)]);
            return true;
        } else {
            return false;
        }
    }
}
