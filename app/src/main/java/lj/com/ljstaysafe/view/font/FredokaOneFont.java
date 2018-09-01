package lj.com.ljstaysafe.view.font;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class FredokaOneFont extends AppCompatTextView {

    public FredokaOneFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/fredoka_one_regular.ttf"));
    }

}
