import React, { Component } from 'react';



class LoanItem{
    constructor(name, amount, interestRate, terms,  repaymentPeriod){
        this.name = name
        this.amount = amount
        this.interestRate = interestRate
        this.terms = terms
        this.repaymentPeriod = repaymentPeriod

        let monthlyRate = interestRate/12
        let totalTerm = terms*12

        let cRateN = monthlyRate*(Math.pow(1+monthlyRate,totalTerm))
        let cRateD = Math.pow((1+monthlyRate), totalTerm) - 1

        this.monthlyPayment = (amount * (cRateN/cRateD)).toFixed(2)


    }
}
function Loan(props){
    let loan = props.loan
    return (<>
        <td>{loan.name}</td>
        <td>{loan.amount}</td>
        <td>{loan.interestRate}</td>
        <td>{loan.terms}</td>
        <td>{loan.monthlyPayment}</td>
        <td>{loan.repaymentPeriod}</td></>)
}
class Payment extends Component {
    constructor(props) {
        super(props);
        this.state = {
            monthlyBudget: 0,
            loans: [{id: 1, loan: new LoanItem('Example', 200, 4, 2, '2022-05-30' )},
            {id: 2, loan: new LoanItem('Example1', 200, 4, 2, '2022-05-30' )},
            {id: 3, loan: new LoanItem('Example2', 200, 4, 2, '2022-05-30' )},
            {id: 4, loan: new LoanItem('Example3', 200, 4, 2, '2022-05-30' )}],
            currentId: 4
        }
    }
    removeLoan = (event, id) =>{
        console.log(id)
        let loans = this.state.loans
        let foundLoan = loans.find(element => element.id ===id)
        let foundLoanIndex = loans.indexOf(foundLoan)
        if(foundLoanIndex > -1){
            loans.splice(foundLoanIndex,1)
        }
        this.setState({
            loans: loans
        })
    }
    addLoan = (event) =>{
        let name = document.getElementById('loanName').value
        let amount = document.getElementById('loanAmount').value
        let interestRate = document.getElementById('loanInterestRate').value
        let terms = document.getElementById('loanTerms').value
        let repaymentPeriod = document.getElementById('loanRepaymentPeriod').value
        
        let loans = this.state.loans
        let newId = this.state.currentId + 1
        loans.push({id: newId, loan: new LoanItem(name, amount,interestRate,terms,repaymentPeriod)})
        this.setState({
            loans: loans,
            currentId: newId
        })
    }
    render() {
        return (
            <div className='payment-core'>
                <div className='payment-piece'>
                    <table>
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Amount</th>
                                <th>Interest Rate</th>
                                <th>Term</th>
                                <th>Monthly Payment</th>
                                <th>Repayment Period</th>
                                <th>Add/Remove</th>
                            </tr>
                        </thead>
                        <tbody>
                            {this.state.loans.map((iloan) => (<tr> <Loan key={iloan.id} loan={iloan.loan}/> <td><button onClick={(event) => this.removeLoan(event, iloan.id)}>Delete</button></td></tr>))}
                            
                        </tbody>
                        <tfoot>
                            <tr id='loanFoot'>
                                <td><input id='loanName' type='text' name='name'/></td>
                                <td><input id='loanAmount'type='number' name='amount'/></td>
                                <td><input id='loanInterestRate'type='number' name='interestRate'/></td>
                                <td><input id='loanTerms' type='number' name='terms'/></td>
                                <td>Calculated</td>
                                <td><input id='loanRepaymentPeriod'type='text' name='repaymentPeriod'/></td>
                                <td><button onClick={(event) =>this.addLoan(event)}>Add</button></td>
                            </tr>
                        </tfoot>
                    </table>
                </div>
                <div className='payment-piece'>

                </div>
            </div>
        );
    }
}

export default Payment;