package com.skylightapp.Classes;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.DialogFragment;

import com.skylightapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;



public class EditSavingsDialog extends DialogFragment {
    public static final String TAG = EditSavingsDialog.class.getSimpleName();
    public static final String COMMENT_TEXT_KEY = "EditCommentDialog.COMMENT_TEXT_KEY";
    public static final String COMMENT_ID_KEY = "EditCommentDialog.COMMENT_ID_KEY";

    private SavingsDialogCallback callback;
    private String commentText;
    private String savingsId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getActivity() instanceof SavingsDialogCallback) {
            callback = (SavingsDialogCallback) getActivity();
        } else {
            throw new RuntimeException(getActivity().getTitle() + " should implements CommentDialogCallback");
        }

        commentText = (String) getArguments().get(COMMENT_TEXT_KEY);
        savingsId = (String) getArguments().get(COMMENT_ID_KEY);

        super.onCreate(savedInstanceState);
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = (LayoutInflater) requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.dialog_update_savings, null);

        final AppCompatEditText editSavingsEditText =  view.findViewById(R.id.edit_savings);

        if (commentText != null) {
            editSavingsEditText.setText(commentText);
        }

        configureDialogButtonState(editSavingsEditText);


        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setView(view)
                .setTitle(R.string.title_edit_savings)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newSavingsNoOfDays = Objects.requireNonNull(editSavingsEditText.getText()).toString();

                        if (!newSavingsNoOfDays.equals(commentText) && callback != null) {
                            callback.onSavingsChanged(newSavingsNoOfDays, savingsId);
                        }

                        dialog.cancel();
                    }
                });

        return builder.create();
    }

    private void configureDialogButtonState(AppCompatEditText editCommentEditText) {
        editCommentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Dialog dialog = getDialog();
                if (dialog != null) {
                    if (TextUtils.isEmpty(s)) {
                        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                    } else {
                        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    }
                }
            }
        });
    }

    public interface SavingsDialogCallback {
        void onSavingsChanged(String newText, String commentId);
    }
}
