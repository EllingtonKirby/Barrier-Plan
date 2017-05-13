package com.mlrinternational.barrierplan.ui.calculate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import com.jakewharton.rxbinding2.view.RxView;
import com.mlrinternational.barrierplan.R;
import com.mlrinternational.barrierplan.data.BarrierType;
import com.mlrinternational.barrierplan.ui.base.BaseBarrierPlanFragment;
import com.mlrinternational.barrierplan.utils.AddBarrierTypeDialogUtil;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.Subject;

import static com.mlrinternational.barrierplan.ui.landing.LandingPresenter.IMPERIAL_STRING;

public class CalculateFragment extends BaseBarrierPlanFragment
    implements AddBarrierTypeDialogListener, CustomBarrierTypeDialogListener {

  @BindView(R.id.single_barrier_container) CardView singleBarrierContainer;
  @BindView(R.id.multi_barrier_container) CardView multiBarrierContainer;
  @BindView(R.id.btn_minit) View btnMinit;
  @BindView(R.id.btn_movit) View btnMovit;
  @BindView(R.id.btn_add_barrier_type) View btnAddBarrierType;
  @BindView(R.id.single_barrier_result) View singleBarrierResult;
  @BindView(R.id.multi_barrier_result) View multiBarrierResult;
  @BindView(R.id.text_entry_length_needed) EditText singleCalcEditText;
  @BindView(R.id.unit) TextView unit;

  private BarrierType currentSingleType = BarrierType.MOVIT;
  private AddBarrierTypeDialogUtil dialogUtil;
  private AlertDialog addBarrierTypeDialog;
  private AlertDialog customBarrierTypeDialog;
  private Subject<String> metricChanged;
  private Disposable singleCalcEditTextDisposable;
  private Disposable btnMinitDisposable;
  private Disposable btnMovitDisposable;
  private Disposable metricChangedDisposable;
  private Disposable btnAddBarrierTypeDisposable;

  public static CalculateFragment getInstance() {
    return new CalculateFragment();
  }

  @Override public int getLayoutId() {
    return R.layout.fragment_calculate;
  }

  @Override public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    metricChanged = listener.getMetricChangedObservable();
    dialogUtil = new AddBarrierTypeDialogUtil(getContext(), LayoutInflater.from(getActivity()));
    addBarrierTypeDialog = dialogUtil.getAddBarrierTypeDialog(this);
    customBarrierTypeDialog = dialogUtil.getCustomBarrierTypeDialog(this);
  }

  @Override public void onResume() {
    super.onResume();
    observeViews();
  }

  @Override public void onDestroy() {
    super.onDestroy();
    singleCalcEditTextDisposable.dispose();
    btnMinitDisposable.dispose();
    btnMovitDisposable.dispose();
    metricChangedDisposable.dispose();
    metricChangedDisposable = null;
    metricChanged = null;
    singleCalcEditTextDisposable = null;
    btnMinitDisposable = null;
    btnMovit = null;
  }

  @Override public void showAddCustomDialog() {
    customBarrierTypeDialog.show();
  }

  @Override public void addMinit() {

  }

  @Override public void addMovit() {

  }

  @Override public void addCustomBarrierType(final String name, final String length) {

  }

  private void changeSingleCalcBarrierType() {
    final View viewToChange;
    final View prevView;
    if (currentSingleType == BarrierType.MOVIT) {
      viewToChange = btnMinit;
      prevView = btnMovit;
      currentSingleType = BarrierType.MINIT;
    } else {
      viewToChange = btnMovit;
      prevView = btnMinit;
      currentSingleType = BarrierType.MOVIT;
    }
    viewToChange.setBackground(ResourcesCompat.getDrawable(getResources(), R.color.gray, null));
    prevView.setBackground(ResourcesCompat.getDrawable(getResources(), R.color.colorWhite, null));
  }

  private void displaySingleCalcResult() {
    final TextView resultText = (TextView) singleBarrierResult.findViewById(R.id.result);
    final TextView remainderText = (TextView) singleBarrierResult.findViewById(R.id.remainder);
    final Pair<Integer, Double> resultAmount = listener.getCalculation(
        Double.valueOf(singleCalcEditText.getText().toString()),
        currentSingleType
    );
    resultText.setText(String.valueOf(resultAmount.first));
    final String remainder = String.valueOf(resultAmount.second) + listener.getMetricString();
    remainderText.setText(remainder);
  }

  private void observeViews() {
    singleCalcEditTextDisposable = RxView.keys(singleCalcEditText)
        .subscribe(
            keyEvent -> {
              if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                  && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                displaySingleCalcResult();
              }
            }
        );

    btnMinitDisposable = RxView.clicks(btnMinit)
        .filter(o -> currentSingleType != BarrierType.MINIT)
        .subscribe(
            o -> changeSingleCalcBarrierType()
        );

    btnMovitDisposable = RxView.clicks(btnMovit)
        .filter(o -> currentSingleType != BarrierType.MOVIT)
        .subscribe(
            o -> changeSingleCalcBarrierType()
        );

    btnAddBarrierTypeDisposable = RxView.clicks(btnAddBarrierType)
        .subscribe(o -> addBarrierTypeDialog.show());

    metricChangedDisposable = metricChanged.subscribe(this::onMetricChanged);
  }

  private void onMetricChanged(final String metric) {
    final String unitString = getString(
        IMPERIAL_STRING.equals(metric) ? R.string.feet : R.string.meters
    );
    unit.setText(unitString);
  }
}
