package cn.net.xinyi.seek.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.security.MessageDigest;

import cn.net.xinyi.seek.R;


public class GlideUtils {
    private static final String TAG = "ImageLoaderUtils";


    public static void showImages(Context context,String path,ImageView mImageView){
        Glide.with(context).load(path).into(mImageView);
    }

    /**
     * 圆角图片加载
     *
     * @param context      上下文
     * @param imageView    图片显示控件
     * @param url          图片链接
     * @param defaultImage 默认占位图片
     * @param errorImage   加载失败后图片
     * @param radius       图片圆角半径
     * @return
     * @author leibing
     * @createTime 2016/8/15
     * @lastModify 2016/8/15
     */
    public static void load(Context context, ImageView imageView, String url, int defaultImage,
                            int errorImage, int radius) {
        //RequestOptions 设置请求参数，通过apply方法设置
        RequestOptions options = new RequestOptions()
                // 但不保证所有图片都按序加载
                // 枚举Priority.IMMEDIATE，Priority.HIGH，Priority.NORMAL，Priority.LOW
                // 默认为Priority.NORMAL
                // 如果没设置fallback，model为空时将显示error的Drawable，
                // 如果error的Drawable也没设置，就显示placeholder的Drawable
                .priority(Priority.NORMAL) //指定加载的优先级，优先级越高越优先加载，
                .placeholder(defaultImage)
                .error(errorImage)
                // 缓存原始数据
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
                .transform(new CornersTranform(context, radius));
        // 图片加载库采用Glide框架
        Glide.with(context).load(url).apply(options)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(imageView);

    }



    /**
     * 加载resoures下的文件
     *
     * @param context
     * @param imageView
     * @param url
     * @param defaultImage
     * @param errorImage
     */
    public static void loadImgId(Context context, final ImageView imageView, int url, int defaultImage,
                                 int errorImage, int radius) {
        RequestOptions options = new RequestOptions()
                // 但不保证所有图片都按序加载
                // 枚举Priority.IMMEDIATE，Priority.HIGH，Priority.NORMAL，Priority.LOW
                // 默认为Priority.NORMAL
                // 如果没设置fallback，model为空时将显示error的Drawable，
                // 如果error的Drawable也没设置，就显示placeholder的Drawable
                .priority(Priority.NORMAL) //指定加载的优先级，优先级越高越优先加载，
                .placeholder(defaultImage)
                .error(errorImage)
                // 缓存原始数据
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
                .transform(new CornersTranform(context, radius));
        // 图片加载库采用Glide框架
        Glide.with(context).load(url)
                .apply(options)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(imageView);
    }


    /**
     * 加载圆形头像
     *
     * @param context
     * @param imageView
     * @param url
     * @param defaultImage
     * @param errorImage
     */
    public static void loadCircle(Context context, final ImageView imageView, String url, int defaultImage,
                                  int errorImage) {
        RequestOptions options = new RequestOptions()
                // 但不保证所有图片都按序加载
                // 枚举Priority.IMMEDIATE，Priority.HIGH，Priority.NORMAL，Priority.LOW
                // 默认为Priority.NORMAL
                // 如果没设置fallback，model为空时将显示error的Drawable，
                // 如果error的Drawable也没设置，就显示placeholder的Drawable
                .priority(Priority.NORMAL) //指定加载的优先级，优先级越高越优先加载，
                .dontAnimate() //防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                .placeholder(defaultImage)
                .error(errorImage)
                // 缓存原始数据
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
                .transform(new GlideCircleTransform1(context, 7, context.getResources().getColor(R.color.white)));
        // 图片加载库采用Glide框架
        Glide.with(context).load(url)
                .apply(options)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        imageView.setImageDrawable(resource);
                    }
                });
    }
}


/**
 * glide处理圆角图片
 * Created by Administrator on 2017/12/13 0013.
 */

class CornersTranform extends BitmapTransformation {
    private float radius;

    public CornersTranform(Context context) {
        super();
        radius = 10;
    }

    public CornersTranform(Context context, float radius) {
        super();
        this.radius = radius;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return cornersCrop(pool, toTransform);
    }

    private Bitmap cornersCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);

        if (result != null && !result.isRecycled()) {
            result.recycle();
            result = null;
        }

        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);
        return result;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {

    }
}


/**
 * glide加载圆形图片
 * Created by Administrator on 2018/2/3 0003.
 */

class GlideCircleTransform extends BitmapTransformation {
    public GlideCircleTransform(Context context) {
        super();
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }

    private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;
        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

        return result;
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {

    }
}


class GlideCircleTransform1 extends BitmapTransformation {

   private Paint mBorderPaint;
   private float mBorderWidth;



   public GlideCircleTransform1(Context context, int borderWidth, int borderColor) {
       super();
       mBorderWidth = Resources.getSystem().getDisplayMetrics().density * borderWidth;
       mBorderPaint = new Paint();
       mBorderPaint.setDither(true);
       mBorderPaint.setAntiAlias(true);
       mBorderPaint.setColor(borderColor);
       mBorderPaint.setStyle(Paint.Style.STROKE);
       mBorderPaint.setStrokeWidth(mBorderWidth);
   }


   protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
       return circleCrop(pool, toTransform);
   }

   private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
       if (source == null) return null;

       int size = (int) (Math.min(source.getWidth(), source.getHeight()) - (mBorderWidth / 2));
       int x = (source.getWidth() - size) / 2;
       int y = (source.getHeight() - size) / 2;

       Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
       Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
       if (result == null) {
           result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
       }
       Canvas canvas = new Canvas(result);
       Paint paint = new Paint();
       paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
       paint.setAntiAlias(true);
       float r = size / 2f;
       canvas.drawCircle(r, r, r, paint);
       if (mBorderPaint != null) {
           float borderRadius = r - mBorderWidth / 2;
           canvas.drawCircle(r, r, borderRadius, mBorderPaint);
       }
       return result;
   }

   @Override
   public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

   }
}