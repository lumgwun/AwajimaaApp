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

-keep class org.sqlite.** { *; }
-keepnames class org.sqlite.* { *; }

    # ISW classes
    -keep public class com.interswitchng.iswmobilesdk.IswMobileSdk {
      public protected *;
    }

    -keep public interface com.interswitchng.iswmobilesdk.IswMobileSdk$IswPaymentCallback {*;}

    -keep public class com.interswitchng.iswmobilesdk.shared.models.core.** {
        public protected *;
        !transient <fields>;
    }
    -keep public class com.interswitchng.iswmobilesdk.shared.models.payment.** {
        public protected *;
        !transient <fields>;
    }

    # SC provider
    -keep class org.spongycastle.**
    -dontwarn org.spongycastle.jce.provider.X509LDAPCertStoreSpi
    -dontwarn org.spongycastle.x509.util.LDAPStoreHelper

-keepclasseswithmembers public class com.flutterwave.raveandroid.** {*; }
-dontwarn com.flutterwave.raveandroid.card.CardFragment
-keep

class com.blongho.** {
    *;
}

-keep

interface com.blongho.**
-keep

interface com.blongho.**
#

# hide the original source file name.
# -renamesourcefileattribute SourceFile
-keeppackagenames com.blongho.country_data
-keepclassmembers

class com.blongho.country_data.* {
    public *;
}
-keep

class com.blongho.country_data.R$* {
    *;
}
-keep class org.jivesoftware.smack.initializer.VmArgInitializer { public *; }
-keep class org.jivesoftware.smack.ReconnectionManager { public *; }
-keep class com.quickblox.core.account.model.** { *; }
-keep class org.apache.** { *; }
-dontwarn org.apache.commons.logging.**
-keep class org.apache.commons.logging.impl.** { *; }

#quickblox sample chat
-keep class com.quickblox.auth.parsers.** { *; }
-keep class com.quickblox.auth.model.** { *; }
-keep class com.quickblox.core.parser.** { *; }
-keep class com.quickblox.core.model.** { *; }
-keep class com.quickblox.core.server.** { *; }
-keep class com.quickblox.core.rest.** { *; }
-keep class com.quickblox.core.error.** { *; }
#noinspection ShrinkerUnresolvedReference
-keep class com.quickblox.core.Query { *; }

-keep class com.quickblox.users.parsers.** { *; }
-keep class com.quickblox.users.model.** { *; }

-keep class com.quickblox.chat.parser.** { *; }
-keep class com.quickblox.chat.model.** { *; }

-keep class com.quickblox.messages.parsers.** { *; }
-keep class com.quickblox.messages.model.** { *; }

-keep class com.quickblox.content.parsers.** { *; }
-keep class com.quickblox.content.model.** { *; }

-keep class org.jivesoftware.** { *; }

 #sample chat
-keep class android.support.v7.** { *; }
-keep class com.bumptech.** { *; }

-dontwarn org.jivesoftware.smackx.**
-dontwarn android.support.v4.app.**
-dontwarn libdashplayer.so.**



