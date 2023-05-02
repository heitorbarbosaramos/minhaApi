import React, { useContext } from 'react'
import './Headers.css'
import { AuthContext } from '../../Services/Auth'
const Headers = () =>{

    const {  logout } = useContext(AuthContext)

    const handleLogout = () => {
        logout();
    }

    return(
        <header className='header cor_header'>
            Meu sistema Legal
            <button onClick={handleLogout}>Logout</button>
        </header>
    )
}
export default Headers