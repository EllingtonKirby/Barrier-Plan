package com.mlrinternational.barrierplan.ui.calculate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.BindViews;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mlrinternational.barrierplan.ui.landing.LandingPresenter.IMPERIAL_STRING;
import static com.mlrinternational.barrierplan.ui.landing.LandingPresenter.METRIC_STRING;

public class CalculateFragment extends BaseBarrierPlanFragment
    implements AddBarrierTypeDialogListener, CustomBarrierTypeDialogListener,
    MultipleBarrierCalcListener, SaveEventDialogListener {

  private static final String format = "%s %s";
  private final Map<String, Pair<BarrierItem, Integer>> multiCalcData = new HashMap<>();

  @BindView(R.id.single_barrier_container) CardView singleBarrierContainer;
  @BindView(R.id.multi_barrier_container) CardView multiBarrierContainer;
  @BindView(R.id.single_save) TextView singleSave;
  @BindView(R.id.multi_save) TextView multiSave;
  @BindView(R.id.btn_minit) View btnMinit;
  @BindView(R.id.btn_movit) View btnMovit;
  @BindView(R.id.btn_xtendit) View btnXtendit;
  @BindView(R.id.btn_add_barrier_type) View btnAddBarrierType;
  @BindView(R.id.single_barrier_result) View singleBarrierResult;
  @BindView(R.id.text_entry_length_needed) EditText singleCalcEditText;
  @BindView(R.id.unit) TextView unit;
  @BindView(R.id.list) RecyclerView list;
  @BindView(R.id.length_total) TextView totalLengthView;
  @BindView(R.id.barriers_total) TextView totalBarriersView;
  @BindViews({
                 R.id.btn_movit,
                 R.id.btn_minit,
                 R.id.btn_xtendit
             })
  List<TextView> buttonViews;

  private ButterKnife.Setter<TextView, Integer> setDrawable;
  private LayoutInflater inflater;
  private BarrierType currentSingleType = BarrierType.MOVIT;
  private AddBarrierTypeDialogUtil dialogUtil;
  private AlertDialog addBarrierTypeDialog;
  private AlertDialog customBarrierTypeDialog;
  private AlertDialog multiSaveDialog;
  private AlertDialog singleSaveDialog;
  private Subject<String> metricChanged;
  private Disposable singleCalcEditTextDisposable;
  private Disposable btnMinitDisposable;
  private Disposable btnMovitDisposable;
  private Disposable metricChangedDisposable;
  private Disposable btnAddBarrierTypeDisposable;
  private Metric currentMetric = Metric.IMPERIAL;
  private String metricString = "lft";
  private MultipleBarrierTypeAdapter adapter;
  private Disposable btnXtenditDisposable;

  public static CalculateFragment getInstance() {
    return new CalculateFragment();
  }

  @Override public int getLayoutId() {
    return R.layout.fragment_calculate;
  }

  @Override public void onCreate(@Nullable final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    inflater = LayoutInflater.from(getContext());
    dialogUtil = new AddBarrierTypeDialogUtil(getContext(), inflater);
    adapter = new MultipleBarrierTypeAdapter(getActivity(), this, listener);
    setDrawable = (view, value, index) -> view.setBackground(ContextCompat.getDrawable(
        getActivity(),
        value
    ));
  }

  @Override public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    list.setLayoutManager(new LinearLayoutManager(getContext()));
    list.setAdapter(adapter);
    list.setNestedScrollingEnabled(false);
  }

  @Override public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    metricChanged = listener.getMetricChangedObservable();
    addBarrierTypeDialog = dialogUtil.getAddBarrierTypeDialog(this);
    customBarrierTypeDialog =
        dialogUtil.getCustomBarrierTypeDialog(this, "feet");
    multiSaveDialog = dialogUtil.getSaveDialog(this, true);
    singleSaveDialog = dialogUtil.getSaveDialog(this, false);
  }

  @Override public void onStart() {
    super.onStart();
    listener.showMetricToolbar();
    observeViews();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    multiCalcData.clear();
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
    adapter.addItem(customBarrier);
  }

  @Override public void addMinit() {
    setUpMultiView(BarrierType.MINIT);
  }

  @Override public void addMovit() {
    setUpMultiView(BarrierType.MOVIT);
  }

  @Override public void addXtendit() {
    setUpMultiView(BarrierType.XTENDIT);
  }

  @Override public void saveMultiBarrierEvent(final String name, final Date date) {
    final List<Pair<BarrierItem, Integer>> items = new ArrayList<>();
    items.addAll(multiCalcData.values());
    listener.saveEvent(name, date, items);
  }

  @Override public void saveSingleBarrierEvent(final String name, final Date date) {
    final Integer length =
        Integer.parseInt(((TextView) singleBarrierResult.findViewById(R.id.result))
            .getText()
            .toString());
    listener.saveEvent(name, date, Arrays.asList(Pair.create(currentSingleType, length)));
  }

  @Override public void showAddCustomDialog() {
    customBarrierTypeDialog.show();
  }

  public void updateTotals(final BarrierItem item, final int numBarriers) {
    if (item != null) {
      if (multiCalcData.containsKey(item.getType())) {
        multiCalcData.remove(item.getType());
      }
      multiCalcData.put(item.getType(), Pair.create(item, numBarriers));
    }
    Double totalLength = 0d;
    int totalBarriers = 0;
    for (Pair<BarrierItem, Integer> pair : multiCalcData.values()) {
      if (currentMetric == Metric.IMPERIAL) {
        totalLength +=
            BarrierType.XTENDIT.getType().equals(pair.first.getType()) ?
            (pair.second / 2) * pair.first.getLengthImperial() :
            pair.second * pair.first.getLengthImperial();
      } else {
        totalLength +=
            BarrierType.XTENDIT.getType().equals(pair.first.getType()) ?
            (pair.second / 2) * pair.first.getLengthMetric() :
            pair.second * pair.first.getLengthMetric();
      }
      totalBarriers += pair.second;
    }
    totalLength = Math.ceil(UnitUtils.convertUp(totalLength, currentMetric));
    final String totalLengthString = String.format(format, totalLength, metricString);
    totalLengthView.setText(totalLengthString);
    totalBarriersView.setText(String.valueOf(totalBarriers));
  }

  private void changeSingleCalcBarrierType(final View viewSelected, final BarrierType selected) {
    currentSingleType = selected;
    ButterKnife.apply(buttonViews, setDrawable, R.color.colorWhite);
    viewSelected.setBackground(ResourcesCompat.getDrawable(
        getResources(),
        R.drawable.selected_logo,
        null
    ));
    displaySingleCalcResult();
  }

  private void displaySingleCalcResult() {
    if (!singleCalcEditText.getText().toString().isEmpty()) {
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

  private void observeViews() {
    singleCalcEditTextDisposable = RxTextView
        .textChanges(singleCalcEditText)
        .map(CharSequence::toString)
        .filter(s -> !s.isEmpty())
        .distinctUntilChanged()
        .subscribe(
            text -> {
              displaySingleCalcResult();
            },
            error -> Log.e("### ERROR", error.getMessage())
        );

    btnMinitDisposable = RxView.clicks(btnMinit)
        .filter(o -> currentSingleType != BarrierType.MINIT)
        .subscribe(
            o -> changeSingleCalcBarrierType(btnMinit, BarrierType.MINIT)
        );

    btnMovitDisposable = RxView.clicks(btnMovit)
        .filter(o -> currentSingleType != BarrierType.MOVIT)
        .subscribe(
            o -> changeSingleCalcBarrierType(btnMovit, BarrierType.MOVIT)
        );

    btnXtenditDisposable = RxView.clicks(btnXtendit)
        .filter(o -> currentSingleType != BarrierType.XTENDIT)
        .subscribe(
            o -> changeSingleCalcBarrierType(btnXtendit, BarrierType.XTENDIT)
        );

    btnAddBarrierTypeDisposable = RxView.clicks(btnAddBarrierType)
        .subscribe(o -> addBarrierTypeDialog.show());

    metricChangedDisposable = metricChanged.subscribe(this::onMetricChanged);

    RxView.clicks(singleSave)
        .subscribe(
            o -> singleSaveDialog.show()
        );

    RxView.clicks(multiSave)
        .subscribe(
            o -> multiSaveDialog.show()
        );
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
    updateTotals(null, 0);
  }

  private void setUpMultiView(final BarrierItem barrierItem) {
    if (multiCalcData.containsKey(barrierItem.getType())) {
      return;
    }
    multiCalcData.put(barrierItem.getType(), Pair.create(barrierItem, 0));
    adapter.addItem(barrierItem);
  }
}
