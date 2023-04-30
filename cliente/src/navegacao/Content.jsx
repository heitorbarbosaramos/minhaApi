import { Route, Routes } from "react-router-dom";
import Home from "../pages/Home/Home.jsx";
import Page1 from "../pages/Page1/Page1.jsx";
import Page2 from "../pages/Page2/Page2.jsx";
import NotFound from "../pages/NotFound/NotFound.jsx";

import './Content.css';
import Headers from "../Components/Headers/Headers.jsx";

const Content = () => {
    return(
        <main className="content cor_fundo">
            <Headers />
            <div className="routes">
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="page1" element={<Page1 />} />
                    <Route path="page2" element={<Page2 />} />
                    <Route path="*" element={<NotFound />} />
                </Routes>
            </div>
          
        </main>
    )
}
export default Content;