/*
 * Copyright (C) 2023 The Android Open Source Project
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

package android.adservices.common;

import android.annotation.NonNull;
import android.annotation.Nullable;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.adservices.AdServicesParcelableUtil;
import com.android.internal.annotations.VisibleForTesting;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/**
 * A container class for filters which are associated with an ad.
 *
 * <p>If any of the filters in an {@link AdFilters} instance are not satisfied, the associated ad
 * will not be eligible for ad selection. Filters are optional ad parameters and are not required as
 * part of {@link AdData}.
 *
 * @hide
 */
// TODO(b/221876775): Unhide for frequency cap API review
public final class AdFilters implements Parcelable {
    /** @hide */
    @VisibleForTesting public static final String FREQUENCY_CAP_FIELD_NAME = "frequency_cap";
    /** @hide */
    @VisibleForTesting public static final String APP_INSTALL_FIELD_NAME = "app_install";
    /** @hide */
    @Nullable private final FrequencyCapFilters mFrequencyCapFilters;

    @Nullable private final AppInstallFilters mAppInstallFilters;

    @NonNull
    public static final Creator<AdFilters> CREATOR =
            new Creator<AdFilters>() {
                @Override
                public AdFilters createFromParcel(@NonNull Parcel in) {
                    Objects.requireNonNull(in);
                    return new AdFilters(in);
                }

                @Override
                public AdFilters[] newArray(int size) {
                    return new AdFilters[size];
                }
            };

    private AdFilters(@NonNull Builder builder) {
        Objects.requireNonNull(builder);

        mFrequencyCapFilters = builder.mFrequencyCapFilters;
        mAppInstallFilters = builder.mAppInstallFilters;
    }

    private AdFilters(@NonNull Parcel in) {
        Objects.requireNonNull(in);

        mFrequencyCapFilters =
                AdServicesParcelableUtil.readNullableFromParcel(
                        in, FrequencyCapFilters.CREATOR::createFromParcel);
        mAppInstallFilters =
                AdServicesParcelableUtil.readNullableFromParcel(
                        in, AppInstallFilters.CREATOR::createFromParcel);
    }

    /**
     * Gets the {@link FrequencyCapFilters} instance that represents all frequency cap filters for
     * the ad.
     *
     * <p>If {@code null}, there are no frequency cap filters which apply to the ad.
     *
     * @hide
     */
    @Nullable
    public FrequencyCapFilters getFrequencyCapFilters() {
        return mFrequencyCapFilters;
    }

    /**
     * Gets the {@link AppInstallFilters} instance that represents all app install filters for the
     * ad.
     *
     * <p>If {@code null}, there are no app install filters which apply to the ad.
     *
     * @hide
     */
    @Nullable
    public AppInstallFilters getAppInstallFilters() {
        return mAppInstallFilters;
    }

    /**
     * @return The estimated size of this object, in bytes.
     * @hide
     */
    public int getSizeInBytes() {
        int size = 0;
        if (mFrequencyCapFilters != null) {
            size += mFrequencyCapFilters.getSizeInBytes();
        }
        if (mAppInstallFilters != null) {
            size += mAppInstallFilters.getSizeInBytes();
        }
        return size;
    }

    /**
     * A JSON serializer.
     *
     * @return A JSON serialization of this object.
     * @hide
     */
    public JSONObject toJson() throws JSONException {
        JSONObject toReturn = new JSONObject();
        if (mFrequencyCapFilters != null) {
            toReturn.put(FREQUENCY_CAP_FIELD_NAME, mFrequencyCapFilters.toJson());
        }
        if (mAppInstallFilters != null) {
            toReturn.put(APP_INSTALL_FIELD_NAME, mAppInstallFilters.toJson());
        }
        return toReturn;
    }

    /**
     * A JSON de-serializer.
     *
     * @param json A JSON representation of an {@link AdFilters} object as would be generated by
     *     {@link #toJson()}.
     * @return An {@link AdFilters} object generated from the given JSON.
     * @hide
     */
    public static AdFilters fromJson(JSONObject json) throws JSONException {
        Builder builder = new Builder();
        if (json.has(FREQUENCY_CAP_FIELD_NAME)) {
            builder.setFrequencyCapFilters(
                    FrequencyCapFilters.fromJson(json.getJSONObject(FREQUENCY_CAP_FIELD_NAME)));
        }
        if (json.has(APP_INSTALL_FIELD_NAME)) {
            builder.setAppInstallFilters(
                    AppInstallFilters.fromJson(json.getJSONObject(APP_INSTALL_FIELD_NAME)));
        }
        return builder.build();
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        Objects.requireNonNull(dest);

        AdServicesParcelableUtil.writeNullableToParcel(
                dest,
                mFrequencyCapFilters,
                (targetParcel, sourceFilters) -> sourceFilters.writeToParcel(targetParcel, flags));
        AdServicesParcelableUtil.writeNullableToParcel(
                dest,
                mAppInstallFilters,
                (targetParcel, sourceFilters) -> sourceFilters.writeToParcel(targetParcel, flags));
    }

    /** @hide */
    @Override
    public int describeContents() {
        return 0;
    }

    /** Checks whether the {@link AdFilters} objects represent the same set of filters. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdFilters)) return false;
        AdFilters adFilters = (AdFilters) o;
        return Objects.equals(mFrequencyCapFilters, adFilters.mFrequencyCapFilters)
                && Objects.equals(mAppInstallFilters, adFilters.mAppInstallFilters);
    }

    /** Returns the hash of the {@link AdFilters} object's data. */
    @Override
    public int hashCode() {
        return Objects.hash(mFrequencyCapFilters, mAppInstallFilters);
    }

    @Override
    public String toString() {
        return "AdFilters{" + generateFrequencyCapString() + generateAppInstallString() + "}";
    }

    private String generateFrequencyCapString() {
        // TODO(b/221876775) Add fcap once it is unhidden
        return "";
    }

    private String generateAppInstallString() {
        // TODO(b/266837113) Add app install once it is unhidden
        return "";
    }

    /** Builder for creating {@link AdFilters} objects. */
    public static final class Builder {
        @Nullable private FrequencyCapFilters mFrequencyCapFilters;
        @Nullable private AppInstallFilters mAppInstallFilters;

        public Builder() {}

        /**
         * Sets the {@link FrequencyCapFilters} which will apply to the ad.
         *
         * <p>If set to {@code null} or not set, no frequency cap filters will be associated with
         * the ad.
         *
         * @hide
         */
        @NonNull
        public Builder setFrequencyCapFilters(@Nullable FrequencyCapFilters frequencyCapFilters) {
            mFrequencyCapFilters = frequencyCapFilters;
            return this;
        }

        /**
         * Sets the {@link AppInstallFilters} which will apply to the ad.
         *
         * <p>If set to {@code null} or not set, no app install filters will be associated with the
         * ad.
         *
         * @hide
         */
        @NonNull
        public Builder setAppInstallFilters(@Nullable AppInstallFilters appInstallFilters) {
            mAppInstallFilters = appInstallFilters;
            return this;
        }

        /** Builds and returns an {@link AdFilters} instance. */
        @NonNull
        public AdFilters build() {
            return new AdFilters(this);
        }
    }
}
