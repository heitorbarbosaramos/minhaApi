/* eslint-disable jsx-a11y/anchor-is-valid */
import { useContext, useEffect, useState } from "react";
import { AuthContext } from '../../Services/Auth.jsx';
import "./DadosResumidoMenu.css";

const DadosResumidoMenu = () =>{

    const { authenticated, logout } = useContext(AuthContext);

    const handleLogout = () => {
        logout();
    }

    const [nome, setNome] = useState(localStorage.getItem("myApiNome"))
    const [login, setLogin] = useState(localStorage.getItem("myApiLogin"))

    useEffect(() => {
        const nomeExibe = localStorage.getItem("myApiNome") !== null ? localStorage.getItem("myApiNome") : "nome do usuário";
        const loginExibe = localStorage.getItem("myApiLogin") !== null ? localStorage.getItem("myApiLogin") : "login do usuário";
        setNome(nomeExibe);
        setLogin(loginExibe);
    }, [authenticated])

    console.log("AUTHENTICATED: ", authenticated);

    return(
        <div className="dados">
            <p>{String(nome)}</p>
            <p>{String(login)}</p>
            {authenticated && (
                <p><a href="#" onClick={handleLogout}> Logout</a></p>
            )}            
            <hr/>
        </div>
    )
}
export default DadosResumidoMenu