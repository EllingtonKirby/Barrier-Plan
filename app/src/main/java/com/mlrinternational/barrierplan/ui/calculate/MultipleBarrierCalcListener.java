package com.mlrinternational.barrierplan.ui.calculate;

import com.mlrinternational.barrierplan.data.BarrierItem;

interface MultipleBarrierCalcListener {
  void updateTotals(final BarrierItem barrierItem, final int numBarriers);
}
