package com.kumela.cmeter.ui.common.util;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.kumela.cmeter.R;

/**
 * Created by Toko on 13,July,2020
 **/

public class AlertDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialogFragmentArgs args = AlertDialogFragmentArgs.fromBundle(requireArguments());

        String title = getResources().getString(args.getTitle());
        String message = getResources().getString(args.getMessage());

        return new MaterialAlertDialogBuilder(requireActivity())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(R.string.ok, (dialog, which) -> dialog.dismiss())
                .create();
    }
}
