import React, { Component } from 'react';

class Budget extends Component {
    constructor(props) {
        super(props);
        this.state = {
            salary: this.props.salary,
            annualBudget: 0,
            totalCurSpend: this.props.spend,
            percentRecommend: 'Enter a salary to receive a recommendation based on the 50-30-20 rule.',
            stateTax: 0,
            fedTax: 0,
        }
        if(this.state.salary > 0){
            let afterTax = this.afterTax(this.state.salary)
            this.state.annualBudget = afterTax[0]*0.8
            this.state.percentRecommend = this.percentRecommend(afterTax[0]*0.8)
            this.state.fedTax = afterTax[1]
            this.state.stateTax = afterTax[2]
        }
    }
    componentDidUpdate(prevProps,prevState){
        // console.log(prevState)
        // console.log(this.state)
    }

    handleInputChange = (event) =>{
        const name = event.target.name
        const value = event.target.value
        this.setState({
            [name]: value
        });
        if(name === 'salary'){
            if (value > 0){
                this.calculate(value)
            }
        }
    }
    calculate(sal){
        let afterTaxSal = this.afterTax(sal)
        this.setState({
            annualBudget: afterTaxSal[0]*0.8,
            percentRecommend: this.percentRecommend(afterTaxSal[0]*0.8),
            fedTax: afterTaxSal[1],
            stateTax: afterTaxSal[2]
        })

    }
    percentRecommend(sal){
        let needs = (sal/12*0.5).toFixed(2)
        let wants = (sal/12*0.3).toFixed(2)
        let savings = (sal/12*0.2).toFixed(2)

        let recommend = `Monntly allocation for needs: ${needs} 
        \nMontly allocation for wants: ${wants}
        \nMontly allocation for savings: ${savings}`

        return recommend
    }
    afterTax(sal){
        let afterTax = 0;
        let fedTax = 0
        let stateTax = 0
        if(sal > 523601.0){
            fedTax = 157804.25 + ((sal - 523600)*.37);
        }
        else if(sal > 209426){
            fedTax = 47843 + ((sal - 209425)*.35);
        }
        else if(sal > 164926){
            fedTax = 33603 + ((sal - 164925)*.32);
        }
        else if(sal > 86376){
            fedTax = 14751 + ((sal - 86375)*.24);
        }
        else if(sal > 40526){
            fedTax = 4664 + ((sal - 40525)*.22);
        }
        else if(sal > 9951){
            fedTax = 995 + ((sal - 9950)*.12);
        }
        else{
            fedTax = sal * .1;
        }
        stateTax = sal * 0.0399;
        afterTax = sal - fedTax - stateTax;
        return [afterTax, fedTax.toFixed(2), stateTax.toFixed(2)];
    }
    loadBudget(){

    }
    render(){
        let monthlyBudget = this.state.annualBudget/12
        let monthlyBudgetRemaining = 'You have reached or exceeded your montly budget'
        if(this.state.totalCurSpend < monthlyBudget){
            monthlyBudget = (monthlyBudget -  this.state.totalCurSpend)
            monthlyBudgetRemaining = `You still have ${monthlyBudget.toFixed(2)} left to spend this month.`
        }

        return (
        <div className='budget-core'>
            <div className='budget-piece'>
                <div>
                    Salary:&nbsp;&nbsp;
                    <input type='number' name='salary' value={this.state.salary} onChange={this.handleInputChange} />
                </div>
                <div>
                    Federal Tax:&nbsp;&nbsp; {this.state.fedTax}&emsp;&emsp;
                    State Tax:&nbsp;&nbsp; {this.state.stateTax}
                </div>
            </div>
            <div className='budget-piece'>
                <div>
                    Total Spent:&nbsp;&nbsp;
                    <input type='number' name='totalCurSpend' value={this.state.totalCurSpend} onChange={this.handleInputChange}/>
                </div>
                <div>
                    {monthlyBudgetRemaining}
                </div>
            </div>
            <div>
                <p>{this.state.percentRecommend}</p>
            </div>
            <div>
                <button onClick={event=>this.props.handleSave(event,this.state.salary, this.state.totalCurSpend)}>Save</button>
            </div>

        </div>
)
    }
}
export default Budget