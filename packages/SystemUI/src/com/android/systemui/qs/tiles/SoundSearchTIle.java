/*
 * Copyright (C) 2018 ABC ROM
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.systemui.qs.tiles;

import android.content.Context;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.service.quicksettings.Tile;
import android.widget.Toast;

import com.android.internal.logging.nano.MetricsProto.MetricsEvent;
import com.android.internal.util.gzosp.GzospUtils;
import com.android.systemui.Dependency;
import com.android.systemui.qs.QSHost;
import com.android.systemui.plugins.qs.QSTile.BooleanState;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.R;

public class SoundSearchTIle extends QSTileImpl<BooleanState> {

    public SoundSearchTIle(QSHost host) {
        super(host);
    }

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.BOOTLEG;
    }

    @Override
    public void handleClick() {
        mHost.collapsePanels();
        // Shazam
        if (GzospUtils.isPackageInstalled(mContext, "com.shazam.android") || GzospUtils.isPackageInstalled(mContext, "com.shazam.encore.android")) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setAction("com.shazam.android.intent.actions.START_TAGGING");
            mContext.startActivity(intent);
        // Soundhound
        } else if (GzospUtils.isPackageInstalled(mContext, "com.melodis.midomiMusicIdentifier.freemium") || GzospUtils.isPackageInstalled(mContext, "com.melodis.midomiMusicIdentifier")) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setAction("com.soundhound.android.ID_NOW_EXTERNAL");
            mContext.startActivity(intent);
        // Google Search Music
        } else if (GzospUtils.isPackageInstalled(mContext, "com.google.android.googlequicksearchbox")) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setAction("com.google.android.googlequicksearchbox.MUSIC_SEARCH");
            mContext.startActivity(intent);
        } else {
            Toast.makeText(mContext, mContext.getString(
                    R.string.quick_settings_sound_search_no_app), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public Intent getLongClickIntent() {
        return null;
    }

    @Override
    public CharSequence getTileLabel() {
        return mContext.getString(R.string.quick_settings_sound_search);
    }

    @Override
    public void handleUpdateState(BooleanState state, Object arg) {
        state.label = mContext.getString(R.string.quick_settings_sound_search);
        state.contentDescription = mContext.getString(
                R.string.quick_settings_sound_search);
        state.icon = ResourceIcon.get(R.drawable.ic_qs_sound_search);
    }

    @Override
    public BooleanState newTileState() {
        return new BooleanState();
    }

    @Override
    public void handleSetListening(boolean listening) {
    }
}
