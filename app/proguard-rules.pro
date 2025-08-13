# Keep your model classes
-keep class com.app.reelshort.Model.** { *; }
-keep class com.app.reelshort.Module.** { *; }


# Keep all classes and methods that are annotated with @Keep
-keep @androidx.annotation.Keep class * { *; }

# Keep Kotlin metadata
-keep class kotlin.Metadata { *; }

# Keep Kotlin coroutines
-dontwarn kotlinx.coroutines.**
-keep class kotlinx.coroutines.** { *; }

-keep class androidx.lifecycle.LiveData { *; }

# AndroidX Core, AppCompat, Material, ConstraintLayout
-keep class androidx.core.** { *; }
-keep class androidx.appcompat.** { *; }
-keep class com.google.android.material.** { *; }
-keep class androidx.constraintlayout.** { *; }


# Hilt
-keep class dagger.hilt.** { *; }
-keep class com.google.dagger.hilt.** { *; }
-dontwarn dagger.hilt.**
-dontwarn com.google.dagger.hilt.**


# Lifecycle, Activity, Fragment
-keep class androidx.lifecycle.** { *; }
-keep class androidx.activity.** { *; }
-keep class androidx.fragment.** { *; }

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class com.bumptech.glide.** { *; }
-dontwarn com.bumptech.glide.**


# Gson
-keep class com.google.code.gson.** { *; }
-keepattributes Signature
-keepattributes *Annotation*

# Keep line numbers for stack traces
-keepattributes SourceFile,LineNumberTable

# Google Play Services (Location, Maps)
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**

# Firebase (Crashlytics, Analytics)
-keep class com.google.firebase.** { *; }
-dontwarn com.google.firebase.**


# Lottie
-keep class com.airbnb.lottie.** { *; }
-dontwarn com.airbnb.lottie.**

# SDP/SSP
-keep class com.intuit.sdp.** { *; }
-keep class com.intuit.ssp.** { *; }

# ANR Watchdog
-keep class com.github.anrwatchdog.** { *; }
-dontwarn com.github.anrwatchdog.**

# DotsIndicator
-keep class com.tbuonomo.dotsindicator.** { *; }


# Obfuscate and remove unused code
-optimizationpasses 5
-allowaccessmodification
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*

# Use aggressive method overloading to make reverse engineering harder
-overloadaggressively

## Remove logging
#-assumenosideeffects class android.util.Log {
#    public static int v(...);
#    public static int d(...);
#    public static int i(...);
#    public static int w(...);
#    public static int e(...);
#}

# Ignore warnings for specific classes
-dontwarn retrofit2.**
-dontwarn okhttp3.**
-dontwarn com.google.firebase.**
-dontwarn com.bumptech.glide.**
-dontwarn com.airbnb.lottie.**
-dontwarn com.facebook.**

# Keep all drawables
-keepclassmembers class **.R$drawable { *; }


-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# Razorpay
-dontwarn com.razorpay.**
-keep class com.razorpay.** {*;}

-optimizations !method/inlining/*

-keepclasseswithmembers class * {
  public void onPayment*(...);
}

# Stripe
-keep class com.stripe.android.payments.** { *; }
-keepclassmembers class com.stripe.android.payments.* { *; }
-keep class com.stripe.android.paymentsheet.PaymentSheetAuthenticators { *; }
-keepclassmembers class com.stripe.android.paymentsheet.PaymentSheetAuthenticators { public static *; }
-keep class com.stripe.android.paymentsheet.** { *; }
-keepclassmembers class com.stripe.android.paymentsheet.* { *; }


# okhttp3
-keepattributes Signature
-keepattributes Annotation
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**

# Keep generic signature of Call, Response (R8 full mode strips signatures from non-kept items).
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response

# With R8 full mode generic signatures are stripped for classes that are not
# kept. Suspend functions are wrapped in continuations where the type argument
# is used.
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

# Remove logging
-assumenosideeffects class android.util.Log {
    public static int v(...);
    public static int d(...);
    public static int i(...);
    public static int w(...);
    public static int e(...);
}

#
## Gson
#-keepclassmembers class * {
#    *;
#}
#-keepattributes Signature
#-keepattributes *Annotation*
#-dontwarn sun.misc.**
#-keep class com.google.gson.** { *; }
#-keep class * implements com.google.gson.TypeAdapterFactory
#-keep class * implements com.google.gson.JsonSerializer
#-keep class * implements com.google.gson.JsonDeserializer
#
#
##retrofit2
#-keepattributes Signature, InnerClasses, EnclosingMethod
#-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
#-keepattributes AnnotationDefault
#-keepclassmembers,allowshrinking,allowobfuscation interface * {
#    @retrofit2.http.* <methods>;
#}
#-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
#-dontwarn javax.annotation.**
#-dontwarn kotlin.Unit
#-dontwarn retrofit2.KotlinExtensions
#-dontwarn retrofit2.KotlinExtensions$*
#-if interface * { @retrofit2.http.* <methods>; }
#-keep,allowobfuscation interface <1>
#-if interface * { @retrofit2.http.* <methods>; }
#-keep,allowobfuscation interface * extends <1>
#-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
#-if interface * { @retrofit2.http.* public *** *(...); }
#-keep,allowoptimization,allowshrinking,allowobfuscation class <3>
#-keep,allowobfuscation,allowshrinking class retrofit2.Response
#
#
## Keep Tink and Stripe classes
#-keep class com.google.crypto.tink.** { *; }
#-keep class com.stripe.** { *; }
#-keep class com.nimbusds.** { *; }
#-dontwarn lombok.**
