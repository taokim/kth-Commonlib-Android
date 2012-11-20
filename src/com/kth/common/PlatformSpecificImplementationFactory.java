/*
 * Copyright 2011 Google Inc.
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

package com.kth.common;

import com.kth.common.location.FroyoLocationUpdateRequester;
import com.kth.common.location.GingerbreadLastLocationFinder;
import com.kth.common.location.GingerbreadLocationUpdateRequester;
import com.kth.common.location.ILastLocationFinder;
import com.kth.common.location.LegacyLastLocationFinder;
import com.kth.common.location.LocationUpdateRequester;
import com.kth.common.preference.FroyoSharedPreferenceSaver;
import com.kth.common.preference.GingerbreadSharedPreferenceSaver;
import com.kth.common.preference.LegacySharedPreferenceSaver;
import com.kth.common.preference.SharedPreferenceSaver;
import com.kth.common.strictmode.HoneycombStrictMode;
import com.kth.common.strictmode.IStrictMode;
import com.kth.common.strictmode.LegacyStrictMode;

import android.content.Context;
import android.location.LocationManager;

/**
 * Factory class to create the correct instances of a variety of classes with
 * platform specific implementations.
 */
public class PlatformSpecificImplementationFactory {

    public static boolean SUPPORTS_GINGERBREAD = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD;

    public static boolean SUPPORTS_HONEYCOMB = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB;

    public static boolean SUPPORTS_FROYO = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.FROYO;

    public static boolean SUPPORTS_ECLAIR = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ECLAIR;

    /**
     * Create a new LastLocationFinder instance
     * 
     * @param context Context
     * @return LastLocationFinder
     */
    public static ILastLocationFinder getLastLocationFinder(Context context) {
        return SUPPORTS_GINGERBREAD ? new GingerbreadLastLocationFinder(context)
                : new LegacyLastLocationFinder(context);
    }

    /**
     * Create a new StrictMode instance.
     * 
     * @return StrictMode
     */
    public static IStrictMode getStrictMode() {
        if (SUPPORTS_HONEYCOMB)
            return new HoneycombStrictMode();
        else if (SUPPORTS_GINGERBREAD)
            return new LegacyStrictMode();
        else
            return null;
    }

    /**
     * Create a new LocationUpdateRequester
     * 
     * @param locationManager Location Manager
     * @return LocationUpdateRequester
     */
    public static LocationUpdateRequester getLocationUpdateRequester(LocationManager locationManager) {
        return SUPPORTS_GINGERBREAD ? new GingerbreadLocationUpdateRequester(locationManager)
                : new FroyoLocationUpdateRequester(locationManager);
    }

    /**
     * Create a new SharedPreferenceSaver
     * 
     * @param context Context
     * @return SharedPreferenceSaver
     */
    public static SharedPreferenceSaver getSharedPreferenceSaver(Context context) {
        return SUPPORTS_GINGERBREAD ? new GingerbreadSharedPreferenceSaver(context)
                : SUPPORTS_FROYO ? new FroyoSharedPreferenceSaver(context)
                        : new LegacySharedPreferenceSaver(context);
    }
}
