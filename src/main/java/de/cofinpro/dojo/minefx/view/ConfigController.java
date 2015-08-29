package de.cofinpro.dojo.minefx.view;

import de.cofinpro.dojo.minefx.model.ConfigFx;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

/**
 * Created by ABorger on 29.08.2015.
 */
public class ConfigController {



    @FXML
    private Slider columnsSlider;

    @FXML
    private Slider rowsSlider;

    @FXML
    private Slider poosSlider;


    @FXML
    private Label columnsSliderValueLbl;

    @FXML
    private Label rowsSliderValueLbl;

    @FXML
    private Label poosSliderValueLbl;

    @FXML
    private ToggleButton doBigBadPooTglBtn;

    private Stage configDialogStage;
    private ConfigFx configFx;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     */
    public void setConfigDialogStage(Stage configDialogStage) {
        this.configDialogStage = configDialogStage;
    }

    /**
     * Sets the configFx to be edited in the dialog.
     */
    public void setConfigFx(ConfigFx configFx) {
        this.configFx = configFx;

        rowsSlider.setValue(configFx.getRows());
        columnsSlider.setValue(configFx.getColumns());
        poosSlider.setValue(configFx.getPoos());
        rowsSliderValueLbl.setText(((Integer) configFx.getRows()).toString());
        rowsSlider.valueProperty().addListener(observable -> {
            Integer intValue = ((Double) rowsSlider.getValue()).intValue();
            rowsSliderValueLbl.setText(intValue.toString());
            calculatePoosSliderValue();
        });

        columnsSliderValueLbl.setText(((Integer) configFx.getColumns()).toString());
        columnsSlider.valueProperty().addListener(observable -> {
            Integer intValue = ((Double) columnsSlider.getValue()).intValue();
            columnsSliderValueLbl.setText(intValue.toString());
            calculatePoosSliderValue();
        });

        poosSliderValueLbl.setText(((Integer) configFx.getPoos()).toString());
        poosSlider.valueProperty().addListener(observable -> {
            Integer intValue = ((Double) poosSlider.getValue()).intValue();
            poosSliderValueLbl.setText(intValue.toString());
        });

        doBigBadPooTglBtn.setSelected(configFx.getDoBigBadPoo());
    }

    private void calculatePoosSliderValue(){
        poosSlider.setMax(100);
        poosSlider.setValue(100);

    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {

            configFx.setColumns(((Double) columnsSlider.getValue()).intValue());
            configFx.setRows(((Double) rowsSlider.getValue()).intValue());
            configFx.setPoos(((Double) poosSlider.getValue()).intValue());
            configFx.setDoBigBadPoo(doBigBadPooTglBtn.isSelected());


            okClicked = true;
            configDialogStage.close();
        }


    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        configDialogStage.close();
    }
}
