import { useState } from "react"
import ToastError from "../../componentes/Toast/ToastError.jsx"
import ToastSucess from "../../componentes/Toast/ToastSucess.jsx"
import ToastInfo from "../../componentes/Toast/ToastInfo.jsx"
import ToastWornig from "../../componentes/Toast/ToastWarning.jsx"

const Home = () => {

    const [show, setShow] = useState(false)
    return(
        <>
            <button onClick={() => {setShow(true); console.log(show);}}>MOSTRA TOAST</button>
            Componente Home
            <ToastError mensagem="mensagem de erro" show={show} setShow={setShow}/>
            <ToastSucess mensagem="mensagem de sucesso" show={show} setShow={setShow}/>
            <ToastInfo mensagem="mensagem de informaçao" show={show} setShow={setShow}/>
            <ToastWornig mensagem="mensagem de alert" show={show} setShow={setShow}/>
        </>
    )
}
export default Home