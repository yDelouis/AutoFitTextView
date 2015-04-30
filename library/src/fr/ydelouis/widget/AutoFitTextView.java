package fr.ydelouis.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.TextView;

public class AutoFitTextView extends TextView
{
	private static final float THRESHOLD = 0.5f;
	
	private enum Mode { Width, Height, Both, None }
	
	private int minTextSize = 1;
	private int maxTextSize = 1000;
	
	private Mode mode = Mode.None;
	private boolean inComputation;
	private int widthMeasureSpec;
	private int heightMeasureSpec;

	public AutoFitTextView(Context context) {
		super(context);
	}
	
	public AutoFitTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public AutoFitTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		TypedArray tAttrs = context.obtainStyledAttributes(attrs, R.styleable.AutoFitTextView, defStyle, 0);
		maxTextSize = tAttrs.getDimensionPixelSize(R.styleable.AutoFitTextView_maxTextSize, maxTextSize);
		minTextSize = tAttrs.getDimensionPixelSize(R.styleable.AutoFitTextView_minTextSize, minTextSize);
		tAttrs.recycle();

        // enable scrolling
        setMovementMethod(new ScrollingMovementMethod());

        // disables the text wrap
        setHorizontallyScrolling(true);
	}

	private void resizeText() {
		if (getWidth() <= 0 || getHeight() <= 0)
			return;
		
		if(mode == Mode.None)
			return;
		
		final int targetWidth = getWidth();
		final int targetHeight = getHeight();
		
		inComputation = true;
		float higherSize = maxTextSize;
		float lowerSize = minTextSize;
		float textSize = getTextSize();
		while(higherSize - lowerSize > THRESHOLD) {
			textSize = (higherSize + lowerSize) / 2;
			if (isTooBig(textSize, targetWidth, targetHeight)) {
				higherSize = textSize; 
			} else {
				lowerSize = textSize;
			}
		}
		setTextSize(TypedValue.COMPLEX_UNIT_PX, lowerSize);
		measure(widthMeasureSpec, heightMeasureSpec);
		inComputation = false;
	}
	
	private boolean isTooBig(float textSize, int targetWidth, int targetHeight) {
		setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
		measure(0, 0);
		if(mode == Mode.Both)
			return getMeasuredWidth() >= targetWidth || getMeasuredHeight() >= targetHeight;
		if(mode == Mode.Width)
			return getMeasuredWidth() >= targetWidth;
		else
			return getMeasuredHeight() >= targetHeight;
	}
	
	private Mode getMode(int widthMeasureSpec, int heightMeasureSpec) {
        // MeasureSpec.getMode doesn't work as expected.
        // I thought the mode is MeasureSpec.EXACTLY when the layout
        // is MATCH_PARENT/FILL_PARENT,but actually it's this:
        // +---------------------------------------------------------+
        // |         LayoutParams          |    MeasureSpec Mode     |
        // | layout_width  | layout_height | horizontal | vertical   |
        // +---------------+---------------+------------+------------+
        // |  wrap_content |  wrap_content |      both* |    AT_MOST |
        // |  wrap_content |  match_parent |      both* |      both* |
        // |  match_parent |  wrap_content |    EXACTLY |      both* |
        // |  match_parent |  match_parent |    EXACTLY |    EXACTLY |
        // +---------------+---------------+------------+------------+
        // *) onMeasure gets called multiple times and when "both" is
        //    specified in the table above, the MeasureSpec Mode is
        //    on one call MeasureSpec.AT_MOST and in the other call
        //    it's MeasureSpec.EXACTLY.

        int widthParams = getLayoutParams().width;
        int heightParams = getLayoutParams().height;

        if (widthParams == ViewGroup.LayoutParams.WRAP_CONTENT) {
            if (heightParams == ViewGroup.LayoutParams.WRAP_CONTENT) {
                return Mode.None;
            } else { // heightParams == MATCH_PARENT (or FILL_PARENT)
                return Mode.Height;
            }
        } else {     // widthParams  == MATCH_PARENT (or FILL_PARENT)
            if (heightParams == ViewGroup.LayoutParams.WRAP_CONTENT) {
                return Mode.Width;
            } else { // heightParams == MATCH_PARENT (or FILL_PARENT)
                return Mode.Both;
            }
        }
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if(!inComputation) {
			this.widthMeasureSpec = widthMeasureSpec;
			this.heightMeasureSpec = heightMeasureSpec;
			mode = getMode(widthMeasureSpec, heightMeasureSpec);
			resizeText();
		}
	}
	
	@Override
	protected int getSuggestedMinimumWidth() {
		Drawable background = getBackground();
		setBackground(null);
		int minWidth = super.getSuggestedMinimumWidth();
		setBackground(background);
		return minWidth;
	}
	
	@Override
	protected int getSuggestedMinimumHeight() {
		Drawable background = getBackground();
		setBackground(null);
		int minHeight = super.getSuggestedMinimumHeight();
		setBackground(background);
		return minHeight;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setBackground(Drawable background) {
		if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN)
			super.setBackground(background);
		else
			setBackgroundDrawable(background);
	}

	@Override
	protected void onTextChanged(final CharSequence text, final int start, final int before, final int after) {
		resizeText();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		if (w != oldw || h != oldh)
			resizeText();
	}

	public int getMinTextSize() {
		return minTextSize;
	}

	public void setMinTextSize(int minTextSize) {
		this.minTextSize = minTextSize;
		resizeText();
	}

	public int getMaxTextSize() {
		return maxTextSize;
	}

	public void setMaxTextSize(int maxTextSize) {
		this.maxTextSize = maxTextSize;
		resizeText();
	}
}
