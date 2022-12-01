import React from 'react'
import UserService from '../services/UserService'


class UserComponent extends React.Component {

    constructor(props){
        super(props)
        this.state = {
            users:[],
            currentUserID: this.props.currentUserID,
            currentUser: this.props.user,
            currentUserName: 'No User Selected'
        }
    }
    
    createData = (event) =>{
        let userName = document.getElementById('userName').value
        let password = document.getElementById('password').value
        let email = document.getElementById('email').value
        UserService.createUser(userName,password,email)
        this.getUsers()
    }

    componentDidMount(){
        this.getUsers()
    }
    // shouldComponentUpdate(nextProps, nextState){
    //     console.log(this.state.users)
    //     console.log(nextState.users)
    //     return true
    // }

    // componentDidUpdate(prevProps, prevState){
    //     console.log(prevState.users)
    //     console.log(this.state.users)
    // }

    getUsers = async () => {
        try{
            let data = await UserService.getUsers().then(({data}) => data)
            this.setState({users: data})
        }catch (error){
            console.log('Error while fetching user data')
        }
    }
    deleteData = async (id) =>{
        let data = await UserService.deleteUser(id)
        this.getUsers()
    }
    selectUser = (event, currentUserID, user) =>{
        this.props.selectUser(event, currentUserID, user)
        this.setState({currentUserID: currentUserID, currentUser: user, currentUserName: user.userName})
    }
    updateUser = async (salary, spending, id) =>{
        if(id !== -1){
            let data = await UserService.updateUser(salary,spending,id)
            this.getUsers()
            console.log(JSON.stringify(this.state.users))
        }
    }
    render (){
        let currentUserName = 'No User Selected'
        if(this.state.currentUser.userName !== null){
            currentUserName = this.state.currentUser.userName
        }
        let salary, spending, id = -1
        if (this.state.currentUser.budget !== {}){
            salary = this.state.currentUser.budget.salary
            spending = this.state.currentUser.budget.spending
            id = this.state.currentUserID
        }
        return (
            <div>
                <h1 className='text-center'>Users List</h1>
                <table className='table table-striped'>
                    <thead>
                        <tr>
                            <td>User Id</td>
                            <td>Username</td>
                            <td>Password</td>
                            <td>Email</td>
                            <td>Delete</td>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.users.map(
                            user =>
                            <tr key={user.accountID}>
                                <td><button onClick={event => (this.selectUser(event, user.accountID, user))}>{user.accountID}</button></td>
                                <td>{user.userName}</td>
                                <td>{user.password}</td>
                                <td>{user.email}</td>
                                <td><button onClick={() => this.deleteData(user.accountID)}>X</button></td>
                            </tr>
                        )}
                    </tbody>
                    <tfoot>
                        <tr>
                            <td>ID</td>
                            <td><input id='userName' type='text'></input></td>
                            <td><input id='password' type='text'></input></td>
                            <td><input id='email' type='text'></input></td>
                            <td>Delete</td>
                        </tr>
                    </tfoot>
                </table>
                <button onClick={(event) => this.createData(event)}>Add User</button>
                <h3>Current User: {currentUserName}</h3>
                <button onClick={() => this.updateUser(salary, spending, id)}>Update User</button>
            </div>
        )
    }
}

export default UserComponent