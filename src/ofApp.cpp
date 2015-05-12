#include "ofApp.h"

//--------------------------------------------------------------
void ofApp::setup(){

	if (XML.loadFile("baseSettings.xml")){
		ofLogNotice("Main") << "baseSettings.xml loaded!";
	}
	else{
		ofLogNotice("Main") << "unable to load baseSettings.xml check data/ folder";
	}

	font.loadFont(OF_TTF_SANS, 12);
	pinMenuFont.loadFont(OF_TTF_SANS, 18);

	next.setButtonStyle(UI_BUTTON_ROUNDED_RECT);
	next.setColor(ofColor::darkBlue);
	next.setHoverColor(ofColor::darkSlateBlue);
	next.setTextColor(ofColor::white);
	next.setTitle("Next");
	next.setFont(font);
	next.setID(1);

	back.setButtonStyle(UI_BUTTON_ROUNDED_RECT);
	back.setColor(ofColor::darkBlue);
	back.setHoverColor(ofColor::darkSlateBlue);
	back.setTextColor(ofColor::white);
	back.setTitle("Back");
	back.setFont(font);
	back.setID(0);

	screenState = PINASSIGN;

	// this is all the stuff neeeded for the pin assignments
	boardSelect.setPosition(30, 20);
	boardSelect.setWidth(150);
	boardSelect.setHeight(30);

	boardSelect.addMenuItem("Pi Model A/B (Rev 1)");
	boardSelect.addMenuItem("Pi Model B (Rev 2)");
	boardSelect.addMenuItem("Pi Model B+");

	boardSelect.setButtonTitle("Pi Model A/B (Rev 1)");

	currentBoard = "Pi Model A/B (Rev 1)";
	boardSelect.setButtonColor(ofColor::lightGray);

	boardSelect.setButtonFont(font);

	piImage.loadImage("B_Pinout.png");
	piImg2.loadImage("p5header.png");

	for (int i = 0; i < 31; i++){
		pinMenus[i].setButtonTitle("GPIO " + ofToString(i + 1));
		pinMenus[i].setHeight(30);
		pinMenus[i].setButtonFont(pinMenuFont);
		pinMenus[i].addMenuItem("GPIO " + ofToString(i + 1));
		pinMenus[i].addMenuItem("Input Pullup");
		pinMenus[i].addMenuItem("Input Pulldown");
		pinMenus[i].addMenuItem("Output High");
		pinMenus[i].addMenuItem("Output Low");
		pinMenus[i].setButtonColor(ofColor::lightGray);
		pinMenus[i].setID(i + 1);
		pinMenus[i].setAutoSizing(true);
		ofAddListener(pinMenus[i].menuEvent, this, &ofApp::UIMenuSelection);
	}
	pinMenus[1].setPosition(125, 157);
	pinMenus[2].setPosition(125, 197);
	pinMenus[3].setPosition(125, 237);
	pinMenus[16].setPosition(125, 317);
	pinMenus[26].setPosition(125, 357);
	pinMenus[21].setPosition(125, 397);
	pinMenus[9].setPosition(125, 477);
	pinMenus[8].setPosition(125, 517);
	pinMenus[10].setPosition(125, 557);

	pinMenus[13].setPosition(700, 237);
	pinMenus[14].setPosition(700, 277);
	pinMenus[17].setPosition(700, 317);
	pinMenus[22].setPosition(700, 397);
	pinMenus[23].setPosition(700, 437);
	pinMenus[24].setPosition(700, 517);
	pinMenus[7].setPosition(700, 557);
	pinMenus[6].setPosition(700, 597);

	pinMenus[27].setPosition(925, 113);
	pinMenus[28].setPosition(1225, 113);
	pinMenus[29].setPosition(925, 157);
	pinMenus[30].setPosition(1225, 157);

	pinMenus[4].setPosition(100, 611);
	pinMenus[5].setPosition(100, 647);
	pinMenus[12].setPosition(100, 683);
	pinMenus[18].setPosition(100, 719);
	pinMenus[25].setPosition(100, 755);

	pinMenus[11].setPosition(630, 647);
	pinMenus[15].setPosition(630, 719);
	pinMenus[19].setPosition(630, 755);
	pinMenus[20].setPosition(630, 791);

	if (piImage.getHeight() > ofGetScreenHeight()*.8){
		float resizeRatio = (ofGetScreenHeight()*.8 + 20) / piImage.getHeight();
		piImage.resize(piImage.getWidth()*resizeRatio, piImage.getHeight()*resizeRatio);
	}
	//pin setup
	ofSetWindowShape(piImage.getWidth() + 40, piImage.getHeight() + 20);

	ofAddListener(boardSelect.menuEvent, this, &ofApp::UIMenuSelection);
	ofAddListener(next.buttonEvent, this, &ofApp::UIButPressed);
	ofAddListener(back.buttonEvent, this, &ofApp::UIButPressed);
}

