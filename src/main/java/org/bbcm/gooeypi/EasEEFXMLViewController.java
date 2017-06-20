package org.bbcm.gooeypi;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Control;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;

public class EasEEFXMLViewController {
	@FXML
	private ChoiceBox<String> pin2;
	@FXML
	private ChoiceBox<String> pin3;
	@FXML
	private ChoiceBox<String> pin4;
	@FXML
	private ChoiceBox<String> pin5;
	@FXML
	private ChoiceBox<String> pin6;
	@FXML
	private ChoiceBox<String> pin7;
	@FXML
	private ChoiceBox<String> pin8;
	@FXML
	private ChoiceBox<String> pin9;
	@FXML
	private ChoiceBox<String> pin10;
	@FXML
	private ChoiceBox<String> pin11;
	@FXML
	private ChoiceBox<String> pin12;
	@FXML
	private ChoiceBox<String> pin13;
	@FXML
	private ChoiceBox<String> pin14;
	@FXML
	private ChoiceBox<String> pin15;
	@FXML
	private ChoiceBox<String> pin16;
	@FXML
	private ChoiceBox<String> pin17;
	@FXML
	private ChoiceBox<String> pin18;
	@FXML
	private ChoiceBox<String> pin19;
	@FXML
	private ChoiceBox<String> pin20;
	@FXML
	private ChoiceBox<String> pin21;
	@FXML
	private ChoiceBox<String> pin22;
	@FXML
	private ChoiceBox<String> pin23;
	@FXML
	private ChoiceBox<String> pin24;
	@FXML
	private ChoiceBox<String> pin25;
	@FXML
	private ChoiceBox<String> pin26;
	@FXML
	private ChoiceBox<String> pin27;
	@FXML
	private AnchorPane output_panel;
	@FXML
	private AnchorPane audio_panel;
	@FXML
	private Button add_output;
	@FXML
	private Menu file_menu;

	List<ChoiceBox<String>> pins = new ArrayList<ChoiceBox<String>>();
	Map<String, List<Integer>> pinMapping = new HashMap<String, List<Integer>>();
	Map<Integer, List<Control>> outputMap = new HashMap<Integer, List<Control>>();

