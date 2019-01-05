package cn.net.xinyi.seek.util;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

public class DrawableUtil {

    public static void loadImageView2TextView(int images, TextView textView,String name){
        textView.setText(name);
        Drawable nav_up = textView.getContext().getResources().getDrawable(images);
        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
        textView.setCompoundDrawables(nav_up, null, null, null);
    }
}
