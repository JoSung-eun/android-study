package tada.study.firebasesamples;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class FrameView extends ConstraintLayout {
    View bonusScoreView;
    TextView frameNumberTextView;

    public FrameView(Context context) {
        super(context);
        init();
    }

    public FrameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        getAttrs(attrs);
    }

    public FrameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        getAttrs(attrs, defStyleAttr);
    }

    private void init() {
        inflate(getContext(), R.layout.include_frame_view, this);
        bonusScoreView = findViewById(R.id.score_bonus);
        frameNumberTextView = findViewById(R.id.frame);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.FrameView);

        setTypeArray(typedArray);
    }


    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.FrameView, defStyle, 0);
        setTypeArray(typedArray);

    }

    private void setTypeArray(TypedArray typedArray) {
        boolean isLastGame = typedArray.getBoolean(R.styleable.FrameView_isLastFrame, false);
        int frameNumber = typedArray.getInteger(R.styleable.FrameView_frameNumber, 0);
        if (bonusScoreView != null) {
            bonusScoreView.setVisibility(isLastGame ? VISIBLE : GONE);
        }
        if (frameNumberTextView != null) {
            frameNumberTextView.setText(String.valueOf(frameNumber));
        }
        typedArray.recycle();
    }

}