//--------------------------------------------------------------
void ofApp::update(){

}

//--------------------------------------------------------------
void ofApp::draw(){
	switch (screenState){
	case (PINASSIGN) : {
		ofSetColor(255);
		piImage.draw(20, 10);
		if (currentBoard == "Pi Model A/B (Rev 1)" || currentBoard == "Pi Model B (Rev 2)"){
			pinMenus[1].draw();
			pinMenus[2].draw();
			pinMenus[3].draw();
			pinMenus[16].draw();
			pinMenus[26].draw();
			pinMenus[21].draw();
			pinMenus[9].draw();
			pinMenus[8].draw();
			pinMenus[10].draw();
			pinMenus[13].draw();
			pinMenus[14].draw();
			pinMenus[17].draw();
			pinMenus[22].draw();
			pinMenus[23].draw();
			pinMenus[24].draw();
			pinMenus[7].draw();
			pinMenus[6].draw();
			if (currentBoard == "Pi Model B (Rev 2)"){
				piImg2.draw(30 + piImage.getWidth(), 10);
				pinMenus[27].draw();
				pinMenus[28].draw();
				pinMenus[29].draw();
				pinMenus[30].draw();
			}
		}
		else {
			for (int i = 0; i < 27; i++){
				pinMenus[i].draw();
			}
		}
		for (int i = 0; i < 31; i++){
			if (pinMenus[i].getToggled())
				pinMenus[i].draw();
		}
		boardSelect.draw();
	}
		next.draw(ofGetWidth() - 100, ofGetHeight() - 40, 80, 25);
		break;
	case CONTROLASSIGN:
		ofSetColor(0);
		ofDrawBitmapString("output control pin | change duration, 0 = toggle", 5, 10);
		for (int i = 0; i < outputMenu.size(); i++){
			outputMenu[i]->draw();
			outputDuration[i]->draw();
		}
		if (errors.size()>0){
			ofPushStyle();
			ofSetColor(ofColor::red);
			for (int i = 0; i < errors.size(); i++){
				ofDrawBitmapString("* Not a number", 260, 40 + 40 * errors[i]);
			}
			ofPopStyle();
		}
		for (int i = 0; i < outputMenu.size(); i++){
			if (outputMenu[i]->getToggled())
				outputMenu[i]->draw();
		}
		back.draw(20, ofGetHeight() - 40, 80, 25);
		next.draw(ofGetWidth() - 140, ofGetHeight() - 40, 120, 25);
		break;
	default:
		break;
	}
}

//--------------------------------------------------------------
void ofApp::keyPressed(int key){

}

//--------------------------------------------------------------
void ofApp::keyReleased(int key){

}

//--------------------------------------------------------------
void ofApp::mouseMoved(int x, int y){

}

//--------------------------------------------------------------
void ofApp::mouseDragged(int x, int y, int button){

}

//--------------------------------------------------------------
void ofApp::mousePressed(int x, int y, int button){

}

//--------------------------------------------------------------
void ofApp::mouseReleased(int x, int y, int button){

}

//--------------------------------------------------------------
void ofApp::windowResized(int w, int h){

}

//--------------------------------------------------------------
void ofApp::gotMessage(ofMessage msg){

}

//--------------------------------------------------------------
void ofApp::dragEvent(ofDragInfo dragInfo){

}

