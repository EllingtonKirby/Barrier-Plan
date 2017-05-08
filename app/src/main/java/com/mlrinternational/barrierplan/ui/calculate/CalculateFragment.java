package com.mlrinternational.barrierplan.ui.calculate;

import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import com.jakewharton.rxbinding2.view.RxView;
import com.mlrinternational.barrierplan.R;
import com.mlrinternational.barrierplan.data.BarrierType;
import com.mlrinternational.barrierplan.ui.base.BaseBarrierPlanFragment;

public class CalculateFragment extends BaseBarrierPlanFragment {

  @BindView(R.id.single_barrier_container) CardView singleBarrierContainer;
  @BindView(R.id.multi_barrier_container) CardView multiBarrierContainer;
  @BindView(R.id.btn_minit) View btnMinit;
  @BindView(R.id.btn_movit) View btnMovit;
  @BindView(R.id.btn_add_barrier_type) View btnAddBarrierType;
  @BindView(R.id.single_barrier_result) View singleBarrierResult;
  @BindView(R.id.multi_barrier_result) View multiBarrierResult;
  @BindView(R.id.text_entry_length_needed) EditText singleCalcEditText;

  private BarrierType currentSingleType = BarrierType.MOVIT;

  public static CalculateFragment getInstance() {
    return new CalculateFragment();
  }

  @Override public int getLayoutId() {
    return R.layout.fragment_calculate;
  }

  @Override public void onResume() {
    super.onResume();
    observeViews();
  }

  private void observeViews() {
    RxView.keys(singleCalcEditText)
        .subscribe(
            keyEvent -> {
              if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                  && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                displaySingleCalcResult();
              }
            }
        );

    RxView.clicks(btnMinit)
        .filter(o -> currentSingleType != BarrierType.MINIT)
        .subscribe(
            o -> changeSingleCalcBarrierType()
        );

    RxView.clicks(btnMovit)
        .filter(o -> currentSingleType != BarrierType.MOVIT)
        .subscribe(
            o -> changeSingleCalcBarrierType()
        );
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
}