	@FXML
	protected void initialize() {
		pins.add(pin2);
		pins.add(pin3);
		pins.add(pin4);
		pins.add(pin5);
		pins.add(pin6);
		pins.add(pin7);
		pins.add(pin8);
		pins.add(pin9);
		pins.add(pin10);
		pins.add(pin11);
		pins.add(pin12);
		pins.add(pin13);
		pins.add(pin14);
		pins.add(pin15);
		pins.add(pin16);
		pins.add(pin17);
		pins.add(pin18);
		pins.add(pin19);
		pins.add(pin20);
		pins.add(pin21);
		pins.add(pin22);
		pins.add(pin23);
		pins.add(pin24);
		pins.add(pin25);
		pins.add(pin26);
		pins.add(pin27);

		pinMapping.put("Input Pullup", new ArrayList<Integer>());
		pinMapping.put("Input Pulldown", new ArrayList<Integer>());
		pinMapping.put("Output High", new ArrayList<Integer>());
		pinMapping.put("Output Low", new ArrayList<Integer>());

		int counter = 2;
		for (ChoiceBox<String> pin : pins) {
			// pin.getItems().add("Pin# " + counter);
			pin.getItems().add("Input Pullup");
			pin.getItems().add("Input Pulldown");
			pin.getItems().add("Output High");
			pin.getItems().add("Output Low");
			final int pinNum = counter;
			pin.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> selected, String oldVal, String newVal) {
					if (oldVal != null) {
						pinMapping.get(oldVal).remove((Object) pinNum);
					}
					pinMapping.get(newVal).add(pinNum);
					if (oldVal != null) {
						if (oldVal.contains("Input") && !newVal.contains("Input")) {
							for (List<Control> outMap : outputMap.values()) {
								((ChoiceBox<Control>) outMap.get(1)).getItems().remove(((Object) pinNum));
								if (newVal.contains("Output")) {
									((ChoiceBox<Integer>) outMap.get(0)).getItems().add(pinNum);
								}
							}
						}
						if (oldVal.contains("Output") && !newVal.contains("Output")) {
							for (List<Control> outMap : outputMap.values()) {
								((ChoiceBox<?>) outMap.get(0)).getItems().remove(((Object) pinNum));
								if (newVal.contains("Input")) {
									((ChoiceBox<Integer>) outMap.get(1)).getItems().add(pinNum);
								}
							}
						}
					} else {
						if (newVal.contains("Output")) {
							for (List<Control> outMap : outputMap.values()) {
								((ChoiceBox<Integer>) outMap.get(0)).getItems().add(pinNum);
							}
						}
						if (newVal.contains("Input")) {
							for (List<Control> outMap : outputMap.values()) {
								((ChoiceBox<Integer>) outMap.get(1)).getItems().add(pinNum);
							}
						}
					}
				}
			});
			counter++;
		}

		add_output.setOnMouseClicked(event -> {
			ChoiceBox<Integer> outputPins = new ChoiceBox<Integer>();
			outputPins.getItems().addAll(pinMapping.get("Output High"));
			outputPins.getItems().addAll(pinMapping.get("Output Low"));
			outputPins.setPrefWidth(150);
			ChoiceBox<Integer> inputPins = new ChoiceBox<Integer>();
			inputPins.getItems().addAll(pinMapping.get("Input Pullup"));
			inputPins.getItems().addAll(pinMapping.get("Input Pulldown"));
			inputPins.setPrefWidth(150);
			TextField duration = new TextField();
			duration.setText("0");
			ToggleButton toggle = new ToggleButton("Set Toggle");
			toggle.getStylesheets().add("/assets/bbcm/gooeypi/layout/toggle_button.css");
			output_panel.getChildren().add(outputPins);
			AnchorPane.setLeftAnchor(outputPins, 14D);
			AnchorPane.setTopAnchor(outputPins, (double) (127 + (outputMap.size() * 30)));
			output_panel.getChildren().add(inputPins);
			AnchorPane.setLeftAnchor(inputPins, 180D);
			AnchorPane.setTopAnchor(inputPins, (double) (127 + (outputMap.size() * 30)));
			output_panel.getChildren().add(duration);
			AnchorPane.setLeftAnchor(duration, 345D);
			AnchorPane.setTopAnchor(duration, (double) (127 + (outputMap.size() * 30)));
			output_panel.getChildren().add(toggle);
			AnchorPane.setLeftAnchor(toggle, 508D);
			AnchorPane.setTopAnchor(toggle, (double) (127 + (outputMap.size() * 30)));
			outputMap.put(outputMap.size(),
					new ArrayList<Control>(Arrays.asList(outputPins, inputPins, duration, toggle)));
		});

		MenuItem export = new MenuItem("Export XML");
		export.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				try {

					DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

					// root elements
					Document doc = docBuilder.newDocument();
					Element rootElement = doc.createElement("settings");
					doc.appendChild(rootElement);

					{// IO Pins
						Element io_pins = doc.createElement("setup_IO_pins");
						rootElement.appendChild(io_pins);
						{// input
							Element inpins = doc.createElement("input");

							for (Integer pin : pinMapping.get("Input Pullup")) {
								Element inPin = doc.createElement("pin");
								inPin.setAttribute("type", "PULLUP");
								inPin.appendChild(doc.createTextNode("" + pin));
								inpins.appendChild(inPin);
							}
							for (Integer pin : pinMapping.get("Input Pulldown")) {
								Element inPin = doc.createElement("pin");
								inPin.setAttribute("type", "PULLDOWN");
								inPin.appendChild(doc.createTextNode("" + pin));
								inpins.appendChild(inPin);
							}
							io_pins.appendChild(inpins);
						}
						{// output
							Element outpins = doc.createElement("output");

							for (Integer pin : pinMapping.get("Output High")) {
								Element outPin = doc.createElement("pin");
								outPin.setAttribute("state", "HIGH");
								outPin.appendChild(doc.createTextNode("" + pin));
								outpins.appendChild(outPin);
							}
							for (Integer pin : pinMapping.get("Output Low")) {
								Element outPin = doc.createElement("pin");
								outPin.setAttribute("state", "LOW");
								outPin.appendChild(doc.createTextNode("" + pin));
								outpins.appendChild(outPin);
							}
							io_pins.appendChild(outpins);
						}

					}
					{// Control
						Element control = doc.createElement("Control");
						rootElement.appendChild(control);
						for (List<Control> elem : outputMap.values()) {
							if (((ChoiceBox<Integer>) elem.get(1)).getValue() != null
									&& ((ChoiceBox<Integer>) elem.get(0)).getValue() != null) {
								Element output = doc.createElement("Output");
								output.setAttribute("trigger", "" + ((ChoiceBox<Integer>) elem.get(1)).getValue());
								Element pin = doc.createElement("pin");
								pin.appendChild(doc.createTextNode("" + ((ChoiceBox<Integer>) elem.get(0)).getValue()));
								output.appendChild(pin);
								Element dur = doc.createElement("duration");
								String duration = ((TextField) elem.get(2)).getText();
								if (duration == null || ((ToggleButton) elem.get(3)).isSelected()) {
									duration = "0";
								}
								dur.appendChild(doc.createTextNode(duration));
								output.appendChild(dur);
								control.appendChild(output);
							}
						}

					}

					// write the content into xml file
					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					DOMSource source = new DOMSource(doc);
					StreamResult result = new StreamResult(new File("settings.xml"));

					transformer.transform(source, result);

				} catch (ParserConfigurationException pce) {
					pce.printStackTrace();
				} catch (TransformerException tfe) {
					tfe.printStackTrace();
				}

			}
		});
		file_menu.getItems().add(export);

	}
}
