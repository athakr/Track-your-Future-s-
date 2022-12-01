import axios from 'axios'

const USERS_REST_API_URL = 'http://localhost:8080/api/users'
const api = axios.create({baseURL: USERS_REST_API_URL})


class UserService {
    
    getUsers(){
        return api.get(USERS_REST_API_URL)
    }

    createUser = async (userName, password, email) =>{
        const headers = {'My-Custom-Header': 'foobar', 'budget': 1.0001}
        let response = await api.post('', 
        {userName: userName ,password: password,email: email},
        {headers} )
    }
    updateUser = async (salary, spending, id) =>{
        const headers = {'budgetsalary': salary, 'budgetspending': spending}
        console.log(id)
        let response = await api.put('', {id}, {headers})
    }
    deleteUser = async (id) =>{
        let data = await api.delete('',{data: id})
    }
}

export default new UserService()