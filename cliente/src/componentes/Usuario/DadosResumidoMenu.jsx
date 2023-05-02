import "./DadosResumidoMenu.css";
const DadosResumidoMenu = () =>{

    const nome = localStorage.getItem("myApiNome");
    const login = localStorage.getItem("myApiLogin");

    return(
        <div className="dados">
            <p>{String(nome)}</p>
            <p>{String(login)}</p>
            <hr/>
        </div>
    )
}
export default DadosResumidoMenu