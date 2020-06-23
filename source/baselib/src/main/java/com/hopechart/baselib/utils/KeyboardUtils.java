package com.hopechart.baselib.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.hardware.input.InputManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.hopechart.baselib.R;

/**
 * 类描述：
 * 创建人：Simple
 * 创建时间：2018/2/6 19:53
 * 修改备注：
 */
public class KeyboardUtils {

    private KeyboardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            view.requestFocus();
            imm.showSoftInput(view, 0);
        }
    }

    public static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void toggleSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(0, 0);
        }
    }

    //查看键盘的弹出状态，注意这里指示简单的根据EditText来判断
    public static boolean checkKeyboardStatus(EditText editText){
        InputMethodManager manager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if(manager != null){
            return manager.isActive(editText);
        }
        return false;
    }

    public static void putTextIntoClip(Context context, String text) {
        if(TextUtils.isEmpty(text)) {
            ToastUtils.showShort(R.string.baselib_copy_empty);
            return;
        }

        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        //创建ClipData对象
        ClipData clipData = ClipData.newPlainText("netMi", text);
        //添加ClipData对象到剪切板中
        if (clipboardManager != null) {
            clipboardManager.setPrimaryClip(clipData);
            ToastUtils.showShort(R.string.baselib_copy_success);
        } else {
            ToastUtils.showShort(R.string.baselib_copy_fail);
        }
    }

    public static void putTextIntoClip(Context context, String text, boolean b) {
        if(TextUtils.isEmpty(text)) {
            ToastUtils.showShort(R.string.baselib_copy_empty);
            return;
        }

        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        //创建ClipData对象
        ClipData clipData = ClipData.newPlainText("netMi", text);
        //添加ClipData对象到剪切板中
        if (clipboardManager != null) {
            clipboardManager.setPrimaryClip(clipData);
        } else {
            ToastUtils.showShort(R.string.baselib_copy_fail);
        }
    }
}
