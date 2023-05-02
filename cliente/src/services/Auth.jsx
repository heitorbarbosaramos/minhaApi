import { createContext, useState, useEffect } from "react"
import { useNavigate } from "react-router-dom";
import { LoginDTO } from "../models/LoginDTO.ts";
import ApiService from "./ApiService.ts";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {

    const navigate = useNavigate();

    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);


    const [loginDTO] = useState(new LoginDTO());

    let logado = localStorage.getItem("myToken") !== null ? true : false;

    useEffect(() => {
        const recoveredUser = localStorage.getItem("myToken");

        console.log(recoveredUser);

        if(recoveredUser) {
            setUser(JSON.parse(recoveredUser))
        }

        setLoading(false);
    }, []);

    const login = (login, senha) => {
        console.log("LOGIN EM AUTH: ", { login, senha })

        loginDTO.setLogin(login);
        loginDTO.setSenha(senha);

        console.log(loginDTO);

        ApiService.post("/rest/login", loginDTO).then(response => {
            console.log("====>>>>> ", response);
            const LoggedUser = {
                login,
                token: response.data.tokenJWT,
            }

            localStorage.setItem("myApiLogin", JSON.stringify(response.data.login).replaceAll("\"", ""))
            localStorage.setItem("myApiNome", JSON.stringify(response.data.nome).replaceAll("\"", ""))
            localStorage.setItem("myApiPerfis", JSON.stringify(response.data.perfis).replaceAll("\"", ""))
            localStorage.setItem("myToken", JSON.stringify(LoggedUser))

            setUser(LoggedUser);

            navigate("/");

        }).catch(error => {
            logado = false;
            alert(error.response.data.mensagem)
            console.error("====>>>>> ", error);
        })

    }

    const logout = () => {
        logado = false;
        console.log("LOGOUT");
        localStorage.removeItem("myApiLogin")
        localStorage.removeItem("myApiNome")
        localStorage.removeItem("myApiPerfis")
        localStorage.removeItem("myToken");
        setUser(null);
        navigate("/login?logout")
    }

    console.log(user);
    console.log(logado);
    console.log(logado);

    return (
        <AuthContext.Provider value={{ authenticated: logado, user, loading, login, logout }}>
            {children}
        </AuthContext.Provider>
    )

}

