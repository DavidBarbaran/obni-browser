package obni.browser.util

import android.app.Activity
import android.content.Context
import android.opengl.Visibility
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun hideKeyboard(context: Context, editText: EditText) {

    editText.requestFocus()
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(editText.windowToken, 0)
    editText.clearFocus()
}

fun fadeAnimation(view: View, visibility: Int) {

    var fromAlpha = 1.0f
    var toAlpha = 0.0f

    if (visibility == View.VISIBLE){
        fromAlpha = 0.0f
        toAlpha = 1.0f
    }

    val alphaAnimation = AlphaAnimation(fromAlpha, toAlpha)
    alphaAnimation.interpolator = AccelerateInterpolator()
    alphaAnimation.duration = 200
    alphaAnimation.setAnimationListener(object : Animation.AnimationListener{
        override fun onAnimationRepeat(p0: Animation?) {
        }

        override fun onAnimationEnd(p0: Animation?) {
            view.visibility = visibility
        }

        override fun onAnimationStart(p0: Animation?) {
        }
    })
    view.startAnimation(alphaAnimation)
}

/*

private void fadeOutAndHideImage(final ImageView img)
  {
    Animation fadeOut = new AlphaAnimation(1, 0);
    fadeOut.setInterpolator(new AccelerateInterpolator());
    fadeOut.setDuration(1000);

    fadeOut.setAnimationListener(new AnimationListener()
    {
            public void onAnimationEnd(Animation animation)
            {
                  img.setVisibility(View.GONE);
            }
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationStart(Animation animation) {}
    });

    img.startAnimation(fadeOut);
}
 */