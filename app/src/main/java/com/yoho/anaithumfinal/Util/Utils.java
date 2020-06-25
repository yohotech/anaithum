package com.yoho.anaithumfinal.Util;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import static com.yoho.anaithumfinal.BuildConfig.DEBUG;


public class Utils {
    public static ProgressDialog mProgress;

    public static final String  domain="http://deeban.patroninternational.in/api/";
    private static int REQUEST=1001;
    public static final String downloadDirectory = "Tenant Downloads";
    public static int dp2px (int dp)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }
    public static boolean isLocal(String url) {
        if (url != null && !url.startsWith("http://") && !url.startsWith("https://")) {
            return true;
        }
        return false;
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                if (DEBUG)
                    DatabaseUtils.dumpCursor(cursor);

                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isDownloadsDocument(Uri uri) {
        System.out.println("value   "+uri.getAuthority());
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

 /*   @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static boolean isLocalStorageDocument(Uri uri) {
        return LocalStorageProvider.AUTHORITY.equals(uri.getAuthority());
    }*/
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void checkPermission (Context fragment, String permissionString, int permissionCode) {
        if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.M) || fragment == null) return;
        int existingPermissionStatus = ContextCompat.checkSelfPermission(fragment, permissionString);
        if (existingPermissionStatus == PackageManager.PERMISSION_GRANTED) return;
        Activity activity=(Activity)fragment;
        activity.requestPermissions(new String[]{permissionString}, permissionCode);
    }

    public static boolean isReadStorageGranted (Context context) {
        int storagePermissionGranted = ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        return storagePermissionGranted == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean isWriteStorageGranted (Context context) {
        int storagePermissionGranted = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return storagePermissionGranted == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean isCameraGranted (Context context) {
        int cameraPermissionGranted = ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA);
        return cameraPermissionGranted == PackageManager.PERMISSION_GRANTED;
    }


    public static Bitmap crop(Bitmap bitmap, int size, int size1) {

        int sourceWidth = bitmap.getWidth();
        int sourceHeight = bitmap.getHeight();

        float xScale = (float) size1 / sourceWidth;
        float yScale = (float) size / sourceHeight;

        float scale = Math.max(xScale, yScale);
        float scaledWidth = scale * sourceWidth;
        float scaledHeight = scale * sourceHeight;

        float left = (size1 - scaledWidth) / 2;
        float top = (size - scaledHeight) / 2;

        RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);

        Bitmap dest = Bitmap.createBitmap(size1, size, bitmap.getConfig());
        Canvas canvas = new Canvas(dest);
        canvas.drawBitmap(bitmap, null, targetRect, null);

        return dest;
    }

    public static ProgressDialog mProgress(Context context, String txt, Boolean aBoolean) {
        mProgress = new ProgressDialog(context);
        mProgress.setMessage(txt);
        mProgress.setCancelable(aBoolean);
        return mProgress;
    }
    public static int getversioncode(Context context) {
        PackageInfo pInfo = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pInfo.versionCode;
    }
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connec.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void setTextAnimation(View tv, int animType, Context context) {
        Animation a = AnimationUtils.loadAnimation(context, animType);
        a.reset();
        tv.clearAnimation();
        tv.startAnimation(a);
    }

}
