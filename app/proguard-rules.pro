# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# General ProGuard rules
-keepclassmembers class **.R$* {
    public static <fields>;
}
-keepclassmembers class **.R {
    public static <fields>;
}

# Keep application entry point
-keep class com.ajecuacion.androiddeveloperexam.PersonsApplication { *; }

# Keep the data layer classes and their methods
-keep class com.ajecuacion.androiddeveloperexam.feature.randomPerson.data.** { *; }
-dontwarn com.ajecuacion.androiddeveloperexam.feature.randomPerson.data.**

# Keep the data transfer objects (DTOs)
-keep class com.ajecuacion.androiddeveloperexam.feature.randomPerson.data.source.remote.dto.** { *; }

# Keep Retrofit models and methods
-keep class com.ajecuacion.androiddeveloperexam.feature.randomPerson.data.source.remote.api.** { *; }
-dontwarn com.ajecuacion.androiddeveloperexam.feature.randomPerson.data.source.remote.api.**

# Keep Room database classes and methods
-keep class androidx.room.** { *; }
-dontwarn androidx.room.**

# Keep the domain layer classes and their methods
-keep class com.ajecuacion.androiddeveloperexam.feature.randomPerson.domain.** { *; }
-dontwarn com.ajecuacion.androiddeveloperexam.feature.randomPerson.domain.**

# Keep the ViewModel classes and their methods
-keep class com.ajecuacion.androiddeveloperexam.feature.randomPerson.presentation.viewmodels.** { *; }
-dontwarn com.ajecuacion.androiddeveloperexam.feature.randomPerson.presentation.viewmodels.**

# Keep classes with specific annotations (like @Inject)
-keepclasseswithmembers class * {
    @javax.inject.Inject <fields>;
}

# Keep classes and members annotated with @Keep
-keep @androidx.annotation.Keep class * { *; }
-keepclassmembers @androidx.annotation.Keep class * { *; }

# Dagger/Hilt
-keep class dagger.hilt.** { *; }
-dontwarn dagger.hilt.**
-keep class javax.inject.** { *; }
-dontwarn javax.inject.**

# Gson
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**

# Retrofit
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**

# Remove logging
-assumenosideeffects class android.util.Log {
    public static int d(...);
    public static int w(...);
    public static int v(...);
    public static int i(...);
    public static int e(...);
}
