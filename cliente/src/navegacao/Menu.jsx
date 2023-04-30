import { Link, Outlet } from "react-router-dom"
import './Menu.css';
import Icone from "../Components/Icone/Icone";

const Menu = () => {
    return(
        <aside className="menu cor_menu">
            <nav>
                <ul>
                    <li> <Link to="/"> <Icone icone="cottage" /> HOME </Link> </li>
                    <li> <Link to="page1"> <Icone icone="arrow_forward" /> PG 1 </Link> </li>
                    <li> <Link to="page2"> <Icone icone="arrow_forward" /> PG 2 </Link> </li>
                </ul>
            </nav>
            <Outlet />
        </aside>
    )
}
export default Menu