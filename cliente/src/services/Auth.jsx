import { createContext, useState, useEffect } from "react"
import { useNavigate } from "react-router-dom";
import { LoginDTO } from "../models/LoginDTO.ts";
import ApiService from "./ApiService.ts";
import ToastError from "../componentes/Toast/ToastError.jsx";
import ToastSucess from "../componentes/Toast/ToastSucess.jsx";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {

    const navigate = useNavigate();

    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);
    const [recoveryPassword, setRecoveryPassword] = useState(false);
    const [showToastSucess, setShowToastSucess] = useState(false);
    const [showToastError, setShowToastError] = useState(false);
    const [mensagemToast, setMensagemToast] = useState("");

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
            localStorage.setItem("myApiToken", JSON.stringify(response.data.tokenJWT).replaceAll("\"", ""))
            localStorage.setItem("myToken", JSON.stringify(LoggedUser))

            setUser(LoggedUser);
            setRecoveryPassword(false);
            setShowToastError(false);
            navigate("/");

        }).catch(error => {
            logado = false;
            setRecoveryPassword(true);
            setShowToastError(true);
            setMensagemToast(error.response.data.mensagem);
            console.error("====>>>>> ", error);
        })

    }

    const recuperaSenha = (email, setSpinner) => {
        ApiService.get(`/rest/usuario/geralinkrecuperasenha/${email}`).then(response => {
            console.log("EMAIL RECUPERA SENHA ENVIADO: ", response);
            setMensagemToast("Verifique seu email para recuperar a senha");
            setShowToastSucess(true);
            setSpinner(false);
        }).catch(error => {
            console.log("ERROR: ", error)
            setMensagemToast(error.response.data.mensagem);
            setShowToastError(true);
            setSpinner(false);
        })

    }

    const logout = () => {
        logado = false;
        console.log("LOGOUT");
        localStorage.removeItem("myApiLogin")
        localStorage.removeItem("myApiNome")
        localStorage.removeItem("myApiPerfis")
        localStorage.removeItem("myApiToken");
        localStorage.removeItem("myToken");
        setUser(null);
        navigate("/login?logout")
    }

    console.log(user);
    console.log(logado);
    console.log(logado);

    return (
        <>
            <ToastSucess show={showToastSucess} setShow={setShowToastSucess} mensagem={mensagemToast}/>
            <ToastError show={showToastError} setShow={setShowToastError} mensagem={mensagemToast}/>
            <AuthContext.Provider value={{ authenticated: logado, user, loading, login, logout, recoveryPassword, recuperaSenha }}>
                {children}
            </AuthContext.Provider>
        </>
    )

}

