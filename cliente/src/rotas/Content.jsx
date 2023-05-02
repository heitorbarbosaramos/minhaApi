import { Link, Navigate, Outlet, Route, Routes } from "react-router-dom"
import Login from "../pages/Login/Login.jsx"
import Home from "../pages/Home/Home"
import { useContext, useState } from "react"
import { AuthContext, AuthProvider } from "../Services/Auth.jsx"
import Page1 from "../pages/Page1/Page1.jsx"
import "./Content.css";
import Headers from "../componentes/Headers/Headers.jsx"
import DadosSistema from "../componentes/DadosSistema/DadosSistema.jsx"
import Icone from "../componentes/Icone/Icone.jsx"

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

                    <nav>
                        <ul>
                            <li><Link to="/"><Icone icone="cottage" /> HOME</Link></li>
                            <li><Link to="page1"><Icone icone="arrow_forward" /> PAGINA 1</Link></li>
                        </ul>
                        <DadosSistema />
                    </nav>

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
                        </Routes>
                    </div>
                </main>



            </main>
        </AuthProvider>

    )
}
export default Content