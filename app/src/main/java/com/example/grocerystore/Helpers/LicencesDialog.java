package com.example.grocerystore.Helpers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.grocerystore.R;
import com.example.grocerystore.Utils;

public class LicencesDialog extends DialogFragment {

	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
		View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_licences, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
				.setView(view);

		TextView licencesTetView = view.findViewById(R.id.licences_text_view);
		Button dismissButton = view.findViewById(R.id.dismiss_button);

		licencesTetView.setText(Utils.getLicences());
		dismissButton.setOnClickListener(v -> dismiss());

		return builder.create();
	}
}
