package com.kapirawan.financial_tracker.ui.category;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.kapirawan.financial_tracker.R;
import com.kapirawan.financial_tracker.activities.ActivityMainViewModel;
import com.kapirawan.financial_tracker.roomdatabase.account.Account;
import com.kapirawan.financial_tracker.roomdatabase.category.Category;

import java.util.Date;
import java.util.List;

public class AddCategoryDialog extends DialogFragment {

    AddCategoryDialogViewModel viewModel;
    List<Category> categories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Create the ViewModel
        View view = inflater.inflate(R.layout.category_dialog_add_category, container, false);
        long userId = ViewModelProviders.of(this.getActivity()).get(ActivityMainViewModel.class).getUser()._id;
        long datasourceId = ViewModelProviders.of(this.getActivity()).get(ActivityMainViewModel.class).getDatasource()._id;
        viewModel = ViewModelProviders.of(this.getActivity()).get(AddCategoryDialogViewModel.class);
        viewModel.initUserId(userId);
        viewModel.initDatasourceId(datasourceId);
        viewModel.getSelectedAccount().observe(this, selectedAccount -> {
            String[] parsedValues = selectedAccount.value.split(",");
            long accountID = Long.parseLong(parsedValues[0]);
            long accounDatasourceId = Long.parseLong(parsedValues[1]);
            viewModel.initAccount(accountID, accounDatasourceId);
            viewModel.getCategories().observe(this, cats -> categories = cats);
            onCreateViewInitName(view);
            onCreateViewInitAddButton(view);
        });
        view.findViewById(R.id.button_cancel).setOnClickListener(v -> this.getDialog().cancel());
        return view;
    }

    private void onCreateViewInitName(View view){
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0){
                    view.findViewById(R.id.button_update).setEnabled(true);
                }else
                    view.findViewById(R.id.button_update).setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        ((EditText)view.findViewById(R.id.edittext_categoryname))
                .addTextChangedListener(textWatcher);
    }

    private void onCreateViewInitAddButton(View view) {
        Button button = view.findViewById(R.id.button_update);
        button.setOnClickListener(v -> {
            String name = ((EditText)
                    view.findViewById(R.id.edittext_categoryname)).getText().toString().toUpperCase();
            boolean isNameInUse = false;
            for(Category cats: categories){
                if (cats.name.toUpperCase().equals(name))
                    isNameInUse = true;
            }
            if (isNameInUse)
                showError("Category Name is already in use");
            else{
                viewModel.addCategory(toTitleCase(name));
                getDialog().dismiss();
            }
        });
        button.setEnabled(false);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    private void showError(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this.getActivity()).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> dialog.dismiss()
        );
        alertDialog.show();
    }

    private String toTitleCase(String s) {
        //Characters that follow these delimeters will be capitalized
        final String ACTIONABLE_DELIMITERS = " '-/";

        StringBuilder sb = new StringBuilder();
        boolean capNext = true;

        for (char c : s.toCharArray()) {
            c = (capNext)
                    ? Character.toUpperCase(c)
                    : Character.toLowerCase(c);
            sb.append(c);
            capNext = (ACTIONABLE_DELIMITERS.indexOf((int) c) >= 0);
        }
        return sb.toString();
    }
}
