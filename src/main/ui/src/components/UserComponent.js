import React from 'react'
import UserService from '../services/UserService'

class UserComponent extends React.Component {

    constructor(props){
        super(props)
        this.state = {
            users:[]
        }
    }

    componentDidMount(){
        this.getData()
    }

    getData = () => {
        try{
            UserService.getUsers().then((response) =>{
                console.log(response)
                this.setState({
                    users: response.data
                })
                return response
            }).catch(function (error) {
                console.log("Error while fetching user data")
            })
        }catch (error){
            console.log('Error while fetching user data')
        }
        
    }

    render (){
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
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.users.map(
                            user =>
                            <tr key={user.accountID}>
                                <td> {user.accountID} </td>
                                <td>{user.userName}</td>
                                <td>{user.password}</td>
                                <td>{user.email}</td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>
        )
    }
}

export default UserComponent