void ofApp::UIMenuSelection(const pair<string, int> & selection){
	if (selection.second == 0){
		boardSelect.setButtonTitle(selection.first);
		boardSelect.setWidth(font.getStringBoundingBox(selection.first, 0, 0).width + 20);
		if (selection.first != currentBoard){
			//we already know that the option selected cant be the same
			//therefore it can't be B+ twice so we wont reload an image here
			if (ofIsStringInString(selection.first, "+"))
				piImage.loadImage("B+Pinout.png");
			else if (!ofIsStringInString(currentBoard, "Rev")) //we only reload this image if we change from the B+
				piImage.loadImage("B_Pinout.png");

			if (ofIsStringInString(selection.first, "Rev")){
				if (!ofIsStringInString(currentBoard, "Rev")){
					pinMenus[1].setPosition(125, 157);
					pinMenus[2].setPosition(125, 197);
					pinMenus[3].setPosition(125, 237);
					pinMenus[16].setPosition(125, 317);
					pinMenus[26].setPosition(125, 357);
					pinMenus[21].setPosition(125, 397);
					pinMenus[9].setPosition(125, 477);
					pinMenus[8].setPosition(125, 517);
					pinMenus[10].setPosition(125, 557);

					pinMenus[13].setPosition(700, 237);
					pinMenus[14].setPosition(700, 277);
					pinMenus[17].setPosition(700, 317);
					pinMenus[22].setPosition(700, 397);
					pinMenus[23].setPosition(700, 437);
					pinMenus[24].setPosition(700, 517);
					pinMenus[7].setPosition(700, 557);
					pinMenus[6].setPosition(700, 597);
					if (piImage.getHeight() > ofGetScreenHeight()*.8){
						float resizeRatio = (ofGetScreenHeight()*.8 + 20) / piImage.getHeight();
						piImage.resize(piImage.getWidth()*resizeRatio, piImage.getHeight()*resizeRatio);
					}
				}
				if (ofIsStringInString(selection.first, "Rev 2"))
					ofSetWindowShape(piImage.getWidth() + piImg2.getWidth() + 40, piImage.getHeight() + 20);
				else
					ofSetWindowShape(piImage.getWidth() + 40, piImage.getHeight() + 20);
			}
			else{

				pinMenus[1].setPosition(100, 143);
				pinMenus[2].setPosition(100, 179);
				pinMenus[3].setPosition(100, 215);
				pinMenus[16].setPosition(100, 287);
				pinMenus[26].setPosition(100, 323);
				pinMenus[21].setPosition(100, 359);
				pinMenus[9].setPosition(100, 431);
				pinMenus[8].setPosition(100, 467);
				pinMenus[10].setPosition(100, 503);

				pinMenus[13].setPosition(630, 215);
				pinMenus[14].setPosition(630, 251);
				pinMenus[17].setPosition(630, 287);
				pinMenus[22].setPosition(630, 359);
				pinMenus[23].setPosition(630, 395);
				pinMenus[24].setPosition(630, 467);
				pinMenus[7].setPosition(630, 503);
				pinMenus[6].setPosition(630, 539);

				if (ofIsStringInString(selection.first, "+"))
					if (piImage.getHeight() > ofGetScreenHeight()*.9){
						float resizeRatio = (ofGetScreenHeight()*.9 + 20) / piImage.getHeight();
						piImage.resize(piImage.getWidth()*resizeRatio, piImage.getHeight()*resizeRatio);
					}
					else
						if (piImage.getHeight() > ofGetScreenHeight()*.8){
							float resizeRatio = (ofGetScreenHeight()*.8 + 20) / piImage.getHeight();
							piImage.resize(piImage.getWidth()*resizeRatio, piImage.getHeight()*resizeRatio);
						}
				ofSetWindowShape(piImage.getWidth() + 40, piImage.getHeight() + 20);
			}


			currentBoard = selection.first;
		}
	}
	else if (selection.second < 100){
		pinMenus[selection.second - 1].setButtonTitle(selection.first);
	}
	else if (selection.second < 200) {
		outputMenu[selection.second - 100]->setButtonTitle(selection.first);
	}

}

