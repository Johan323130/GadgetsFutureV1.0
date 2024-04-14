/*
 * Copyright (C) 2019 The Android Open Source Project
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

package android.app;

import android.annotation.IntRange;
import android.annotation.NonNull;
import android.annotation.Nullable;
import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.annotations.Immutable;
import com.android.internal.util.DataClass;

/**
 * Message for noted runtime permission access.
 * @hide
 */
@Immutable
@SystemApi
/*@DataClass(genConstructor = false)
@DataClass.Suppress("getOpCode")*/
public final class RuntimeAppOpAccessMessage implements Parcelable {
    /** Uid of package for which runtime app op access message was collected */
    private final @IntRange(from = 0L) int mUid;
    /** Op code of operation access which was collected */
    private final @IntRange(from = 0L, to = AppOpsManager._NUM_OP - 1) int mOpCode;
    /** Name of package for which runtime app op access message was collected */
    private final @NonNull String mPackageName;
    /** Feature of package for which runtime app op access message was collected */
    private final @Nullable String mAttributionTag;
    /** Message collected (including stacktrace for synchronous ops) */
    private final @NonNull String mMessage;
    /** Sampling strategy used to collect this message. */
    private final @AppOpsManager.SamplingStrategy int mSamplingStrategy;

    public @NonNull String getOp() {
        return AppOpsManager.opToPublicName(mOpCode);
    }

    /**
     * Creates a new RuntimeAppOpAccessMessage.
     *
     * @param uid
     *   Uid of package for which runtime app op access message was collected
     * @param opCode
     *   Op code of operation access which was collected
     * @param packageName
     *   Name of package for which runtime app op access message was collected
     * @param attributionTag
     *   Attribution tag for which runtime app op access message was collected
     * @param message
     *   Message collected (including stacktrace for synchronous ops)
     * @param samplingStrategy
     *   Sampling strategy used to collect this message.
     */
    @DataClass.Generated.Member
    public RuntimeAppOpAccessMessage(
            @IntRange(from = 0L) int uid,
            @IntRange(from = 0L) int opCode,
            @NonNull String packageName,
            @Nullable String attributionTag,
            @NonNull String message,
            @AppOpsManager.SamplingStrategy int samplingStrategy) {
        this.mUid = uid;
        com.android.internal.util.AnnotationValidations.validate(
                IntRange.class, null, mUid,
                "from", 0L);
        this.mOpCode = opCode;
        com.android.internal.util.AnnotationValidations.validate(
                IntRange.class, null, mOpCode,
                "from", 0L,
                "to", AppOpsManager._NUM_OP - 1);
        this.mPackageName = packageName;
        com.android.internal.util.AnnotationValidations.validate(
                NonNull.class, null, mPackageName);
        this.mAttributionTag = attributionTag;
        this.mMessage = message;
        com.android.internal.util.AnnotationValidations.validate(
                NonNull.class, null, mMessage);
        this.mSamplingStrategy = samplingStrategy;
        com.android.internal.util.AnnotationValidations.validate(
                AppOpsManager.SamplingStrategy.class, null, mSamplingStrategy);

        // onConstructed(); // You can define this method to get a callback
    }




    // Code below generated by codegen v1.0.14.
    //
    // DO NOT MODIFY!
    // CHECKSTYLE:OFF Generated code
    //
    // To regenerate run:
    // $ codegen $ANDROID_BUILD_TOP/frameworks/base/core/java/android/app/RuntimeAppOpAccessMessage.java
    //
    // To exclude the generated code from IntelliJ auto-formatting enable (one-time):
    //   Settings > Editor > Code Style > Formatter Control
    //@formatter:off


    /**
     * Uid of package for which runtime app op access message was collected
     */
    @DataClass.Generated.Member
    public @IntRange(from = 0L) int getUid() {
        return mUid;
    }

    /**
     * Name of package for which runtime app op access message was collected
     */
    @DataClass.Generated.Member
    public @NonNull String getPackageName() {
        return mPackageName;
    }

    /**
     * Attribution tag for which runtime app op access message was collected
     */
    @DataClass.Generated.Member
    public @Nullable String getAttributionTag() {
        return mAttributionTag;
    }

