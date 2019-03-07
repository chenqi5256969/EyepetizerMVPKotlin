package com.m163.eyepetizermvpkotlin.skin;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.m163.eyepetizermvpkotlin.R;
import com.m163.eyepetizermvpkotlin.skin.inter.IChangeSkinListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.m163.eyepetizermvpkotlin.skin.SkinConstantKt.DEFAULT_WALLPAPER_NAME;
import static com.m163.eyepetizermvpkotlin.skin.SkinConstantKt.EXTRA_WEAC_SHARE;
import static com.m163.eyepetizermvpkotlin.skin.SkinConstantKt.WALLPAPER_NAME;
import static com.m163.eyepetizermvpkotlin.skin.SkinConstantKt.WALLPAPER_PATH;


public class SkinUtils {


    public static void saveWallpaper(Context context, String saveType, String value) {
        SharedPreferences share = context.getSharedPreferences(
                EXTRA_WEAC_SHARE, Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = share.edit();
        edit.putString(saveType, value);
        edit.apply();
    }

    @SuppressWarnings("deprecation")
    public static void setBackground(ViewGroup vg, Activity activity,IChangeSkinListener listener) {

        SharedPreferences share = activity.getSharedPreferences(EXTRA_WEAC_SHARE,
                Activity.MODE_PRIVATE);
        String value = share.getString(WALLPAPER_PATH, null);
        if (value != null) {
            // 自定义壁纸
            Drawable drawable1 = Drawable.createFromPath(value);
            // 文件没有被删除
            if (drawable1 != null) {
                vg.setBackgroundDrawable(drawable1);
            } else {
                saveWallpaper(activity, WALLPAPER_NAME, DEFAULT_WALLPAPER_NAME);
                setWallpaper(vg, activity, share,listener);
            }
        } else {
            setWallpaper(vg, activity, share,listener);
        }
        setStatusBarTranslucent(vg, activity);
    }

    public static void setStatusBarTranslucent(ViewGroup vg, Activity activity) {
        // 如果版本在4.4以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 状态栏高度
            int height = getStatusBarHeight(activity);
            if (height <= 0) {
                return;
            }
            // 设置距离顶部状态栏垂直距离
            vg.setPadding(0, height, 0, 0);
            // 状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 导航栏透明
            //            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    public static int getStatusBarHeight(Activity activity) {
        int height = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            height = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }

    private static void setWallpaper(ViewGroup vg, Activity activity, SharedPreferences share, final IChangeSkinListener listener) {
        int resId = getResId(activity, share);
        Observable.just(resId).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                listener.changeSkin(integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        vg.setBackgroundResource(resId);
    }

    @SuppressWarnings("deprecation")
    public static void setBackgroundBlur(ViewGroup vg, Activity activity) {
        vg.setBackgroundDrawable(getWallPaperBlurDrawable(activity));
        setStatusBarTranslucent(vg, activity);
    }

    public static Drawable getWallPaperBlurDrawable(Context context) {
        Bitmap bitmap;
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        // 取得主题背景配置信息
        SharedPreferences share = context.getSharedPreferences(EXTRA_WEAC_SHARE,
                Activity.MODE_PRIVATE);
        String value = share.getString(WALLPAPER_PATH, null);
        // 默认壁纸为自定义
        if (value != null) {
            try {
                BitmapFactory.decodeStream(new FileInputStream(new File(value)), null, options);
                // 设置图片模糊度为20
                options.inSampleSize = 20;
                options.inJustDecodeBounds = false;
                // 使用设置的inSampleSize值再次解析图片
                bitmap = BitmapFactory.decodeStream(new FileInputStream(new File(value)),
                        null, options);
                bitmap = fastBlur(context, 0, value, bitmap, 20);
            } catch (FileNotFoundException e) {

                bitmap = setWallpaperBlur(context, options, share);
            }
        } else {
            bitmap = setWallpaperBlur(context, options, share);
        }

        // 返回经过毛玻璃模糊度20处理后的Bitmap
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    private static Bitmap setWallpaperBlur(Context context, BitmapFactory.Options options, SharedPreferences share) {
        int resId = getResId(context, share);
        BitmapFactory.decodeResource(context.getResources(), resId, options);
        // 设置图片模糊度为20
        options.inSampleSize = 20;
        options.inJustDecodeBounds = false;
        // 使用设置的inSampleSize值再次解析图片
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId, options);
        bitmap = fastBlur(context, resId, null, bitmap, 20);
        return bitmap;
    }

    private static int getResId(Context context, SharedPreferences share) {
        String value = share.getString(WALLPAPER_NAME, DEFAULT_WALLPAPER_NAME);
        Class drawable = R.drawable.class;
        int resId;
        try {
            Field field = drawable.getField(value);
            resId = field.getInt(field.getName());
        } catch (Exception e) {
            resId = R.drawable.wallpaper_0;
        }
        return resId;
    }

    /**
     * 毛玻璃效果
     *
     * @param context    context
     * @param resId      图片资源id
     * @param filePath   自定义壁纸path
     * @param sentBitmap bitmap
     * @param radius     模糊半径：指每个像素点周围模糊的半径，半径越大，
     *                   模糊程度约高，模糊效果越明显，同时，模糊计算耗费时间越长。
     * @return 模糊处理后的bitmap
     */
    private static Bitmap fastBlur(Context context, int resId, String filePath, Bitmap sentBitmap,
                                   int radius) {
        try {
            Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

            if (radius < 1) {
                return (null);
            }

            int w = bitmap.getWidth();
            int h = bitmap.getHeight();

            int[] pix = new int[w * h];
            //        Log.e("pix", w + " " + h + " " + pix.length);
            bitmap.getPixels(pix, 0, w, 0, 0, w, h);

            int wm = w - 1;
            int hm = h - 1;
            int wh = w * h;
            int div = radius + radius + 1;

            int r[] = new int[wh];
            int g[] = new int[wh];
            int b[] = new int[wh];
            int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
            int vmin[] = new int[Math.max(w, h)];

            int divsum = (div + 1) >> 1;
            divsum *= divsum;
            int temp = 256 * divsum;
            int dv[] = new int[temp];
            for (i = 0; i < temp; i++) {
                dv[i] = (i / divsum);
            }

            yw = yi = 0;

            int[][] stack = new int[div][3];
            int stackpointer;
            int stackstart;
            int[] sir;
            int rbs;
            int r1 = radius + 1;
            int routsum, goutsum, boutsum;
            int rinsum, ginsum, binsum;

            for (y = 0; y < h; y++) {
                rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
                for (i = -radius; i <= radius; i++) {
                    p = pix[yi + Math.min(wm, Math.max(i, 0))];
                    sir = stack[i + radius];
                    sir[0] = (p & 0xff0000) >> 16;
                    sir[1] = (p & 0x00ff00) >> 8;
                    sir[2] = (p & 0x0000ff);
                    rbs = r1 - Math.abs(i);
                    rsum += sir[0] * rbs;
                    gsum += sir[1] * rbs;
                    bsum += sir[2] * rbs;
                    if (i > 0) {
                        rinsum += sir[0];
                        ginsum += sir[1];
                        binsum += sir[2];
                    } else {
                        routsum += sir[0];
                        goutsum += sir[1];
                        boutsum += sir[2];
                    }
                }
                stackpointer = radius;

                for (x = 0; x < w; x++) {

                    r[yi] = dv[rsum];
                    g[yi] = dv[gsum];
                    b[yi] = dv[bsum];

                    rsum -= routsum;
                    gsum -= goutsum;
                    bsum -= boutsum;

                    stackstart = stackpointer - radius + div;
                    sir = stack[stackstart % div];

                    routsum -= sir[0];
                    goutsum -= sir[1];
                    boutsum -= sir[2];

                    if (y == 0) {
                        vmin[x] = Math.min(x + radius + 1, wm);
                    }
                    p = pix[yw + vmin[x]];

                    sir[0] = (p & 0xff0000) >> 16;
                    sir[1] = (p & 0x00ff00) >> 8;
                    sir[2] = (p & 0x0000ff);

                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];

                    rsum += rinsum;
                    gsum += ginsum;
                    bsum += binsum;

                    stackpointer = (stackpointer + 1) % div;
                    sir = stack[(stackpointer) % div];

                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];

                    rinsum -= sir[0];
                    ginsum -= sir[1];
                    binsum -= sir[2];

                    yi++;
                }
                yw += w;
            }
            for (x = 0; x < w; x++) {
                rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
                yp = -radius * w;
                for (i = -radius; i <= radius; i++) {
                    yi = Math.max(0, yp) + x;

                    sir = stack[i + radius];

                    sir[0] = r[yi];
                    sir[1] = g[yi];
                    sir[2] = b[yi];

                    rbs = r1 - Math.abs(i);

                    rsum += r[yi] * rbs;
                    gsum += g[yi] * rbs;
                    bsum += b[yi] * rbs;

                    if (i > 0) {
                        rinsum += sir[0];
                        ginsum += sir[1];
                        binsum += sir[2];
                    } else {
                        routsum += sir[0];
                        goutsum += sir[1];
                        boutsum += sir[2];
                    }

                    if (i < hm) {
                        yp += w;
                    }
                }
                yi = x;
                stackpointer = radius;
                for (y = 0; y < h; y++) {
                    // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                    pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) |
                            dv[bsum];

                    rsum -= routsum;
                    gsum -= goutsum;
                    bsum -= boutsum;

                    stackstart = stackpointer - radius + div;
                    sir = stack[stackstart % div];

                    routsum -= sir[0];
                    goutsum -= sir[1];
                    boutsum -= sir[2];

                    if (x == 0) {
                        vmin[y] = Math.min(y + r1, hm) * w;
                    }
                    p = x + vmin[y];

                    sir[0] = r[p];
                    sir[1] = g[p];
                    sir[2] = b[p];

                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];

                    rsum += rinsum;
                    gsum += ginsum;
                    bsum += binsum;

                    stackpointer = (stackpointer + 1) % div;
                    sir = stack[stackpointer];

                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];

                    rinsum -= sir[0];
                    ginsum -= sir[1];
                    binsum -= sir[2];

                    yi += w;
                }
            }
            bitmap.setPixels(pix, 0, w, 0, 0, w, h);
            return (bitmap);
        } catch (Exception e) {
            if (filePath == null) {
                return BitmapFactory.decodeResource(context.getResources(), resId);
            } else {
                return BitmapFactory.decodeFile(filePath);
            }
        }
    }
}
