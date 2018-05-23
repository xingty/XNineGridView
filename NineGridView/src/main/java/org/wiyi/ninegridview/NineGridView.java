package org.wiyi.ninegridview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by xing on 11/3/15.
 */
public class NineGridView extends ViewGroup {
    private static final String TAG = "NineGridView" ;
    private NineGridAdapter mAdapter ;
    private OnImageClickListener mListener ;
    /**
     * total rows
     */
    private int mRows;
    /**
     * total columns
     */
    private int mColumns;
    /**
     * child's space
     */
    private int mSpace ;
    private int mChildWidth;
    private int mChildHeight;

    public NineGridView(Context context) {
        this(context, null) ;
    }

    public NineGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        int defaultSpace = (int) getResources().getDimension(R.dimen.nine_grid_gap) ;
        if (attrs == null) {
            mSpace = defaultSpace ;
            return ;
        }

        TypedArray a = null ;
        try {
            a = context.obtainStyledAttributes(attrs, R.styleable.NineGridView, 0, 0) ;
            mSpace = (int) a.getDimension(R.styleable.NineGridView_gap,0);
        } finally {
            a.recycle();
        }
    }

    public void setAdapter(NineGridAdapter adapter) {
        if (adapter == null || adapter.getCount() <= 0) {
            removeAllViews(); //避免listview复用显示脏数据
            return ;
        }

        int oldCount = getChildCount() ;
        int newCount = adapter.getCount() ;
        removeScrapViews(oldCount,newCount);
        mAdapter = adapter ;
        initMatrix(newCount);
        addChildren(adapter);
    }

    private void removeScrapViews(int oldCount, int newCount) {
        if (newCount < oldCount) {
            removeViews(newCount - 1, oldCount - newCount);
        }
    }

    private void initMatrix(int length) {
        if (length <= 3) {
            mRows = 1;
            mColumns = length;
        } else if (length <= 6) {
            mRows = 2;
            mColumns = 3; //因为length <=6 所以实际Columns<3也是不会导致计算出问题的
            if (length == 4) {
                mColumns = 2;
            }
        } else {
            mRows = 3;
            mColumns = 3;
        }
    }

    private void addChildren(NineGridAdapter adapter) {
        int childCount = getChildCount() ;
        int count = adapter.getCount() ;
        for (int i=0;i<count;i++) {
            boolean hasChild = i < childCount ;
            //简单的回收机制,主要是为ListView做优化
            View recycleView = hasChild ? getChildAt(i) : null ;
            View child = adapter.getView(this,i,recycleView) ;

            if (child != recycleView) {
                if (hasChild) {
                    removeView(recycleView);
                }

                addView(child);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int childCount = getChildCount() ;
        if (childCount <= 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return ;
        }

        final int minW = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth() ;
        int width = resolveSizeAndState(minW,widthMeasureSpec,1) ;
        int availableWidth = width - getPaddingLeft() - getPaddingRight() ;
        if (childCount <= 1) {
            mChildWidth = availableWidth * 2 / 3 ;
            mChildHeight = mChildWidth;
        } else {
            mChildWidth = (availableWidth - mSpace * (mColumns - 1)) / 3 ;
            mChildHeight = mChildWidth;
        }
        int height = mChildHeight * mRows + mSpace * (mRows - 1);

        setMeasuredDimension(width, height + getPaddingTop() + getPaddingBottom());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        layoutChildren() ;
    }

    protected void layoutChildren() {
        if (mRows <= 0 || mColumns <= 0) {
            return ;
        }

        final int childCount = getChildCount() ;
        for (int i=0;i<childCount;i++) {
            ImageView view = (ImageView) getChildAt(i);

            int row = i / mColumns ;
            int col = i % mColumns ;
            int left = (mChildWidth + mSpace) * col + getPaddingLeft() ;
            int top = (mChildHeight + mSpace) * row + getPaddingTop() ;
            int right = left + mChildWidth;
            int bottom = top + mChildHeight;

            view.layout(left, top, right, bottom);

            final int position = i ;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onImageClicked(position,v);
                    }
                }
            });
        }
    }

    @Override
    public void addView(View child) {
        if (!(child instanceof ImageView)) {
            throw new ClassCastException("addView(View child) NineGridView只能放ImageView") ;
        }

        setChildLayoutParams(child);
        super.addView(child);
    }

    /**
     * 给child view设置绝对大小,减少child view measure次数
     * @param view
     */
    private void setChildLayoutParams(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams() ;
        if (params != null) {
            params.width = mChildWidth ;
            params.height = mChildHeight ;
        }
    }

    public void setOnImageClickListener(OnImageClickListener listener) {
        mListener = listener ;
    }

    public void setSpace(int space) {
        mSpace = space ;
    }

    public int getSpace() {
        return mSpace ;
    }

    public int getChildWidth() {
        return mChildWidth ;
    }

    public int getChildHeight() {
        return mChildHeight ;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
    }

    public interface NineGridAdapter<T> {
        int getCount() ;
        T getItem(int position) ;
        View getView(ViewGroup parent,int position,View recycleView) ;
    }

    public interface OnImageClickListener {
        void onImageClicked(int position, View view) ;
    }
}

