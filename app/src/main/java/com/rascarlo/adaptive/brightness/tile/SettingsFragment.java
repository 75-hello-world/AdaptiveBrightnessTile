package com.rascarlo.adaptive.brightness.tile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

public class SettingsFragment extends PreferenceFragmentCompat {
    private SwitchPreference switchPreferenceModifySystemSettingsPermission;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.fragment_settings);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        switchPreferenceModifySystemSettingsPermission = getPreferenceManager().findPreference(getString(R.string.key_permission_modify_system_settings));
        if (getActivity() != null && getActivity().getPackageName() != null) {
            switchPreferenceModifySystemSettingsPermission.setOnPreferenceChangeListener((preference, o) -> {
                startActivity(new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .setData(Uri.parse("package:" + getActivity().getPackageName())));
                return false;
            });
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        updateModifySystemSettingsPermissionSwitch();
        super.onResume();
    }

    private void updateModifySystemSettingsPermissionSwitch() {
        switchPreferenceModifySystemSettingsPermission.setChecked(Settings.System.canWrite(getActivity()));
    }
}
