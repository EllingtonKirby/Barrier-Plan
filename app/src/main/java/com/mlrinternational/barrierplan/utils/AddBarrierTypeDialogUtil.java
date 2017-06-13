package com.mlrinternational.barrierplan.utils;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.mlrinternational.barrierplan.R;
import com.mlrinternational.barrierplan.ui.calculate.AddBarrierTypeDialogListener;
import com.mlrinternational.barrierplan.ui.calculate.CustomBarrierTypeDialogListener;
import com.mlrinternational.barrierplan.ui.calculate.SaveEventDialogListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.mlrinternational.barrierplan.data.EventList.DD_MM_YYYY;

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
    final View btnXtendit = ButterKnife.findById(view, R.id.btn_xtendit);
    btnXtendit.setOnClickListener(
        v -> {
          listener.addXtendit();
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

  public AlertDialog getSaveDialog(
      final SaveEventDialogListener saveEventDialogListener,
      final boolean isMulti) {
    final DateFormat dateFormat = new SimpleDateFormat(DD_MM_YYYY, Locale.US);
    final View view = inflater.inflate(R.layout.dialog_save, null);
    final AlertDialog dialog = new AlertDialog.Builder(context).setView(view).create();
    final TextView selectedDate = ButterKnife.findById(view, R.id.selected_date);
    final Button datePicker = ButterKnife.findById(view, R.id.date_picker);
    final EditText name = ButterKnife.findById(view, R.id.event_name);
    final TextView save = ButterKnife.findById(view, R.id.save_button);
    save.setVisibility(View.GONE);
    final TextView cancel = ButterKnife.findById(view, R.id.cancel);

    RxTextView.textChanges(name)
        .filter(charSequence -> charSequence.length() > 0)
        .filter(charSequence -> selectedDate.getVisibility() != View.GONE)
        .distinctUntilChanged()
        .subscribe(
            charSequence -> {
              save.setVisibility(View.VISIBLE);
            }
        );

    RxView.clicks(cancel)
        .subscribe(o -> dialog.dismiss());

    datePicker.setOnClickListener(v -> {
          final Calendar c = Calendar.getInstance();
          @SuppressLint("WrongConstant") DatePickerDialog dpd = new DatePickerDialog(
              context,
              (view1, year, monthOfYear, dayOfMonth) ->
                  selectedDate.setText(String.format("%d-%d-%d", dayOfMonth, monthOfYear + 1, year)),
              c.get(Calendar.YEAR),
              c.get(Calendar.MONTH),
              c.get(Calendar.DATE)
          );
          dpd.show();
        }
    );

    RxView.clicks(save)
        .subscribe(
            o -> {
              if (name.getText().toString().isEmpty() || selectedDate
                  .getText()
                  .toString()
                  .isEmpty()) {
                return;
              } else {
                if (isMulti) {
                  saveEventDialogListener.saveMultiBarrierEvent(
                      name.getText().toString(),
                      dateFormat.parse(selectedDate.getText().toString())
                  );
                } else {
                  saveEventDialogListener.saveSingleBarrierEvent(
                      name.getText().toString(),
                      dateFormat.parse(selectedDate.getText().toString())
                  );
                }
                dialog.dismiss();
              }
            }

        );

    return dialog;
  }
}
