# Track-your-Future-s-

Using the Track Your Future application is a multi step process.  Due to time constraints not all features were able to be fully implemented or integrated into one cohesive unit.  This document will guide the user through running the individual units and the setup process for those units.  The sections are as follows.

- Java Server
- React Application
- Python Ticker

Github Link: https://github.com/athakr/Track-your-Future-s-

## Java Server
The java server is a backend data storage and management feature.  It contains the logic to manage and modify the data of various features which at the moment includes the budget and payment tracker.  To run the java server commands will need to be executed in the src/main/java/net directory.  The server is running on and implemented with the latest version of the java development kit, 17.0.5.

### Setup
First the latest version of apache maven should be installed (which can be found here: https://maven.apache.org/download.cgi).  This project uses version 3.8.6.

While at the highest level of the souurce code run the following command:
```
mvn clean install
```
The command may not be recognized.  If this is the case then the environment variables may have to be modified.  First two user variables must be created M2 and M2_HOME.  M2_HOME should be set to the directory of the apache maven installation.  M2 should be set to the bin directory within the home directory of the apache maven installation.

Then the path in the system variables must be modified.  Edit the path system variable to include the M2 path by adding it to the end of the path list.  Once this process is complete the environment for ruunning the project may need to be restarted to recognize the changes made.

### Running the Feature
To run the java server the user must be in the src/main/java/net directory.  Once there compile and run BackendApplication.java.  This should display a long form springboot initializer in the command line once it concludes the server is running.  The user will be able to view a few sample user data by opening the following link in their browser: http://localhost:8080/api/users.  The server otherwise has no functionality until it is used in tangent with the react application.

## React Application
The react application is the frontend user interface intended to meld the features of Track Your Future together.  Currently it supports user data saving, payment tracking, and the budget tool.  To execute any commands or install dependencies the user will need to be in the src/main/ui directory.

### Setup
This feature is written in React.js with css support.  First react dependencies will need to be installed first the user will need to install npm.  Thenthe dependenciescan be installed by executing the following command.

```
npm install
```

This should intall the react dependencies required to run the frontend of the project.

### Running the Feature
To run the feature the following command must be executed in the command line.
```
npm run
```
Note that without the Java server running concurrently with the frontend application the user profiles tab of the application will display an empty list and be un-updatable (as it has no server data to call to).  Running this command may take some time, but when finished should open a website tab which runs the application at http://localhost:3000/

## Python Ticker
Due to the api convenience of python both the Stock Tracker and Engine of the Track Your Future application were implemented there.  Unfortunately a connection to the frontend application itself was a difficult process and an unfinished feature given the timeline.  The features can be run independently though given a small setup.

### Setup
This code is written in python3 and will require the latest stable version of python3 to be installed on the device running the feature. Three dependencies are required for this feature to run, yfinance, pysimplegui, pandas, and numpy.  Using either pip or npm these can be installed locally.  Here is a provided example using pip:
```
pip install yfinance
pip install pysimplegui
pip install pandas
pip install numpy 
```
### Running the Feature
There are two seperate python files to run, one for the stock tracker and the other for the engine.  To execute the code for these features simply type python3 and either gui.py or engine.py for the tracker or engine respectively.

Example
```
python3 gui.py
python3 engine.py
```

To use the tracker feature a stock ticker should be input to the text box.  An example of such would be AAPL for example.

To use the engine follow the instructions given and select a risk level ranging from 1-4.  With 1 being conservative and 4 being aggressive growth.  Then select a dollar ammount and add a line following the below formatting to either the existing engine.py code or a new file importing engine.py.

```
generate = Engine('your risk level', 'dollar amount')
generate.residualDisplay() 
generate.get_recommendation('number of recommendations desired')
```
Then run the code.