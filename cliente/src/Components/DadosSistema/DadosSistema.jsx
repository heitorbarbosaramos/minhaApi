import { useEffect, useState } from "react";
import Api from "../../services/Api.ts";
import "./DadosSistema.css";

const DadosSistema = () =>{

    const [nomeApi, setNomeApi] = useState("")
    const [versao, setVersao] = useState("");

    useEffect(() => {
        
        Api.get("/rest/actuator/info").then(response => {
            console.log(response);
            setNomeApi(response.data.app.name);
            setVersao(response.data.app.version);
        }).catch(error => {
            console.error(error);
        })

    }, [nomeApi, versao])

   

    return(
        <div className="dadosSistema">
            <p>{String(nomeApi)}</p>
            <p>Versao: {String(versao)}</p>
        </div>
    )
}
export default DadosSistema