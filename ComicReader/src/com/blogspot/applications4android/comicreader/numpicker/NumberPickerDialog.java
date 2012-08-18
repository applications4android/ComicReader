package com.blogspot.applications4android.comicreader.numpicker;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

/**
 * Dialog for NumberPicker
 */
public class NumberPickerDialog extends Dialog {
	/** number picker */
	private NumberPicker m_np;

	public interface OnSetListener {
		public void onNumSet(NumberPicker view, int num);
	};

	/**
	 * Constructor
	 */
	public NumberPickerDialog(Context context, final OnSetListener onSet, int min, int max, int current) {
		super(context);
		m_np = new NumberPicker(context);
		m_np.setRange(min, max);
		m_np.onCancelListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				NumberPickerDialog.this.dismiss();
			}
		});
		m_np.onSetListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				onSet.onNumSet(m_np, m_np.getCurrent());
				NumberPickerDialog.this.dismiss();
			}
		});
		m_np.setCurrent(current);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(m_np);
	}
}
