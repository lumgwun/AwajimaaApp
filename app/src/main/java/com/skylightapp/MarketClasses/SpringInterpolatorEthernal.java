package com.skylightapp.MarketClasses;

import android.view.animation.Interpolator;

public class SpringInterpolatorEthernal implements Interpolator {

    private float factor = 1f; // default

    public SpringInterpolatorEthernal() {
    }

    public SpringInterpolatorEthernal(float factor) {
        this.factor = factor;
    }

    @Override
    public float getInterpolation(float input) {
        return (float) (Math.pow(2, (-10 * input)) * Math.sin(((2 * Math.PI) * (input - (factor / 4))) / factor) + 1);
    }
}
