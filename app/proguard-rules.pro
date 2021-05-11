# Common
-keepattributes Signature, InnerClasses
-keepattributes *Annotation*
-keepattributes Exceptions
-dontwarn javax.annotation.**

# Remove logs
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
}
-assumenosideeffects class timber.log.Timber {
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
}

# Kotlin
-keep class kotlin.Metadata { *; }
-keep class kotlin.internal.annotations.AvoidUninitializedObjectCopyingCheck { *; }
-dontwarn kotlin.internal.annotations.AvoidUninitializedObjectCopyingCheck
-keepclassmembers enum com.**.**.mvvm.entity.** { *; }

# Gson
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

# Crashlytics
-keepattributes SourceFile,LineNumberTable
-keep class com.google.firebase.crashlytics. { *; }
-dontwarn com.google.firebase.crashlytics.

-keep public class com.android.installreferrer.** { *; }
-keep class com.appsflyer.** { *; }