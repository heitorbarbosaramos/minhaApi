/* eslint-disable jsx-a11y/anchor-is-valid */
import { useContext, useEffect, useState } from "react";
import { AuthContext } from '../../Services/Auth.jsx';
import "./DadosResumidoMenu.css";
import Cookies from "js-cookie";

const DadosResumidoMenu = () =>{

    const { authenticated, logout } = useContext(AuthContext);

    const handleLogout = () => {
        logout();
    }

    const [nome, setNome] = useState(Cookies.get("api-nome"))
    const [login, setLogin] = useState(Cookies.get("api-login"))

    useEffect(() => {
        const nomeExibe = Cookies.get("api-nome") !== null ? Cookies.get("api-nome") : "nome do usuário";
        const loginExibe = Cookies.get("api-login") !== null ? Cookies.get("api-login") : "login do usuário";
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