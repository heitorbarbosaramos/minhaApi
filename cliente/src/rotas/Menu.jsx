/* eslint-disable jsx-a11y/anchor-is-valid */
import { Link } from "react-router-dom";
import DadosSistema from "../componentes/DadosSistema/DadosSistema.jsx"
import Icone from "../componentes/Icone/Icone.jsx"
import { useState } from "react";
import "./Menu.css";

const MenuLateral = () => {

    const [ocultaUsuario, setOcultaUsuario] = useState(true);
    return (
        <nav>

            <ul>
                <li><Link to="/"><Icone icone="cottage" /> HOME</Link></li>
                <li style={{ display: "block" }}>
                    <a onClick={(e) => { e.preventDefault(); setOcultaUsuario(!ocultaUsuario); console.log(ocultaUsuario) }} className="cursor_ponter"><Icone icone="group" /> USUARIOS <Icone icone={ocultaUsuario ? "expand_more" : "expand_less"} /></a>
                    <ul className={ocultaUsuario ? "oculta" : "submenu"}>
                        <li><Link to="usuario"><Icone icone="browse_activity" />Geral</Link></li>
                        <li><Link to="usuarioListar"><Icone icone="switch_account" />Listar usuários</Link></li>
                        <li><Link to="formularioIsuario"><Icone icone="assignment" />Formulario usuários</Link></li>
                    </ul>
                </li>
                <li><Link to="page1"><Icone icone="arrow_forward" /> PAGINA 1</Link></li>
            </ul>
            <DadosSistema />
        </nav>
    )
}
export default MenuLateral