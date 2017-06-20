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

public class PiDarFXMLViewController {
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
	private Button add_audio;
	@FXML
	private Menu file_menu;

	List<ChoiceBox<String>> pins = new ArrayList<ChoiceBox<String>>();
	Map<String, List<Integer>> pinMapping = new HashMap<String, List<Integer>>();
	Map<Integer, List<Control>> outputMap = new HashMap<Integer, List<Control>>();
	Map<Integer, List<Control>> audioMap = new HashMap<Integer, List<Control>>();

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
							for (List<Control> audMap : audioMap.values()) {
								((ChoiceBox<Integer>) audMap.get(0)).getItems().remove(((Object) pinNum));
								((ChoiceBox<Integer>) audMap.get(1)).getItems().remove(((Object) pinNum));
							}
						}
						if (oldVal.contains("Output") && !newVal.contains("Output")) {
							for (List<Control> outMap : outputMap.values()) {
								((ChoiceBox<?>) outMap.get(0)).getItems().remove(((Object) pinNum));
								if (newVal.contains("Input")) {
									((ChoiceBox<Integer>) outMap.get(1)).getItems().add(pinNum);
								}
							}
							if (newVal.contains("Input"))
								for (List<Control> audMap : audioMap.values()) {
									((ChoiceBox<Integer>) audMap.get(0)).getItems().add(pinNum);
									((ChoiceBox<Integer>) audMap.get(1)).getItems().add((pinNum));
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
							for (List<Control> audMap : audioMap.values()) {
								((ChoiceBox<Integer>) audMap.get(0)).getItems().add((pinNum));
								((ChoiceBox<Integer>) audMap.get(1)).getItems().add((pinNum));
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

		add_audio.setOnMouseClicked(event -> {
			ChoiceBox<Integer> inputPins = new ChoiceBox<Integer>();
			inputPins.getItems().addAll(pinMapping.get("Input Pullup"));
			inputPins.getItems().addAll(pinMapping.get("Input Pulldown"));
			inputPins.setPrefWidth(150);

			ChoiceBox<Integer> stopPins = new ChoiceBox<Integer>();
			stopPins.getItems().addAll(pinMapping.get("Input Pullup"));
			stopPins.getItems().addAll(pinMapping.get("Input Pulldown"));
			stopPins.setPrefWidth(150);

			ChoiceBox<String> playlistOption = new ChoiceBox<String>();
			playlistOption.getItems().add("Play a Song");
			playlistOption.getItems().add("Use Playlist");
			playlistOption.setPrefWidth(150);

			TextField files = new TextField();
			Button pickFile = new Button("Pick File(s)");
			CheckBox loops = new CheckBox("Loop");

			playlistOption.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> selected, String oldVal, String newVal) {
					if (newVal != null && !newVal.isEmpty()) {
						if (newVal.contains("Song")) {
							loops.setText("Loop");
						} else {
							loops.setText("Repeat");
						}
					}
				}
			});

			pickFile.setOnMouseClicked(fileEvent -> {
				String item = playlistOption.getSelectionModel().getSelectedItem();
				if (item != null) {
					FileChooser fileChooser = new FileChooser();
					fileChooser.setTitle("Open Audio File");
					fileChooser.getExtensionFilters()
							.add(new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.ogg"));
					if (item.equals("Play a Song")) {
						File song = fileChooser.showOpenDialog(PiDar.pStage);
						if (song != null) {
							files.setText(song.getName());
						}
					} else {
						List<File> playlist = fileChooser.showOpenMultipleDialog(PiDar.pStage);
						String songs = "";
						for (File song : playlist) {
							songs += song.getName() + ";";
						}
						files.setText(songs);
					}
				}
			});
			audio_panel.getChildren().add(inputPins);
			AnchorPane.setLeftAnchor(inputPins, 14D);
			AnchorPane.setTopAnchor(inputPins, (double) (127 + (audioMap.size() * 30)));
			audio_panel.getChildren().add(playlistOption);
			AnchorPane.setLeftAnchor(playlistOption, 180D);
			AnchorPane.setTopAnchor(playlistOption, (double) (127 + (audioMap.size() * 30)));
			audio_panel.getChildren().add(files);
			AnchorPane.setLeftAnchor(files, 345D);
			AnchorPane.setTopAnchor(files, (double) (127 + (audioMap.size() * 30)));
			audio_panel.getChildren().add(pickFile);
			AnchorPane.setLeftAnchor(pickFile, 508D);
			AnchorPane.setTopAnchor(pickFile, (double) (127 + (audioMap.size() * 30)));
			audio_panel.getChildren().add(loops);
			AnchorPane.setLeftAnchor(loops, 600D);
			AnchorPane.setTopAnchor(loops, (double) (131 + (audioMap.size() * 30)));
			audio_panel.getChildren().add(stopPins);
			AnchorPane.setLeftAnchor(stopPins, 680D);
			AnchorPane.setTopAnchor(stopPins, (double) (127 + (audioMap.size() * 30)));
			audioMap.put(audioMap.size(),
					new ArrayList<Control>(Arrays.asList(inputPins, stopPins, playlistOption, files, pickFile, loops)));
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

					{// Mixer
						Element mixer = doc.createElement("mixer");
						rootElement.appendChild(mixer);

						Element freq = doc.createElement("frequency");
						freq.appendChild(doc.createTextNode("48000"));
						mixer.appendChild(freq);

						Element depth = doc.createElement("bit_depth");
						depth.appendChild(doc.createTextNode("16"));
						mixer.appendChild(depth);

						Element sign = doc.createElement("signed");
						sign.appendChild(doc.createTextNode("true"));
						mixer.appendChild(sign);

						Element channel = doc.createElement("channels");
						channel.appendChild(doc.createTextNode("2"));
						mixer.appendChild(channel);

						Element buffer = doc.createElement("buffer");
						buffer.appendChild(doc.createTextNode("1024"));
						mixer.appendChild(buffer);
					}
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
					{// Audio
						Element audio = doc.createElement("Audio");
						audio.setAttribute("interruptible", "true");
						rootElement.appendChild(audio);
						for (List<Control> elem : audioMap.values()) {
							if (((ChoiceBox<String>) elem.get(2)).getValue() != null) {
								if (((ChoiceBox<String>) elem.get(2)).getValue().equals("Play a Song")) {
									if (((ChoiceBox<Integer>) elem.get(0)).getValue() != null) {
										Element song = doc.createElement("song");
										song.setAttribute("pin", "" + ((ChoiceBox<Integer>) elem.get(0)).getValue());
										song.setAttribute("loop", "" + ((CheckBox) elem.get(5)).isSelected());
										if (((ChoiceBox<Integer>) elem.get(1)).getValue() != null) {
											song.setAttribute("stop_pin",
													"" + ((ChoiceBox<Integer>) elem.get(1)).getValue());
										}
										if (!((TextField) elem.get(3)).getText().isEmpty()) {
											song.appendChild(doc.createTextNode(((TextField) elem.get(3)).getText()));
										} else {
											song.appendChild(doc.createTextNode("null"));
										}

										audio.appendChild(song);
									}
								} else {
									Element playlist = doc.createElement("playlist");
									playlist.setAttribute("pin", "" + ((ChoiceBox<Integer>) elem.get(0)).getValue());
									playlist.setAttribute("random", "" + ((CheckBox) elem.get(5)).isSelected());
									String songs = ((TextField) elem.get(3)).getText();

									for (String song : songs.split(Pattern.quote(";"))) {
										Element songName = doc.createElement("name");
										if (song.isEmpty()) {
											song = "null";
										}
										songName.appendChild(doc.createTextNode(song));
										playlist.appendChild(songName);
									}
									audio.appendChild(playlist);
								}
							}
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
