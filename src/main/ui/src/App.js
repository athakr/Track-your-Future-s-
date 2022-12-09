import Tabs from "./components/Tabs";
import Budget from "./components/budget";
import Payment from "./components/payment";
import UserComponent from "./components/UserComponent";
import './App.css';
import React from "react";

class App extends React.Component{
  constructor(props){
    super(props)
    this.handleBudgetChange = this.handleBudgetChange.bind(this)
    this.state = {
      budget: {salary: 0, totalCurSpend: 0},
      payment: {loans: []},
      user: {budget: {salary: 0, totalCurSpend: 0}},
      currentUserID: -1,
      count: 0
    }
  }
  handleBudgetChange(budget){
    this.setState({budget: budget})
  }

  render(){
    //Code for Statesharing between subcomponents.
      const handleSave = (event, sal, spend) => {
        this.setState({budget: {salary: sal, totalCurSpend: spend}});
        if (this.state.currentUserID !== -1){
          this.setState(prevState => {
            let prevUser = Object.assign({}, prevState.user)
            prevUser.budget.salary = sal
            prevUser.budget.spending = spend
          })
        }
      };
      const handlePaySave = (event, loan) => {
        this.setState({payment: {loans: loan}});
      }
      const selectUser = (event, curUserID, curUser) =>{
        this.setState({currentUserID: curUserID, user: curUser})
      }
      //Conditional code usef to stateshare 
      let sal = this.state.budget.salary
      let spen = this.state.budget.totalCurSpend
      if (this.state.currentUserID !== -1){
        sal = this.state.user.budget.salary
        spen = this.state.user.budget.spending
      }
      return (
      <div className='main'>
        <div className='core'>
          <h1>Track Your Future</h1>
          <Tabs >
            <div label="Budget">
                <Budget salary={sal} spend={spen} handleSave={handleSave}/>
            </div>
            <div label="Tracker">
              Tracker tab.
            </div>
            <div label="Payment">
              <Payment loans={this.state.payment.loans} handlePaySave={handlePaySave}/>
            </div>
            <div label="Engine">
              Engine tab.
            </div>
            <div label="Profile">
              <UserComponent currentUserID={this.state.currentUserID} user={this.state.user} selectUser={selectUser}/>
            </div>
          </Tabs>
          </div>
      </div>
      );
    }
}




export default App;
