package tada.study.firebasesamples;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

public class TitleBarLayout extends ConstraintLayout {
    private TextView titleTextView;
    private ImageView menuIconImageView;

    public TitleBarLayout(Context context) {
        super(context);
        init();
    }

    public TitleBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TitleBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.include_title, this);
        titleTextView = findViewById(R.id.title_textview);
        menuIconImageView = findViewById(R.id.menu_icon_imageview);
    }

    public void setTitle(@StringRes int titleRes) {
        if (titleRes == -1) return;
        titleTextView.setText(titleRes);
    }

    public void setMenuIcon(@DrawableRes int iconRes) {
        if (iconRes == -1) return;
        menuIconImageView.setImageResource(iconRes);
    }

    public void setMenuClickListener(OnClickListener menuClickListener) {
        menuIconImageView.setOnClickListener(menuClickListener);
    }

    public void setMenuIconVisibility(boolean visible) {
        menuIconImageView.setVisibility(visible ? VISIBLE : GONE);
    }
}
