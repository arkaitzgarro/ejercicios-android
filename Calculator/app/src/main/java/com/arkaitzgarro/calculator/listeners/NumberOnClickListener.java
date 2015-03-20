package com.arkaitzgarro.calculator.listeners;

import android.view.View;
import android.widget.Button;

import com.arkaitzgarro.calculator.MainActivity;

/**
 * Created by arkaitz on 20/03/15.
 */
public class NumberOnClickListener implements View.OnClickListener {

    public interface NumberListenerInterface {
        public void setNumber(String number);
    }

    private NumberListenerInterface target;

    public NumberOnClickListener(NumberListenerInterface target) {
        this.target = target;
    }

    @Override
    public void onClick(View v) {
        Button btn = (Button) v;

        target.setNumber(btn.getText().toString());
    }
}
