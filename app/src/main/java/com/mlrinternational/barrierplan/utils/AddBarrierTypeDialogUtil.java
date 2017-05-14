package com.mlrinternational.barrierplan.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.mlrinternational.barrierplan.R;
import com.mlrinternational.barrierplan.ui.calculate.AddBarrierTypeDialogListener;
import com.mlrinternational.barrierplan.ui.calculate.CustomBarrierTypeDialogListener;

public class AddBarrierTypeDialogUtil {

  private final Context context;
  private final LayoutInflater inflater;

  public AddBarrierTypeDialogUtil(final Context context, final LayoutInflater inflater) {
    this.context = context;
    this.inflater = inflater;
  }

  public AlertDialog getAddBarrierTypeDialog(final AddBarrierTypeDialogListener listener) {
    final View view = inflater.inflate(R.layout.dialog_add_barrier_type, null);
    final AlertDialog dialog = new AlertDialog.Builder(context).setView(view).create();
    final View btnMovit = ButterKnife.findById(view, R.id.btn_movit);
    btnMovit.setOnClickListener(
        v -> {
          listener.addMovit();
          dialog.dismiss();
        });
    final View btnMinit = ButterKnife.findById(view, R.id.btn_minit);
    btnMinit.setOnClickListener(
        v -> {
          listener.addMinit();
          dialog.dismiss();
        }
    );
    final View btnCustom = ButterKnife.findById(view, R.id.btn_custom);
    btnCustom.setOnClickListener(
        v -> {
          listener.showAddCustomDialog();
          dialog.dismiss();
        }
    );
    return dialog;
  }

  public AlertDialog getCustomBarrierTypeDialog(
      final CustomBarrierTypeDialogListener listener,
      final String metric) {
    final View view = inflater.inflate(R.layout.dialog_custom_barrier_type, null);
    final AlertDialog dialog = new AlertDialog.Builder(context).setView(view).create();
    final EditText name = ButterKnife.findById(view, R.id.entry_name);
    final EditText length = ButterKnife.findById(view, R.id.entry_length);
    final TextView unit = ButterKnife.findById(view, R.id.unit);
    unit.setText(metric);
    final View add = ButterKnife.findById(view, R.id.btn_add);
    add.setOnClickListener(
        v -> {
          listener.addCustomBarrierType(
              name.getText().toString(),
              length.getText().toString()
          );
          dialog.dismiss();
        }
    );
    final View cancel = ButterKnife.findById(view, R.id.btn_cancel);
    cancel.setOnClickListener(v -> dialog.dismiss());
    return dialog;
  }
}
