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

-keep class com.example.filmrecommendation.** { *; }
-keep class androidx.** { *; }
-keep class com.google.gson.** { *; }
-keep class dagger.hilt.** { *; }
-keep class com.squareup.moshi.** { *; }
-keepattributes *Annotation*

# Keep Hilt/Dagger generated classes
-keep class * extends dagger.hilt.internal.GeneratedComponent { *; }

# Coil image loading
-keep class coil.** { *; }

# Prevent obfuscation of model classes used with Gson (if you're using @SerializedName)
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Keep Compose Preview
-keep class androidx.compose.ui.tooling.preview.PreviewParameterProvider { *; }
-dontwarn androidx.compose.**

# Logging
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}
