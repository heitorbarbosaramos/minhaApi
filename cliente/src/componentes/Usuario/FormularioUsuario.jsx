/* eslint-disable no-unused-vars */
/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable no-const-assign */
/* eslint-disable react-hooks/rules-of-hooks */
import { useEffect, useState } from "react"
import { IMaskInput } from 'react-imask';
import { Form } from "react-bootstrap"
import ApiService from "../../Services/ApiService.ts"
import { UsuarioDTO } from "../../models/UsuarioDTO.ts"
import Button from 'react-bootstrap/Button';
import ToastError from "../Toast/ToastError.jsx";
import ToastSucess from "../Toast/ToastSucess.jsx";
import ToastWornig from "../Toast/ToastWarning.jsx";
import { Navigate, useParams } from "react-router-dom";
import ToastInfo from "../Toast/ToastInfo.jsx";
import { Endereco } from "../../models/Endereco.ts";

function addTell(setFone, fones, novoFone) {
    setFone([novoFone, ...fones]);
    console.log("ADD TEL", fones);
}

function checkPerfis(setIdsPerfis, idsPerfis, novoPerfil) {
    console.log("INDICE ", idsPerfis.indexOf(novoPerfil), " DO PERFIL: ", novoPerfil, " - ", idsPerfis)
    if (idsPerfis.indexOf(novoPerfil) === -1) {
        setIdsPerfis([novoPerfil, ...idsPerfis]);
        console.log("ACIONANDO PERFIL: ", idsPerfis)
    } else {
        idsPerfis.splice(novoPerfil);
        console.log("REMOVENDO PERFIL: ", idsPerfis)
    }
}

