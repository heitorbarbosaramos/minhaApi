import { Route, Routes, Navigate } from "react-router-dom";
import Home from "../pages/Home/Home.jsx";
import Page1 from "../pages/Page1/Page1.jsx";
import Page2 from "../pages/Page2/Page2.jsx";
import NotFound from "../pages/NotFound/NotFound.jsx";

import { AuthProvider, AuthContext } from "../services/Auth.jsx";

import './Content.css';
import Headers from "../Components/Headers/Headers.jsx";
import Login from "../pages/Login/Login.jsx";
import { useContext } from "react";

const Content = () => {

    const Private = ({children}) => {
        
        const { authenticated, loading } = useContext(AuthContext);

        if(loading){
            <div className="loading">Carregando...</div>
        }

        if(!authenticated){
            return <Navigate to="/login" />
        }

        return children;
    }


    return(
        <main className="content cor_fundo">
             <AuthProvider>
            <Headers />
            <div className="routes">
               
                    <Routes>  
                        <Route path="login" element={<Login />} />                 
                        <Route exact path="/" element={<Private><Home /></Private>} />                        
                        <Route path="page1" element={<Private><Page1 /></Private>} />
                        <Route path="page2" element={<Private><Page2 /></Private>} />
                        <Route path="*" element={<NotFound />} />
                    </Routes>
                
            </div>
            </AuthProvider>
          
        </main>
    )
}
export default Content;