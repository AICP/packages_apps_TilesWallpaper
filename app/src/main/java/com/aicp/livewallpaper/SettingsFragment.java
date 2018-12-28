package com.aicp.livewallpaper;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceGroup;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import java.util.Arrays;

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

        // We need to use device protected storage context in order to be able to read
        // our settings before user login
        SharedPreferencesSettingsStore settingsDataStore = new SharedPreferencesSettingsStore(
                PreferenceManager.getDefaultSharedPreferences(
                    getActivity().getApplicationContext().createDeviceProtectedStorageContext()));
        setPreferenceDataStore(getPreferenceScreen(), settingsDataStore);
    }

    private void setPreferenceDataStore(PreferenceGroup prefGroup, SharedPreferencesSettingsStore store) {
        for (int i = 0; i < prefGroup.getPreferenceCount(); i++) {
            Preference pref = prefGroup.getPreference(i);
            if (pref instanceof PreferenceGroup) {
                setPreferenceDataStore((PreferenceGroup) pref, store);
            } else {
                pref.setPreferenceDataStore(store);
            }
        }
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
