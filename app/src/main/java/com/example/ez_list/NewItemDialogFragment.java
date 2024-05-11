package com.example.ez_list;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class NewItemDialogFragment extends DialogFragment {
    public interface OnDialogTextEntered {
        void onFirstItemTextEntered(String text);
        void onSecondItemTextEntered(String text);
        void onDeleteItemTextEntered(String text);
    }
    private OnDialogTextEntered mListener;

    private String tag;
    private String hintString;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (OnDialogTextEntered) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction.
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater.
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_new_item, null);
            String tag = getTag();
            if ("firstDialog".equals(tag)) {
                hintString = "Enter a grocery item";
            } else if ("secondDialog".equals(tag)) {
                hintString = "Enter the item's aisle number";
            } else if ("deleteDialog".equals(tag)) {
                hintString = "Enter the item you want to delete";
            }
        EditText input = view.findViewById(R.id.dialogEditText);
        input.setHint(hintString);
        // Inflate and set the layout for the dialog.
        // Pass null as the parent view because it's going in the dialog layout.
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog dlg = (Dialog) dialog;
                        EditText nameEdit =  dlg.findViewById(R.id.dialogEditText);
                        if(tag.equals("firstDialog")){
                            mListener.onFirstItemTextEntered(nameEdit.getText().toString());
                        } else if (tag.equals("secondDialog")) {
                            mListener.onSecondItemTextEntered(nameEdit.getText().toString());
                        } else if (tag.equals("deleteDialog")) {
                            mListener.onDeleteItemTextEntered(nameEdit.getText().toString());
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });
        return builder.create();
    }
}
