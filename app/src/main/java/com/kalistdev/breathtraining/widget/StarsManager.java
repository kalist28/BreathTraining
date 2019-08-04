package com.kalistdev.breathtraining.widget;

import java.util.List;
import android.view.View;
import java.util.ArrayList;
import android.content.Context;
import android.widget.TextView;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.view.LayoutInflater;
import com.kalistdev.breathtraining.R;

/**
 * Breath TrainingRecord Application
 *
 * This file is part of the Breath TrainingRecord package.
 * This class describes the view of Stars ParametersManager.
 *
 * @author  Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public class StarsManager extends FrameLayout implements View.OnClickListener {

    /** ID of the first star. */
    private static final int STAR_ONE   = 0;

    /** ID of the second star. */
    private static final int STAR_TWO   = 1;

    /** Third star ID. */
    private static final int STAR_THREE = 2;

    /** Fourth star ID. */
    private static final int STAR_FOUR  = 3;

    /** Fifth star ID. */
    private static final int STAR_FIVE  = 4;

    /** Number of positive stars. */
    private int mCountStars;

    /** Object containing all stars. */
    private List<ImageButton> stars = new ArrayList<>();

    /** Text in the top on layout. */
    private TextView topText;

    /** Text in the bottom on layout. */
    private TextView bottomText;

    /** The Id of the stars in Stars ParametersManager. */
    @SuppressWarnings("FieldCanBeLocal")
    private final int[] idViewStartElements
            =  {R.id.stars_manager_star_one,
                R.id.stars_manager_star_two,
                R.id.stars_manager_star_three,
                R.id.stars_manager_star_four,
                R.id.stars_manager_star_five};

    /**
     * Constructor - create a new object.
     * @param context - application context.
     */
    public StarsManager(final Context context) {
        super(context);
        initialize();
    }

    /**
     * Constructor - create a new object.
     * @param context   - application context.
     * @param attrs     - the required initial attributes.
     */
    public StarsManager(final Context context,
                        final AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    /** Initialization method of the object. */
    private void initialize() {
        LayoutInflater layoutInflater
                = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.manager_stars, this);

        topText     = findViewById(R.id.stars_manager_top_text);
        bottomText  = findViewById(R.id.stars_manager_bottom_text);

        if (stars.size() != idViewStartElements.length + 1) {
            for (int i = 0; i <  idViewStartElements.length; i++) {
                stars.add((ImageButton) findViewById(idViewStartElements[i]));
                stars.get(i).setOnClickListener(this);
            }
        }
        setStars(-1);
    }

    @Override
    public final void onClick(final View view) {
        String[] levels = getResources().getStringArray(R.array.levels);

        switch (view.getId()) {

            case R.id.stars_manager_star_one: {
                    setStars(STAR_ONE);
                    bottomText.setText(levels[STAR_ONE]);
                    break;
            }

            case R.id.stars_manager_star_two: {
                setStars(STAR_TWO);
                bottomText.setText(levels[STAR_TWO]);
                break;
            }

            case R.id.stars_manager_star_three: {
                setStars(STAR_THREE);
                bottomText.setText(levels[STAR_THREE]);
                break;
            }

            case R.id.stars_manager_star_four: {
                setStars(STAR_FOUR);
                bottomText.setText(levels[STAR_FOUR]);
                break;
            }

            case R.id.stars_manager_star_five: {
                setStars(STAR_FIVE);
                bottomText.setText(levels[STAR_FIVE]);
                break;
            }

            default: {
                bottomText.setText(levels[STAR_ONE]);
                break;
            }
        }
    }

    /** Sets the number of positive stars and image on view.
     * @param idStar - number of positive stars.
     */
    public final void setStars(final int idStar) {
        mCountStars = -1;
        for (int i = 0; i < stars.size(); i++) {
            if (i <= idStar) {
                stars.get(i)
                        .setImageResource(R.drawable.star);
                mCountStars++;
            } else {
                stars.get(i)
                        .setImageResource(R.drawable.star_black);
            }
        }
    }

    /**
     * Sets the size and margin on all StarsView.
     * @param size      - height and wight star;
     * @param margin    - margins view.
     */
    public void setSizeStar(final int size,
                            final int margin) {
        float density = getContext().getResources().getDisplayMetrics().density;
        int mSize = (int) (size * density);
        int mMargin = (int) (margin * density);
        LinearLayout.LayoutParams params
                = new LinearLayout.LayoutParams(mSize, mSize);
        params.setMargins(mMargin, mMargin, mMargin, mMargin);

        for (ImageButton button : stars) {
            button.setLayoutParams(params);
        }
    }

    /** Set clickable property.
     *
     * @param clickable - clickable true or false.
     */
    public final void setClickable(final boolean clickable) {
        for (ImageButton v : stars) {
            v.setClickable(clickable);
        }
    }

    /**
     * Function hides all text views.
     */
    public void hideAllText() {
        topText.setVisibility(GONE);
        bottomText.setVisibility(GONE);
    }

    /**
     * Function to get value of field {@link StarsManager#mCountStars}.
     * @return returns the number of positive stars.
     */
    public int getCountStars() {
        return mCountStars;
    }

    /**
     * Function to get value of field {@link StarsManager#topText}.
     * @return returns the top TextView.
     */
    public TextView getTopText() {
        return topText;
    }

    /**
     * Function to get value of field {@link StarsManager#bottomText}.
     * @return returns the bottom TextView.
     */
    public TextView getBottomText() {
        return bottomText;
    }
}