const FormularioUsuario = () => {

    const { idUsuario } = useParams();

    const [showToastError, setShowToastError] = useState(false);
    const [showToastSucess, setShowToastSucess] = useState(false);
    const [showToastWarning, setShowToastWarning] = useState(false);
    const [showToastInfo, setShowToastInfo] = useState(false);
    const [mensagemToast, setMensagemToast] = useState("Erro");

    const [listaPerfis, setListaPerfis] = useState([]);
    const [loginExiste, setLoginExiste] = useState(false);

    const [usuarioDTO, setUsuarioDto] = useState(new UsuarioDTO());
    const [endereco, setEndereco] = useState(new Endereco());

    const [idUser, setIdUser] = useState(null);
    const [nome, setNome] = useState("");
    const [login, setLogin] = useState("");
    const [senha, setSenha] = useState("");
    const [ativo, setAtivo] = useState(false);
    const [fone, setFone] = useState([]);
    const [idsPerfis, setIdsPerfis] = useState([]);

    const [idEnd, setIdEnd] = useState(null);
    const [cep, setCep] = useState("");
    const [logradouro, setLogradouro] = useState("");
    const [numero, setNumero] = useState("");
    const [bairro, setBairro] = useState("");
    const [localidade, setLocalidade] = useState("");
    const [complemento, setComplemento] = useState("");
    const [uf, setUf] = useState("");
    const [ddd, setDdd] = useState("");
    const [ibge, setIbge] = useState("");
    const [gia, setGia] = useState("");

    const eddRestrito = localStorage.getItem('myApiPerfis').indexOf('ROLE_USUARIO');


    if (idUsuario > 0) {

        console.log("PARAMETROS: ", idUsuario);

        useEffect(() => {

            ApiService.get(`/rest/usuario/${idUsuario}`).then((response) => {

                console.log("USUARIO LOCALIZADO: ", response)

                setIdUser(response.data.id);
                setNome(response.data.nome);
                setAtivo(response.data.ativo);
                setLogin(response.data.login);
                setFone(response.data.fone);

                for (let i = 0; i < response.data.perfis.length; i++) {
                    console.log("PERFIS RECUPERADOS: ", response.data.perfis[i].id, response.data.perfis[i].nome)
                    setIdsPerfis([response.data.perfis[i].id, ...idsPerfis]);
                }

                setIdEnd(response.data.endereco.id);
                setBairro(response.data.endereco.bairro);
                setCep(response.data.endereco.cep);
                setComplemento(response.data.endereco.complemento);
                setDdd(response.data.endereco.ddd);
                setGia(response.data.endereco.gia);
                setIbge(response.data.endereco.ibge);
                setLocalidade(response.data.endereco.localidade);
                setLogradouro(response.data.endereco.logradouro);
                setNumero(response.data.endereco.numero);
                setUf(response.data.endereco.uf);

                console.log("RECUPERANDO USUARIO DTO: ", usuarioDTO);

                setShowToastInfo(true);
                setMensagemToast("Usuário localizado com sucesso")
            }).catch(error => {
                console.error("RECUPERANDO USUARIO", error)
                setShowToastError(true);
                setMensagemToast(error.response.data.mensagem)
                limpaForm();
            })

        }, [idUsuario, usuarioDTO]);


    }

    function buscarTodosPerfis() {
    
        useEffect(() => {
            ApiService.get("/rest/perfil").then(response => {
                setListaPerfis(response.data.content);
                console.log("TODOS OS PERFIS: ", response.data.content)
                console.log("LISTA PERFIL", listaPerfis);
            }).catch(error => {
                console.log("TODOS OS PERFIS: ", error)
            })
        },[])
    }

    function limpaForm() {
        console.log("FORMULARIO LIMPANDO")
        setIdUser(null);
        setUsuarioDto();
        setNome("");
        setLogin("");
        setSenha("");
        setAtivo(false);
        setFone([]);
        setIdsPerfis([]);
        setIdEnd(null);
        setCep("");
        setLogradouro("");
        setNumero("");
        setBairro("");
        setLocalidade("");
        setComplemento("");
        setUf("");
        setDdd("");
        setIbge("");
        setGia("");


        console.log("FORMULARIO LIMPO")
        return <Navigate to="formularioIsuario/0" />
    }

    function buscaLogin(e) {

        console.log("BUSCA LOGIN: ", e)

        ApiService.get(`/rest/usuario/findlogin/${e}`).then((response) => {
            console.log(response)
            setLoginExiste(true);
            setShowToastWarning(true);
            setMensagemToast("Login já em uso")
        }).catch(error => {
            setLoginExiste(false);
            console.error(error)
        })
    }

    function buscaEndereco(cep) {

        setCep(cep);
        
        if(cep.length < 9){return;}
    
        console.log("PESQUISA CEP:", cep);

        ApiService.get(`/rest/endereco/${cep}`).then(response => {

            if(cep.lengt < 9){return}

            setBairro(response.data.bairro);
            setCep(response.data.cep);
            setComplemento(response.data.complemento);
            setDdd(response.data.ddd);
            setGia(response.data.gia);
            setIbge(response.data.ibge);
            setLocalidade(response.data.localidade);
            setLogradouro(response.data.logradouro);
            setNumero(response.data.numero);
            setUf(response.data.uf);
 
            console.log("ENDERECO: ", response.data);
        }).catch(error => {
            console.error("ENDERECO: ", error)
        })

    }

    function salvarForm(e) {

        e.preventDefault();

        usuarioDTO.setId(idUser);
        usuarioDTO.setLogin(login);
        usuarioDTO.setSenha(senha);
        usuarioDTO.setNome(nome);
        usuarioDTO.setAtivo(ativo);
        usuarioDTO.setFone(fone);
        usuarioDTO.setPerfis(idsPerfis);
      
        endereco.setId(idEnd);
        endereco.setCep(cep);
        endereco.setLogradouro(logradouro);
        endereco.setNumero(numero);
        endereco.setComplemento(complemento);
        endereco.setBairro(bairro);
        endereco.setLocalidade(localidade);
        endereco.setUf(uf);
        endereco.setIbge(ibge);
        endereco.setDDD(ddd);
        endereco.setGia(gia);

        usuarioDTO.setEndereco(endereco);

        console.log("SALVAR USUARIO", usuarioDTO);

        ApiService.post("/rest/usuario", usuarioDTO).then(response => {
            console.log("FORMULARIO NOVO USUARIO: ", response);
            setShowToastSucess(true);
            setMensagemToast("Usuário cadastrado com sucesso!")
        }).catch(error => {
            console.error("FORMULARIO NOVO USUARIO: ", error);
            setShowToastError(true);
            setMensagemToast(error.response.data.mensagem);
            if (error.response.data.erros) {
                let mensagem = error.response.data.mensagem;
                for (let i = 0; i < error.response.data.erros.length; i++) {
                    mensagem += ` \n\n ${error.response.data.erros[i].campo} ${error.response.data.erros[i].erro} `
                }

                setMensagemToast(mensagem)
                console.log("ERRO DE VALIDACOES: ", error.response.data.erros)
            }
            console.log("SHOW TOAST: ", showToastError);
        })

    }
    
    buscarTodosPerfis();

    console.log("idsPerfilRecuperado", idsPerfis)

    return (
        <div className="border">
            <Form onSubmit={salvarForm} className="form-inline">

                <div className="row mt-3">

                    <Form.Group className="form-group col-md-5">
                        <Form.Label>Nome</Form.Label>
                        <Form.Control type="text" required value={nome} onChange={(e) => {setNome(e.target.value); console.log(usuarioDTO)}} />
                    </Form.Group>

                    <Form.Group className="form-group col-md-4">
                        <Form.Label className={loginExiste === true ? "cor_erro" : ""}>{loginExiste === true ? "usuário já existe" : "Login"}
                        </Form.Label>
                        <Form.Control type="email" required value={login} onChange={(e) => {setLogin(e.target.value); buscaLogin(e.target.value) }} />
                    </Form.Group>

                    <Form.Group className="form-group col-md-3">
                        <Form.Label>Senha</Form.Label>
                        <Form.Control type="password" value={senha} onChange={(e) => setSenha(e.target.value)} />
                    </Form.Group>
                </div>

                <div class="row mt-3">
                    <Form.Group className="form-group col-md-2">
                        <Form.Label>CEP</Form.Label>
                        <Form.Control type="text" as={IMaskInput} mask="00000-000"  value={cep} onChange={(e) => {buscaEndereco(e.target.value)}} />
                    </Form.Group>

                    <Form.Group className="form-group col-md-4">
                        <Form.Label>Logradouro</Form.Label>
                        <Form.Control type="text" value={logradouro} onChange={(e) => {setLogradouro(e.target.value)}} />
                    </Form.Group>

                    <Form.Group className="form-group col-md-1">
                        <Form.Label>Nº</Form.Label>
                        <Form.Control type="text" value={numero} onChange={(e) => {setNumero(e.target.value)}} />
                    </Form.Group>

                    <Form.Group className="form-group col-md-5">
                        <Form.Label>Bairro</Form.Label>
                        <Form.Control type="text" value={bairro} onChange={(e) => {setBairro(e.target.value)}}/>
                    </Form.Group>
                </div>

                <div class="row mt-3">

                    <Form.Group className="form-group col-md-4">
                        <Form.Label>Município</Form.Label>
                        <Form.Control type="text"  value={localidade} onChange={(e) => {setLocalidade(e.target.value)}} />
                    </Form.Group>
                    
                    <Form.Group className="form-group col-md-2">
                        <Form.Label>Complemento</Form.Label>
                        <Form.Control type="text" value={complemento} onChange={(e) => {setComplemento(e.target.value)}} />
                    </Form.Group>

                    <Form.Group className="form-group col-md-1">
                        <Form.Label>UF</Form.Label>
                        <Form.Control type="text" value={uf} onChange={(e) => {setUf(e.target.value)}} />
                    </Form.Group>

                    <Form.Group className="form-group col-md-1">
                        <Form.Label>DDD</Form.Label>
                        <Form.Control type="text" value={ddd} disabled />
                    </Form.Group>

                    <Form.Group className="form-group col-md-2">
                        <Form.Label>IBGE</Form.Label>
                        <Form.Control type="text" value={ibge} disabled />
                    </Form.Group>

                    <Form.Group className="form-group col-md-2">
                        <Form.Label>GIA</Form.Label>
                        <Form.Control type="text" value={gia} disabled />
                    </Form.Group>
                </div>

                <div className="row mt-3">
                    <div className="mb-3" style={{width:"15%"}}>
                        
                        {listaPerfis && (
                            listaPerfis.map((item) => {
                                return(
                                    <Form.Group className="form-group col-md-2" controlId="formBasicCheckbox">
                                        <Form.Check type="checkbox" disabled={eddRestrito === -1 ? true:false} id={item.id} defaultChecked={idsPerfis.indexOf(item.id) !== -1 ? true:false} label={item.nome} value={item.id} onClick={(e) =>{checkPerfis(setIdsPerfis, idsPerfis, e.target.value)}} />  
                                    </Form.Group>
                                )
                            })
                        )}
                    </div>

                    <div className="mb-3" style={{width:"12%"}}>
                    <Form.Group className="form-group col-md-2">
                        <Form.Label>Usuário ativo:</Form.Label>
                        <Form.Check  type="switch" id="custom-switch" disabled={eddRestrito === -1 ? true:false} checked={ativo} label={ativo === true ? "Ativo" : "Inativo"} value={true} onChange={(e) => {setAtivo(!ativo); console.log(e.target.select)}} />
                    </Form.Group>

                    </div>

                    <div className="form-group col-md-2">                        
                        <Form.Group className="mb-3">
                            <Form.Label>telefones:</Form.Label>
                            <Form.Control type="phone" as={IMaskInput} mask="(00) 00000-0000" 
                                // eslint-disable-next-line no-unused-expressions
                                onChange={(e) =>{console.log("QUANT: ", e.target.value.length);e.target.value.length === 15 ? addTell(setFone, fone, e.target.value):"";e.target.value.length === 15 ? e.target.value="" : ""}} />
                        </Form.Group>
                    </div>
                    <div className="form-group col-md-2" style={{width:"20%"}}>
                        {fone.length > 0 ? <Button variant="danger" size="sm" onClick={() => setFone([])} >Resetar</Button> : ""}
                        {fone && (
                            fone.map((item) => {
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