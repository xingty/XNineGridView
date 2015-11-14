package org.wiyi.ninegridview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by xing on 11/3/15.
 */
public class NineGridView extends ViewGroup {
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
        initView(context,attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        int defaultSpace = (int) getResources().getDimension(R.dimen.nine_grid_gap) ;
        if (attrs == null) {
            mSpace = defaultSpace ;
        } else {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NineGridView, 0, 0) ;
            mSpace = (int) a.getDimension(R.styleable.NineGridView_gap,0);
        }
    }

    public void setAdapter(NineGridAdapter adapter) {
        if (adapter == null) {
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
            View child = adapter.getView(i,recycleView) ;

            if (child != recycleView) {
                if (hasChild) { //为了防止有的逗比不复用RecycleView做处理
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
            return ;
        }

        int width = MeasureSpec.getSize(widthMeasureSpec) ;
        int availableWidth = width - getPaddingLeft() - getPaddingRight() ;
        if (childCount <= 1) {
            mChildWidth = availableWidth * 2 / 3 ;
            mChildHeight = mChildWidth;
        } else {
            mChildWidth = (availableWidth - mSpace * (3 - 1)) / 3 ;
            mChildHeight = mChildWidth;
        }

        int mode = MeasureSpec.getMode(widthMeasureSpec) ;
        int height = mChildHeight * mRows + mSpace * (mRows - 1);
        if (mode == MeasureSpec.UNSPECIFIED) {
            width = mChildWidth * mColumns + mSpace * (mColumns - 1);
            width += getPaddingLeft() + getPaddingRight() ;
        }

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
                        mListener.onImageCilcked(position,v);
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
        super.addView(child);
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

    public interface NineGridAdapter<T> {
        int getCount() ;
        T getItem(int position) ;
        View getView(int positon,View recycleView) ;
    }

    public interface OnImageClickListener {
        void onImageCilcked(int position, View view) ;
    }
}

