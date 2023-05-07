/* eslint-disable react-hooks/rules-of-hooks */
import { useEffect, useState } from "react"
import { IMaskInput } from 'react-imask';
import { Form } from "react-bootstrap"
import ApiService from "../../Services/ApiService.ts"
import { UsuarioDTO } from "../../models/UsuarioDTO.ts"
import Button from 'react-bootstrap/Button';
import { Endereco } from "../../models/Endereco.ts";
import ToastError from "../Toast/ToastError.jsx";
import ToastSucess from "../Toast/ToastSucess.jsx";
import ToastWornig from "../Toast/ToastWarning.jsx";
import { useParams } from "react-router-dom";
import ToastInfo from "../Toast/ToastInfo.jsx";

function addTell(setFone, fones, novoFone){
    setFone([novoFone, ...fones]);
    console.log("ADD TEL", fones);
}

function checkPerfis(setIdsPerfis, idsPerfis, novoPerfil){
    if(idsPerfis.indexOf(novoPerfil) === -1){
        setIdsPerfis([novoPerfil, ...idsPerfis]);
        console.log("ACIONANDO PERFIL: ")
    }else{
        idsPerfis.splice(novoPerfil);
        console.log("REMOVENDO PERFIL: ")
    }    
    console.log("EDD PERFIL: ", idsPerfis);
}