void ofApp::UIButPressed(const pair<bool, int> & state){

	if (state.second == 1 && screenState == CONTROLASSIGN && state.first){
		errors.clear();
		for (int i = 0; i < outputMenu.size(); i++){
			//check to make sure all the duration values are valid numbers
			if (!isInt(outputDuration[i]->text))
				errors.push_back(i);
		}
		if (errors.size() == 0){
			XML.pushTag("settings");
			XML.pushTag("setup_IO_pins");
			XML.pushTag("input");
			for (int i = 0; i < inputPins.size(); i++){
				//save the xml file and exit
				//XML.addTag("pin");
				XML.addValue("pin", inputPins[i].first);
				XML.addAttribute("pin", "type", (ofIsStringInString(inputPins[i].second, "Pullup") ? "PULLUP" : "PULLDOWN"), i);
				
			}
			XML.popTag();
			XML.pushTag("output");
			for (int i = 0; i < outputPins.size(); i++){
				//save the xml file and exit
				//XML.addTag("pin");
				XML.addValue("pin", outputPins[i].first);
				XML.addAttribute("pin", "state", (ofIsStringInString(outputPins[i].second, "High") ? "HIGH" : "LOW"), i);
				

			}
			XML.popTag();
			XML.popTag();
			XML.pushTag("Control");
			for (int i = 0; i < outputMenu.size(); i++){
				//save the xml file and exit
				if (ofIsStringInString(outputMenu[i]->getSelection(), "Input")){
					XML.addTag("Output");
					string tempS = outputMenu[i]->getSelection();
					ofStringReplace(tempS, "Input Pin ", "");
					XML.addAttribute("Output", "trigger", tempS, i);
					XML.pushTag("Output", i);
					//XML.addTag("pin");
					XML.addValue("pin", outputPins[i].first);
					//XML.addTag("duration");
					XML.addValue("duration", ofToInt(outputDuration[i]->text));
					XML.popTag();
				}

			}
			XML.save("settings.xml");
			ofExit();
		}
	}
	if (state.second == 1 && screenState == PINASSIGN && state.first){
		screenState = CONTROLASSIGN;
		int tempIDs = 0;
		for (int i = 0; i < 31; i++){
			if (ofIsStringInString(pinMenus[i].getSelection(), "Input")){
				pair<int, string> temp(i + 1, pinMenus[i].getSelection());
				inputPins.push_back(temp);
			}
		}
		for (int i = 0; i < 31; i++){

			if (ofIsStringInString(pinMenus[i].getSelection(), "Output")){
				pair<int, string> pTemp(i + 1, pinMenus[i].getSelection());
				outputPins.push_back(pTemp);
				ofxDropDownMenu * temp = new ofxDropDownMenu();
				temp->setButtonTitle("Output Pin " + ofToString(i + 1));
				temp->setHeight(30);
				temp->setButtonFont(font);
				temp->addMenuItem("Output Pin " + ofToString(i + 1));
				for (int j = 0; j < inputPins.size(); j++){
					temp->addMenuItem("Input Pin " + ofToString(inputPins[j].first) + ' ');
				}
				temp->setButtonColor(ofColor::white);
				temp->setID(tempIDs++ + 100);
				temp->setAutoSizing(true);
				temp->setPosition(25, 20 + 40 * outputMenu.size());
				ofAddListener(temp->menuEvent, this, &ofApp::UIMenuSelection);

				ofxTextInputField * tempIn = new ofxTextInputField();
				tempIn->setup();
				tempIn->text = "500";
				tempIn->bounds = ofRectangle(150, 20 + 40 * outputMenu.size(), 100, 30);
				tempIn->setFont(font);
				outputMenu.push_back(temp);
				outputDuration.push_back(tempIn);
			}
		}
		next.setTitle("Save & Exit");
		ofSetWindowShape(400, 80 + 40 * outputMenu.size());
	}
	if (state.second == 0 && screenState == CONTROLASSIGN && state.first){
		next.setTitle("Next");
		inputPins.clear();
		outputMenu.clear();
		outputDuration.clear();
		screenState = PINASSIGN;
		ofSetWindowShape(piImage.getWidth() + 40, piImage.getHeight() + 20);
	}
	//state.first ? cout << "button " + ofToString(state.second) + " pressed down" << endl : cout << "button " + ofToString(state.second) + " depressed" << endl;
}

bool ofApp::isInt(string s){
	for (int i = 0; i < s.length(); i++){
		if (!isdigit(s[i])){
			if (!(s[i] == '-'))
				return false;
		}
	}
	return true;
}