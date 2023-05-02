import { useEffect, useState } from "react";
import "./DadosSistema.css";
import ApiService from "../../Services/ApiService.ts";

const DadosSistema = () =>{

    const [nomeApi, setNomeApi] = useState("")
    const [versao, setVersao] = useState("");

    useEffect(() => {
        
        ApiService.get("/rest/actuator/info").then(response => {
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