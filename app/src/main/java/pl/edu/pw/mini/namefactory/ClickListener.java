package pl.edu.pw.mini.namefactory;

import android.view.View;

/**
 * Created by Asus on 14.04.2017.
 */

public interface ClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
