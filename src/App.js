import Tabs from "./components/Tabs";
import './App.css';

function App() {
  return (
    <body>
      <div class='main'>
        <div class='core'>
          <h1>Track Your Future</h1>
          <Tabs>
            <div label="Budget">
              Budget tab.
            </div>
            <div label="Tracker">
              Tracker tab.
            </div>
            <div label="Payment">
              Payment tab.
            </div>
            <div label="Engine">
              Engine tab.
            </div>
            <div label="Profile">
              Profile tab.
            </div>
          </Tabs>
          </div>
      </div>
    </body>
  );
}

export default App;