    /**
     * Message collected (including stacktrace for synchronous ops)
     */
    @DataClass.Generated.Member
    public @NonNull String getMessage() {
        return mMessage;
    }

    /**
     * Sampling strategy used to collect this message.
     */
    @DataClass.Generated.Member
    public @AppOpsManager.SamplingStrategy int getSamplingStrategy() {
        return mSamplingStrategy;
    }

    @Override
    @DataClass.Generated.Member
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        // You can override field parcelling by defining methods like:
        // void parcelFieldName(Parcel dest, int flags) { ... }

        byte flg = 0;
        if (mAttributionTag != null) flg |= 0x8;
        dest.writeByte(flg);
        dest.writeInt(mUid);
        dest.writeInt(mOpCode);
        dest.writeString(mPackageName);
        if (mAttributionTag != null) dest.writeString(mAttributionTag);
        dest.writeString(mMessage);
        dest.writeInt(mSamplingStrategy);
    }

    @Override
    @DataClass.Generated.Member
    public int describeContents() { return 0; }

    /** @hide */
    @SuppressWarnings({"unchecked", "RedundantCast"})
    @DataClass.Generated.Member
    /* package-private */ RuntimeAppOpAccessMessage(@NonNull Parcel in) {
        // You can override field unparcelling by defining methods like:
        // static FieldType unparcelFieldName(Parcel in) { ... }

        byte flg = in.readByte();
        int uid = in.readInt();
        int opCode = in.readInt();
        String packageName = in.readString();
        String attributionTag = (flg & 0x8) == 0 ? null : in.readString();
        String message = in.readString();
        int samplingStrategy = in.readInt();

        this.mUid = uid;
        com.android.internal.util.AnnotationValidations.validate(
                IntRange.class, null, mUid,
                "from", 0L);
        this.mOpCode = opCode;
        com.android.internal.util.AnnotationValidations.validate(
                IntRange.class, null, mOpCode,
                "from", 0L,
                "to", AppOpsManager._NUM_OP - 1);
        this.mPackageName = packageName;
        com.android.internal.util.AnnotationValidations.validate(
                NonNull.class, null, mPackageName);
        this.mAttributionTag = attributionTag;
        this.mMessage = message;
        com.android.internal.util.AnnotationValidations.validate(
                NonNull.class, null, mMessage);
        this.mSamplingStrategy = samplingStrategy;
        com.android.internal.util.AnnotationValidations.validate(
                AppOpsManager.SamplingStrategy.class, null, mSamplingStrategy);

        // onConstructed(); // You can define this method to get a callback
    }

    @DataClass.Generated.Member
    public static final @NonNull Parcelable.Creator<RuntimeAppOpAccessMessage> CREATOR
            = new Parcelable.Creator<RuntimeAppOpAccessMessage>() {
        @Override
        public RuntimeAppOpAccessMessage[] newArray(int size) {
            return new RuntimeAppOpAccessMessage[size];
        }

        @Override
        public RuntimeAppOpAccessMessage createFromParcel(@NonNull Parcel in) {
            return new RuntimeAppOpAccessMessage(in);
        }
    };

    /*@DataClass.Generated(
            time = 1581517099127L,
            codegenVersion = "1.0.14",
            sourceFile = "frameworks/base/core/java/android/app/RuntimeAppOpAccessMessage.java",
            inputSignatures = "private final @android.annotation.IntRange(from=0L) int mUid\nprivate final @android.annotation.IntRange(from=0L, to=AppOpsManager._NUM_OP - 1) int mOpCode\nprivate final @android.annotation.NonNull java.lang.String mPackageName\nprivate final @android.annotation.Nullable java.lang.String mAttributionTag\nprivate final @android.annotation.NonNull java.lang.String mMessage\nprivate final @android.app.AppOpsManager.SamplingStrategy int mSamplingStrategy\npublic @android.annotation.NonNull java.lang.String getOp()\nclass RuntimeAppOpAccessMessage extends java.lang.Object implements [android.os.Parcelable]\n@com.android.internal.util.DataClass(genConstructor=false)")*/
    @Deprecated
    private void __metadata() {}


    //@formatter:on
    // End of generated code

}
