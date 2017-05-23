package com.mlrinternational.barrierplan.ui.calculate;

import java.util.Date;

public interface SaveEventDialogListener {

  void saveSingleBarrierEvent(final String name, final Date date);

  void saveMultiBarrierEvent(final String name, final Date date);

}
