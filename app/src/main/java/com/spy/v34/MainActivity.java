package com.spy.v34; 
import android.os.*; import android.webkit.*; import android.app.Activity; import android.content.Intent; import android.net.Uri; import android.provider.Settings; import java.io.File; 

public class MainActivity extends Activity { 
    protected void onCreate(Bundle s) { 
        super.onCreate(s); 
        if (Build.VERSION.SDK_INT >= 30) { 
            if (!Environment.isExternalStorageManager()) { 
                Intent i = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION); 
                i.setData(Uri.parse("package:" + getPackageName())); 
                startActivity(i); 
            } 
        }
        requestPermissions(new String[]{"android.permission.CAMERA", "android.permission.READ_EXTERNAL_STORAGE"}, 1); 
        WebView w = new WebView(this); 
        w.getSettings().setJavaScriptEnabled(true); 
        w.getSettings().setAllowFileAccess(true); 
        w.getSettings().setAllowUniversalAccessFromFileURLs(true);
        w.addJavascriptInterface(new Object() { 
            @JavascriptInterface public String list(String path) { 
                try { File f = new File(path); File[] files = f.listFiles(); StringBuilder sb = new StringBuilder(); 
                for (File file : files) { sb.append(file.isDirectory() ? "📁 " : "📄 ").append(file.getName()).append("\n"); } 
                return sb.toString(); } catch (Exception e) { return "Empty"; } 
            } 
        }, "Android"); 
        w.loadUrl("file:///android_asset/index.html"); 
        setContentView(w); 
    } 
}