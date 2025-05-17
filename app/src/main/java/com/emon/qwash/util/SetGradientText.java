package com.emon.qwash.util;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.widget.TextView;

public class SetGradientText {

    // Static method to apply gradient globally
    public static void applyGradient(TextView textView) {
        Shader shader = new LinearGradient(
                0, 0,
                textView.getPaint().measureText(textView.getText().toString()), 0,
                new int[]{Color.parseColor("#5F4BFF"), Color.parseColor("#5F4BFF"), Color.parseColor("#9E5CFF")},
                new float[]{0f, 0.7f, 0.7f},
                Shader.TileMode.CLAMP
        );
        textView.getPaint().setShader(shader);
        textView.invalidate(); // Redraw with the shader
    }
}
