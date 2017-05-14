package com.mlrinternational.barrierplan.ui.calculate;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.mlrinternational.barrierplan.R;
import com.mlrinternational.barrierplan.data.BarrierItem;
import com.mlrinternational.barrierplan.data.BarrierType;
import com.mlrinternational.barrierplan.data.CustomBarrier;
import com.mlrinternational.barrierplan.data.Metric;
import com.mlrinternational.barrierplan.ui.base.BaseBarrierPlanFragment;
import com.mlrinternational.barrierplan.utils.AddBarrierTypeDialogUtil;
import com.mlrinternational.barrierplan.utils.UnitUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.Subject;
import java.util.HashMap;
import java.util.Map;

import static com.mlrinternational.barrierplan.ui.landing.LandingPresenter.IMPERIAL_STRING;
import static com.mlrinternational.barrierplan.ui.landing.LandingPresenter.METRIC_STRING;

public class CalculateFragment extends BaseBarrierPlanFragment
    implements AddBarrierTypeDialogListener, CustomBarrierTypeDialogListener {

  private static final String format = "%s %s";
  private final Map<String, Pair<BarrierItem, Integer>> multiCalcData = new HashMap<>();

  @BindView(R.id.single_barrier_container) CardView singleBarrierContainer;
  @BindView(R.id.multi_barrier_container) CardView multiBarrierContainer;
  @BindView(R.id.btn_minit) View btnMinit;
  @BindView(R.id.btn_movit) View btnMovit;
  @BindView(R.id.btn_add_barrier_type) View btnAddBarrierType;
  @BindView(R.id.single_barrier_result) View singleBarrierResult;
  @BindView(R.id.text_entry_length_needed) EditText singleCalcEditText;
  @BindView(R.id.unit) TextView unit;
  @BindView(R.id.list) LinearLayout list;
  @BindView(R.id.length_total) TextView totalLengthView;
  @BindView(R.id.barriers_total) TextView totalBarriersView;

  private LayoutInflater inflater;
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
  private Metric currentMetric = Metric.IMPERIAL;
  private String metricString = "feet";

  public static CalculateFragment getInstance() {
    return new CalculateFragment();
  }

  @Override public int getLayoutId() {
    return R.layout.fragment_calculate;
  }

  @Override public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    inflater = LayoutInflater.from(getContext());
    metricChanged = listener.getMetricChangedObservable();
    dialogUtil = new AddBarrierTypeDialogUtil(getContext(), inflater);
    addBarrierTypeDialog = dialogUtil.getAddBarrierTypeDialog(this);
    customBarrierTypeDialog =
        dialogUtil.getCustomBarrierTypeDialog(this, "feet");
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
    btnAddBarrierTypeDisposable.dispose();
    btnAddBarrierTypeDisposable = null;
    metricChangedDisposable = null;
    metricChanged = null;
    singleCalcEditTextDisposable = null;
    btnMinitDisposable = null;
    btnMovit = null;
  }

  @Override public void addCustomBarrierType(final String name, final String length) {
    if (multiCalcData.containsKey(name)) {
      return;
    }
    final CustomBarrier customBarrier = new CustomBarrier(
        name,
        length,
        listener.getMetricString().equals(METRIC_STRING) ? Metric.METRIC : Metric.IMPERIAL
    );
    multiCalcData.put(customBarrier.getType(), Pair.create(customBarrier, 0));
    final View customView = inflater.inflate(R.layout.item_mult_barrier_calculation, null);
    final TextInputEditText editText = ButterKnife.findById(customView, R.id.entry_length);
    final TextView textType = ButterKnife.findById(customView, R.id.title);
    textType.setText(customBarrier.getType());
    final TextView barrierTotal = ButterKnife.findById(customView, R.id.barrier_total);
    list.addView(customView);
    RxTextView
        .textChanges(editText)
        .filter(charSequence -> charSequence.length() > 0)
        .subscribe(
            charSequence -> {
              final Double value = Double.valueOf(charSequence.toString());
              final String barrier = value == 1 ? "Barrier" : "Barriers";
              final int numBarriers = listener.getCalculation(value, customBarrier).first;
              final String result = String.format(
                  format,
                  numBarriers,
                  barrier
              );
              if (multiCalcData.containsKey(customBarrier.getType())) {
                multiCalcData.remove(customBarrier.getType());
              }
              multiCalcData.put(customBarrier.getType(), Pair.create(customBarrier, numBarriers));
              barrierTotal.setText(result);
              updateTotals();
            }
        );
  }

  @Override public void addMinit() {
    setUpMultiView(R.drawable.minit_logo, BarrierType.MINIT);
  }

  @Override public void addMovit() {
    setUpMultiView(R.drawable.movit_logo, BarrierType.MOVIT);
  }

  @Override public void showAddCustomDialog() {
    customBarrierTypeDialog.show();
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
    metricString = getString(
        IMPERIAL_STRING.equals(metric) ? R.string.feet : R.string.meters
    );
    currentMetric = IMPERIAL_STRING.equals(metric) ? Metric.IMPERIAL : Metric.METRIC;
    unit.setText(metricString);
    customBarrierTypeDialog = null;
    customBarrierTypeDialog =
        dialogUtil.getCustomBarrierTypeDialog(this, metricString);
    updateTotals();
  }

  private void setUpMultiView(final @DrawableRes int logo, final BarrierItem barrierItem) {
    if (multiCalcData.containsKey(barrierItem.getType())) {
      return;
    }
    multiCalcData.put(barrierItem.getType(), Pair.create(barrierItem, 0));
    final View minitView = inflater.inflate(R.layout.item_mult_barrier_calculation, null);
    final TextInputEditText editText = ButterKnife.findById(minitView, R.id.entry_length);
    final ImageView imgType = ButterKnife.findById(minitView, R.id.img_type);
    final TextView textType = ButterKnife.findById(minitView, R.id.title);
    textType.setVisibility(View.GONE);
    imgType.setVisibility(View.VISIBLE);
    imgType.setImageDrawable(getResources().getDrawable(logo));
    final TextView barrierTotal = ButterKnife.findById(minitView, R.id.barrier_total);
    list.addView(minitView);
    RxTextView
        .textChanges(editText)
        .filter(charSequence -> charSequence.length() > 0)
        .subscribe(
            charSequence -> {
              final Double value = Double.valueOf(charSequence.toString());
              final String barrier = value == 1 ? "Barrier" : "Barriers";
              final int numBarriers = listener.getCalculation(value, barrierItem).first;
              final String result = String.format(
                  format,
                  numBarriers,
                  barrier
              );
              if (multiCalcData.containsKey(barrierItem.getType())) {
                multiCalcData.remove(barrierItem.getType());
              }
              multiCalcData.put(barrierItem.getType(), Pair.create(barrierItem, numBarriers));
              barrierTotal.setText(result);
              updateTotals();
            }
        );
  }

  private void updateTotals() {
    Double totalLength = 0d;
    int totalBarriers = 0;
    for (Pair<BarrierItem, Integer> pair : multiCalcData.values()) {
      if (currentMetric == Metric.IMPERIAL) {
        totalLength += pair.second * pair.first.getLengthImperial();
      } else {
        totalLength += pair.second * pair.first.getLengthMetric();
      }
      totalBarriers += pair.second;
    }
    totalLength = UnitUtils.convertUp(totalLength, currentMetric);
    final String totalLengthString = String.format(format, totalLength, metricString);
    totalLengthView.setText(totalLengthString);
    totalBarriersView.setText(String.valueOf(totalBarriers));
  }
}
