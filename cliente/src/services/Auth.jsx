import React, {createContext, useEffect, useState} from "react";
import { useNavigate } from "react-router-dom";
import Api from "./Api.ts";
import { LoginDTO } from "../model/LoginDTO.ts";

export const AuthContext = createContext();

export const AuthProvider = ({children}) => {

    const navigate = useNavigate();

    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);

    const [loginDTO] = useState(new LoginDTO());

    useEffect(() => {
        const recoveredUser = localStorage.getItem("user");
       
        if(recoveredUser){
            setUser(JSON.parse(recoveredUser))
        }

        setLoading(false);
    },[])

    const login = (login, senha) => {
        console.log("LOGIN EM CONTENT: ", {login, senha})

        loginDTO.setLogin(login);
        loginDTO.setSenha(senha);

        console.log(loginDTO);

        Api.post("/rest/login", loginDTO).then(response => {
            console.log("====>>>>> ", response);
            const LoggedUser = {
                login,
                token: response.data.tokenJWT,
            }

            localStorage.setItem("user", JSON.stringify(LoggedUser))

            setUser(LoggedUser);

            navigate("/");

        }).catch(error => {
            alert(error.response.data.mensagem)
            console.error("====>>>>> ", error);
        })
        
    }

    const logout = () =>{
        console.log("LOGOUT");
        localStorage.removeItem("user");
        setUser(null);
        navigate("/login")
    }

    return(
        <AuthContext.Provider value={{authenticated: !!user, user, loading, login, logout}}>
            {children}
        </AuthContext.Provider>
    )
}