import { useContext, useEffect, useState } from "react";
import { AuthContext } from '../../Services/Auth.jsx';
import "./DadosResumidoMenu.css";

const DadosResumidoMenu = () =>{

    const { authenticated } = useContext(AuthContext);

    const [nome, setNome] = useState(localStorage.getItem("myApiNome"))
    const [login, setLogin] = useState(localStorage.getItem("myApiLogin"))

    useEffect(() => {
        const nomeExibe = localStorage.getItem("myApiNome") !== null ? localStorage.getItem("myApiNome") : "nome do usuário";
        const loginExibe = localStorage.getItem("myApiLogin") !== null ? localStorage.getItem("myApiLogin") : "login do usuário";
        setNome(nomeExibe);
        setLogin(loginExibe);
    }, [authenticated])

    return(
        <div className="dados">
            <p>{String(nome)}</p>
            <p>{String(login)}</p>
            <hr/>
        </div>
    )
}
export default DadosResumidoMenu