const FormularioUsuario = () => {

    const { idUsuario } = useParams();

    const [showToastError, setShowToastError] = useState(false);
    const [showToastSucess, setShowToastSucess] = useState(false);
    const [showToastWarning, setShowToastWarning] = useState(false);
    const [showToastInfo, setShowToastInfo] = useState(false);
    const [mensagemToast, setMensagemToast] = useState("Erro");

    const [usuarioDTO, setUsuarioDTO] = useState(new UsuarioDTO());
    const [endereco, setEndereco] = useState(new Endereco());
   
    console.log("CONFERE USUARIO DTO: ", usuarioDTO);


    const [ativo, setAtivo] = useState(true);
    const [idUser, setIdUser] = useState(null);
    const [nome, setNome] = useState(null);
    const [login, setLogin] = useState("");
    const [senha, setSenha] = useState("");
    const [fones, setFone] = useState([]);

    const [listaPerfis, setListaPerfis] = useState([]);
    const [idsPerfis, setIdsPerfis] = useState([]);

    const [idEndereco, setIdEndereco] = useState(null);
    const [cep, setCep] = useState("");
    const [logradouro, setLogradouro] = useState("");
    const [numero, setNumero] = useState("");
    const [localidade, setLocalidade] = useState("")
    const [bairro, setBairro] = useState("");
    const [complemento, setComplemento] = useState("");
    const [uf, setUf] = useState("");
    const [ddd, setDdd] = useState("");
    const [ibge, setIbge] = useState("");
    const [gia, setGia] = useState("");

    const [loingExiste, setLoginExiste] = useState(false);

    if(idUsuario > 0){

        console.log("PARAMETROS: ", idUsuario);
  
        useEffect(() => {

            ApiService.get(`/rest/usuario/${idUsuario}`).then((response) =>{
                
                console.log("USUARIO LOCALIZADO: ", response)

                setIdUser(response.data.id);
                setNome(response.data.nome);
                setAtivo(response.data.ativo);
                setLogin(response.data.login);
                setFone(response.data.fone);

                let idsPerfilRecuperado = [];
                for(let i = 0; i<response.data.perfis.length; i++){
                    console.log("PERFIS RECUPERADOS: ", response.data.perfis[i].id, response.data.perfis[i].nome)
                    idsPerfilRecuperado.push(response.data.perfis[i].id);
                }
                setIdsPerfis(idsPerfilRecuperado);

                setIdEndereco(response.data.endereco.id);
                setBairro(response.data.endereco.bairro);
                setCep(response.data.endereco.cep);
                setComplemento(response.data.endereco.complemento);
                setEndereco(response.data.endereco.ddd);
                setGia(response.data.endereco.gia);
                setIbge(response.data.endereco.ibge);
                setLocalidade(response.data.endereco.localidade);
                setLogradouro(response.data.endereco.logradouro);
                setNumero(response.data.endereco.numero);
                setUf(response.data.endereco.uf);
                
                setShowToastInfo(true);
                setMensagemToast("Usuário localizado com sucesso")
            }).catch(error => {
                console.log(error)
                setShowToastError(true);
                setMensagemToast(error.response.data.mensagem)
                limpaForm();
            })

        },[idUsuario]);
       

    }

    function limpaForm(){
        setUsuarioDTO(new UsuarioDTO());
        setEndereco(new Endereco());
        setAtivo(true);
        setIdUser(null);
        setNome("");
        setLogin("");
        setSenha("");
        setFone([]);

        setIdsPerfis([]);

        setIdEndereco(null);
        setCep("");
        setLogradouro("");
        setNumero("");
        setLocalidade("");
        setBairro("");
        setComplemento("");
        setUf("");
        setDdd("");
        setIbge("");
        setGia("");

        console.log("FORMULARIO LIMPO")

    }

    function salvarForm(e){
        e.preventDefault();
        console.log("SALVAR USUARIO", idEndereco, cep);

        endereco.id = idEndereco;
        endereco.cep = cep;
        endereco.logradouro = logradouro;
        endereco.complemento = complemento;
        endereco.numero = numero;
        endereco.bairro = bairro;
        endereco.localidade = localidade;
        endereco.uf = uf;
        endereco.ddd = ddd;
        endereco.ibge = ibge;
        endereco.gia = gia;

        usuarioDTO.id = parseInt(idUser);
        usuarioDTO.login = login;
        usuarioDTO.nome = nome;
        usuarioDTO.senha = senha;
        usuarioDTO.fone = fones;
        usuarioDTO.ativo = ativo;
        usuarioDTO.idsPerfis = idsPerfis;

        usuarioDTO.endereco = endereco;

        console.log("USUARIO DTO: ", usuarioDTO);

        ApiService.post("/rest/usuario", usuarioDTO).then(response => {
            console.log("FORMULARIO NOVO USUARIO: ", response);
            setShowToastSucess(true);
            setMensagemToast("Usuário cadastrado com sucesso!")
            limpaForm();
        }).catch(error => {
            console.error("FORMULARIO NOVO USUARIO: ", error);
            setShowToastError(true);
            setMensagemToast(error.response.data.mensagem);      
            if(error.response.data.erros){
                let mensagem = error.response.data.mensagem;
                for(let i = 0; i<error.response.data.erros.length;i++){
                    mensagem += ` \n\n ${error.response.data.erros[i].campo} ${error.response.data.erros[i].erro} `
                } 
                
                setMensagemToast(mensagem)
                console.log("ERRO DE VALIDACOES: ", error.response.data.erros)
            }
            console.log("SHOW TOAST: ", showToastError);     
        })
        
    }

    useEffect(() => {

        ApiService.get("/rest/perfil").then(response => {
            setListaPerfis(response.data.content);
            console.log("TODOS OS PERFIS: ", response)
            console.log("LISTA PERFIL", listaPerfis);
        }).catch(error => {
            console.log("TODOS OS PERFIS: ", error)
        })

    }, [])
    
    useEffect(() => {
        ApiService.get(`/rest/endereco/${cep}`).then(response => {
            setLogradouro(response.data.logradouro);
            setLocalidade(response.data.localidade);
            setBairro(response.data.bairro);
            setUf(response.data.uf);
            setDdd(response.data.ddd);
            setIbge(response.data.ibge);
            setGia(response.data.gia);

            console.log("ENDERECO: ", response);
        }).catch(error => {
            console.error("ENDERECO: ", error)
        })
    }, [cep])


    useEffect(() => {
        ApiService.get(`/rest/usuario/findlogin/${login}`).then((response) =>{
            setLoginExiste(true);
            setShowToastWarning(true);
            setMensagemToast("Login já em uso")
        }).catch(error =>{
            console.error(error)
        })
    },[login]);

    return (
        <div className="border">

            <Form autoComplete="false" onSubmit={salvarForm}>
                
                <div className="row">
                    <Form.Group className="mb-3" style={{width:"50%"}}>
                        <Form.Label>Nome</Form.Label>
                        <Form.Control type="text" required value={nome} onChange={(e) => setNome(e.target.value)} />
                    </Form.Group>

                    <Form.Group className="mb-3" style={{width:"25%"}}>
                        <Form.Label className={loingExiste === true ? "cor_erro" : ""}>{loingExiste === true ? "usuário já existe" : "Login"}
                        </Form.Label>
                        <Form.Control type="email" required value={login}  onChange={(e) => {setLogin(e.target.value); setLoginExiste(false); console.log(login)}} />
                    </Form.Group>

                    <Form.Group className="mb-3" style={{width:"25%"}}>
                        <Form.Label>Senha</Form.Label>
                        <Form.Control type="password" required value={senha} onChange={(e) => setSenha(e.target.value)}/>
                    </Form.Group>
                </div>

                <div class="row">
                    <Form.Group className="mb-3" style={{width:"20%"}}>
                        <Form.Label>CEP</Form.Label>
                        <Form.Control type="text" as={IMaskInput} mask="00000-000"  value={cep} onChange={(e) => {setCep(e.target.value)}} />
                    </Form.Group>

                    <Form.Group className="mb-3" style={{width:"33%"}}>
                        <Form.Label>Logradouro</Form.Label>
                        <Form.Control type="text" value={logradouro} onChange={(e) => {setLogradouro(e.target.value)}} />
                    </Form.Group>

                    <Form.Group className="mb-3" style={{width:"10%"}}>
                        <Form.Label>Nº</Form.Label>
                        <Form.Control type="text" value={numero} onChange={(e) => {setNumero(e.target.value)}} />
                    </Form.Group>

                    <Form.Group className="mb-3" style={{width:"37%"}}>
                        <Form.Label>Bairro</Form.Label>
                        <Form.Control type="text" value={bairro} onChange={(e) => {setBairro(e.target.value)}}/>
                    </Form.Group>

                    
                </div>

                <div class="row">

                    <Form.Group className="mb-3" style={{width:"30%"}}>
                        <Form.Label>Município</Form.Label>
                        <Form.Control type="text"  value={localidade} onChange={(e) => {setLocalidade(e.target.value)}} />
                    </Form.Group>
                    
                    <Form.Group className="mb-3" style={{width:"30%"}}>
                        <Form.Label>Complemento</Form.Label>
                        <Form.Control type="text" value={complemento} onChange={(e) => {setComplemento(e.target.value)}} />
                    </Form.Group>

                    <Form.Group className="mb-3" style={{width:"10%"}}>
                        <Form.Label>UF</Form.Label>
                        <Form.Control type="text" value={uf} onChange={(e) => {setUf(e.target.value)}} />
                    </Form.Group>

                    <Form.Group className="mb-3" style={{width:"10%"}}>
                        <Form.Label>DDD</Form.Label>
                        <Form.Control type="text" value={ddd} disabled />
                    </Form.Group>

                    <Form.Group className="mb-3" style={{width:"10%"}}>
                        <Form.Label>IBGE</Form.Label>
                        <Form.Control type="text" value={ibge} disabled />
                    </Form.Group>

                    <Form.Group className="mb-3" style={{width:"10%"}}>
                        <Form.Label>GIA</Form.Label>
                        <Form.Control type="text" value={gia} disabled />
                    </Form.Group>

                </div>

                <div className="row">
                    <div className="mb-3" style={{width:"15%"}}>
                        Perfis:
                        {listaPerfis && (
                            listaPerfis.map((item) => {
                                return(
                                    <Form.Group className="mb-3" controlId="formBasicCheckbox">
                                        <Form.Check type="checkbox" checked={idsPerfis.indexOf(item.id) >= 0 ? true : false} label={item.nome} value={item.id} onClick={(e) =>{checkPerfis(setIdsPerfis, idsPerfis, e.target.value)}} />  
                                    </Form.Group>
                                )
                            })
                        )}
                    </div>

                    <div className="mb-3" style={{width:"12%"}}>
                    <Form.Group className="mb-3">
                        <Form.Label>Usuário ativo:</Form.Label>
                        <Form.Check  type="switch" id="custom-switch" checked={ativo} label={ativo === true ? "Ativo" : "Inativo"} value={true} onChange={(e) => {setAtivo(!ativo); console.log(e)}} />
                    </Form.Group>

                    </div>

                    <div className="mb-3" style={{width:"16%"}}>                        
                        <Form.Group className="mb-3">
                            <Form.Label>telefones:</Form.Label>
                            <Form.Control type="phone" as={IMaskInput} mask="(00) 00000-0000" 
                                // eslint-disable-next-line no-unused-expressions
                                onChange={(e) =>{console.log("QUANT: ", e.target.value.length);e.target.value.length === 15 ? addTell(setFone, fones, e.target.value):"";e.target.value.length === 15 ? e.target.value="" : ""}} />
                        </Form.Group>
                    </div>
                    <div className="mb-3" style={{width:"20%"}}>
                        {fones.length > 0 ? <Button variant="danger" size="sm" onClick={() => setFone([])} >Resetar</Button> : ""}
                        {fones && (
                            fones.map((item) => {
                                return(
                                    <p>
                                        {item}
                                    </p>
                                )
                            })
                        )}

                    </div>
                </div>

               <Button type="submit">SALVAR</Button>
               <Button variant="warning" onClick={()=>limpaForm()} style={{marginLeft:"10px"}}>RESETAR</Button>


            </Form>
         <ToastError mensagem={mensagemToast} show={showToastError} setShow={setShowToastError} />
         <ToastSucess mensagem={mensagemToast} show={showToastSucess} setShow={setShowToastSucess} />
         <ToastWornig mensagem={mensagemToast} show={showToastWarning} setShow={setShowToastWarning} />
         <ToastInfo mensagem={mensagemToast} show={showToastInfo} setShow={setShowToastInfo} />
        </div>
    )
}
export default FormularioUsuario