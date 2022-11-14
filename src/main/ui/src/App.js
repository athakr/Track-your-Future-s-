import Tabs from "./components/Tabs";
import Budget from "./components/budget";
import Payment from "./components/payment";
import './App.css';

function App() {
  //const budget = new budget()
  return (
  <div className='main'>
    <div className='core'>
      <h1>Track Your Future</h1>
      <Tabs>
        <div label="Budget">
          <Budget/>
        </div>
        <div label="Tracker">
          Tracker tab.
        </div>
        <div label="Payment">
          <Payment/>
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
  );
}

export default App;
