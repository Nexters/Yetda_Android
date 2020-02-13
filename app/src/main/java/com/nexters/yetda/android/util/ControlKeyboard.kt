package com.nexters.yetda.android.util

import android.app.Activity
import android.content.Context
import android.text.InputFilter
import android.view.View
import android.view.inputmethod.InputMethodManager


object ControlKeyboard {
    /* Show Keyboard */
    fun show(activity: Activity) {
        val inputMethodManager: InputMethodManager = activity
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val focus: View? = activity.currentFocus
        if (focus != null) inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    /* Hide Keyboard */
    fun hide(activity: Activity) {
        //        InputMethodManager inputMethodManager = (InputMethodManager) activity
//                .getSystemService(Context.INPUT_METHOD_SERVICE);
//        View focus = activity.getCurrentFocus();
//        if (focus != null)
//            inputMethodManager.hideSoftInputFromWindow(focus.getWindowToken(), 0);
        val view: View? = activity.currentFocus
        if (view != null) {
            val imm: InputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
            //                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    fun filter(): InputFilter {
        return InputFilter { source, start, end, dest, dstart, dend ->
            /**
             * 영문~숫자만 특수문자 제한
             */
            /**
             * 영문~숫자만 특수문자 제한
             */

            //            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            //                Pattern ps = Pattern.compile("^[a-zA-Z0-9]+$");
            //                if (!ps.matcher(source).matches()) {
            //                    return "";
            //                }
            //                return null;
            //            }
            /**
             * 공백만 입력 안되도록
             */
            /**
             * 공백만 입력 안되도록
             */
            for (i in start until end) {
                if (Character.isSpaceChar(source[i])) {
                    return@InputFilter ""
                }
            }
            null
        }
    }
}