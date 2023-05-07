import { Navigate, Outlet, Route, Routes } from "react-router-dom"
import Login from "../pages/Login/Login.jsx"
import Home from "../pages/Home/Home"
import { useContext, useState } from "react"
import { AuthContext, AuthProvider } from "../Services/Auth.jsx"
import Page1 from "../pages/Page1/Page1.jsx"
import "./Content.css";
import Headers from "../componentes/Headers/Headers.jsx"
import DadosResumidoMenu from "../componentes/Usuario/DadosResumidoMenu.jsx"
import MenuLateral from "./Menu.jsx"
import ListarUsuario from "../componentes/Usuario/ListarUsuario.jsx"
import Usuario from "../pages/Usuarios/Usuario.jsx"
import FormularioUsuario from "../componentes/Usuario/FormularioUsuario.jsx"

const Content = () => {

    const [oculta, setOculta] = useState(true);
    

    console.log(oculta);

    const Private = ({ children }) => {

        const { authenticated, loading } = useContext(AuthContext);

        if (loading) {
            console.log("Carrengando PG");
            <div className="loading">Carregando...</div>
        }

        console.log(authenticated)
        console.log(oculta)

        if (!authenticated) {
            setOculta(true);
            console.log(oculta)
            return <Navigate to="/login" />
        }else{
            setOculta(false);
        }

        return children;
    }

    return (

        <AuthProvider>

            <main className="content">

                <aside className={oculta ? "oculta" : "menu cor_menu"}>

                    <DadosResumidoMenu />
                    
                    <MenuLateral />

                    <Outlet />
                </aside>



                <main className={"renderizar"}>

                    <div className={oculta ? "oculta" : ""}>
                        <Headers />
                    </div>


                    <div className={oculta ? "" : "routes"}>
                        <Routes>
                            <Route path="/" exact element={<Private><Home /></Private>} />
                            <Route path="page1" element={<Private><Page1 /></Private>} />
                            <Route path="login" element={<Login />} />
                            <Route path="usuario" element={<Private><Usuario /></Private>} />
                            <Route path="usuarioListar" element={<Private><ListarUsuario /></Private>} />
                            <Route path="formularioIsuario" element={<Private><FormularioUsuario /></Private>} />
                            

                        </Routes>
                    </div>
                </main>



            </main>
        </AuthProvider>

    )
}
export default Content