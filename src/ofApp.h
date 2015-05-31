#pragma once

#include "ofMain.h"
#include "ofxXMLSettings.h"
#include "ofxUIUtils.h"
#include "ofBitmapFont.h"

enum States {
	PINASSIGN,
	CONTROLASSIGN

};

enum BoardSelect {
	PIAB,
	PIB2,
	PIBP
};

class ofApp : public ofBaseApp{

	public:
		void setup();
		void update();
		void draw();

		void keyPressed(int key);
		void keyReleased(int key);
		void mouseMoved(int x, int y );
		void mouseDragged(int x, int y, int button);
		void mousePressed(int x, int y, int button);
		void mouseReleased(int x, int y, int button);
		void windowResized(int w, int h);
		void dragEvent(ofDragInfo dragInfo);
		void gotMessage(ofMessage msg);
		
		ofImage piImage;
		ofImage piImg2;
		ofxDropDownMenu pinMenus[31];
		ofxDropDownMenu boardSelect;

		ofxXmlSettings XML;

		ofTrueTypeFont font;
		ofTrueTypeFont pinMenuFont;

		vector<pair<int, string> > inputPins;
		vector<pair<int, string> > outputPins;

		vector<ofxDropDownMenu *> outputMenu;
		vector<ofxTextInputField *> outputDuration;

		void UIMenuSelection(const pair<string, int> & selection);

		string currentBoard;

		States screenState;
		ofxUIButton next, back;

		void UIButPressed(const pair<bool, int> & state);

		bool isInt(string s);

		vector<int> errors;

		BoardSelect board;
};
