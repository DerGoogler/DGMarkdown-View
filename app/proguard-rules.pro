-repackageclasses
-ignorewarnings
-dontwarn
-dontnote

-keep class com.dergoogler.markdown.MarkdownView.**

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-keep class * extends android.webkit.WebChromeClient { *; }
-dontwarn com.dergoogler.markdown.MarkdownView.**