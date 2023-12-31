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
#-keep class androidx.appcompat.widget.** { *; }

#-keep class dream.guys.hotdeskandroid.** { *; }
#-dontwarn mypackage.**
#-dontwarn dream.guys.hotdeskandroid.**
#-dontusemixedcaseclassnames
#-dontskipnonpubliclibraryclasses
#-verbose

#-keepnames class dream.guys.** {
# *;
#}
#-repackageclasses 'myobfuscated'
#-allowaccessmodification

-obfuscationdictionary "C:\Users\Dreamsuser\AndroidStudioProjects\hotdesk_android\dic.txt"
-classobfuscationdictionary "C:\Users\Dreamsuser\AndroidStudioProjects\hotdesk_android\dic.txt"
-packageobfuscationdictionary "C:\Users\Dreamsuser\AndroidStudioProjects\hotdesk_android\dic.txt"


-mergeinterfacesaggressively
-overloadaggressively
#-repackageclasses "dream.guys.hotdeskandroid"
-repackageclasses 'myobfuscated'


# Retrofit
-dontwarn retrofit2.**
-dontwarn org.codehaus.mojo.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keepattributes *Annotation*

-keepattributes RuntimeVisibleAnnotations
-keepattributes RuntimeInvisibleAnnotations
-keepattributes RuntimeVisibleParameterAnnotations
-keepattributes RuntimeInvisibleParameterAnnotations

-keepattributes EnclosingMethod
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}
-keepclasseswithmembers class * {
    @retrofit2.* <methods>;
}

-keepclasseswithmembers interface * {
    @retrofit2.* <methods>;
}