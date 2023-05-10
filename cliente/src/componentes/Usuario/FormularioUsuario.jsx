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
import { useParams } from "react-router-dom";
import ToastInfo from "../Toast/ToastInfo.jsx";
import { Endereco } from "../../models/Endereco.ts";

function addTell(setFone, fones, novoFone) {
    setFone([novoFone, ...fones]);
    console.log("ADD TEL", fones);
}

function checkPerfis(setIdsPerfis, idsPerfis, novoPerfil) {
    if (idsPerfis.indexOf(novoPerfil) === -1) {
        setIdsPerfis([novoPerfil, ...idsPerfis]);
        console.log("ACIONANDO PERFIL: ")
    } else {
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

    const [listaPerfis, setListaPerfis] = useState([]);
    const [loginExiste, setLoginExiste] = useState(false);

    const [usuarioDTO] = useState(new UsuarioDTO());
    const [endereco] = useState(new Endereco());
    usuarioDTO.setEndereco(endereco);

    if (idUsuario > 0) {

        console.log("PARAMETROS: ", idUsuario);

        useEffect(() => {

            ApiService.get(`/rest/usuario/${idUsuario}`).then((response) => {

                console.log("USUARIO LOCALIZADO: ", response)

                usuarioDTO.setId(response.data.id);
                usuarioDTO.setNome(response.data.nome);
                usuarioDTO.setAtivo(response.data.ativo);
                usuarioDTO.setLogin(response.data.login);
                usuarioDTO.setFone(response.data.fone);

                let idsPerfilRecuperado = [];
                for (let i = 0; i < response.data.perfis.length; i++) {
                    console.log("PERFIS RECUPERADOS: ", response.data.perfis[i].id, response.data.perfis[i].nome)
                    idsPerfilRecuperado.push(response.data.perfis[i].id);
                }
                usuarioDTO.setPerfis(idsPerfilRecuperado)

                usuarioDTO.endereco.setId(response.data.endereco.id);
                usuarioDTO.endereco.setBairro(response.data.endereco.bairro);
                usuarioDTO.endereco.setCep(response.data.endereco.cep);
                usuarioDTO.endereco.setComplemento(response.data.endereco.complemento);
                usuarioDTO.endereco.setDDD(response.data.endereco.ddd);
                usuarioDTO.endereco.setGia(response.data.endereco.gia);
                usuarioDTO.endereco.setIbge(response.data.endereco.ibge);
                usuarioDTO.endereco.setLocalidade(response.data.endereco.localidade);
                usuarioDTO.endereco.setLogradouro(response.data.endereco.logradouro);
                usuarioDTO.endereco.setNumero(response.data.endereco.numero);
                usuarioDTO.endereco.setUf(response.data.endereco.uf);

                console.log("RECUPERANDO USUARIO DTO: ", usuarioDTO);

                setShowToastInfo(true);
                setMensagemToast("Usuário localizado com sucesso")
            }).catch(error => {
                console.error("RECUPERANDO USUARIO", error)
                setShowToastError(true);
                setMensagemToast(error.response.data.mensagem)
                limpaForm();
            })

        }, []);


    }

    function buscarTodosPerfis(e) {
        e.preventDefault();

        ApiService.get("/rest/perfil").then(response => {
            setListaPerfis(response.data.content);
            console.log("TODOS OS PERFIS: ", response)
            console.log("LISTA PERFIL", listaPerfis);
        }).catch(error => {
            console.log("TODOS OS PERFIS: ", error)
        })
    }

    function limpaForm() {
        usuarioDTO = new UsuarioDTO();

        console.log("FORMULARIO LIMPO")
    }

    function buscaLogin(e) {
        e.preventDefault();
        console.log("BUSCA LOGIN: ", usuarioDTO.login)

        ApiService.get(`/rest/usuario/findlogin/${usuarioDTO.login}`).then((response) => {
            console.log(response)
            setLoginExiste(true);
            setShowToastWarning(true);
            setMensagemToast("Login já em uso")
        }).catch(error => {
            console.error(error)
        })
    }

    function buscaEndereco(cep) {
        
        if(cep.length < 9){return;}
    
        console.log("PESQUISA CEP:", cep);

        ApiService.get(`/rest/endereco/${cep}`).then(response => {

            usuarioDTO.endereco.setBairro(response.data.bairro);
            usuarioDTO.endereco.setCep(response.data.cep);
            usuarioDTO.endereco.setComplemento(response.data.complemento);
            usuarioDTO.endereco.setDDD(response.data.ddd);
            usuarioDTO.endereco.setGia(response.data.gia);
            usuarioDTO.endereco.setIbge(response.data.ibge);
            usuarioDTO.endereco.setLocalidade(response.data.localidade);
            usuarioDTO.endereco.setLogradouro(response.data.logradouro);
            usuarioDTO.endereco.setNumero(response.data.numero);
            usuarioDTO.endereco.setUf(response.data.uf);
 
            console.log("ENDERECO: ", usuarioDTO.endereco);
        }).catch(error => {
            console.error("ENDERECO: ", error)
        })

    }

    function salvarForm(e) {
        e.preventDefault();
        console.log("SALVAR USUARIO", usuarioDTO);

        ApiService.post("/rest/usuario", usuarioDTO).then(response => {
            console.log("FORMULARIO NOVO USUARIO: ", response);
            setShowToastSucess(true);
            setMensagemToast("Usuário cadastrado com sucesso!")
            limpaForm();
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

    return (
        <div className="border">
            <Form onSubmit={salvarForm} className="form-inline">

                <div className="row">

                    <Form.Group className="form-group col-md-5">
                        <Form.Label>Nome</Form.Label>
                        <Form.Control type="text" required value={usuarioDTO.nome} onChange={(e) => usuarioDTO.setNome(e.target.value)} />
                    </Form.Group>

                    <Form.Group className="form-group col-md-4">
                        <Form.Label className={loginExiste === true ? "cor_erro" : ""}>{loginExiste === true ? "usuário já existe" : "Login"}
                        </Form.Label>
                        <Form.Control type="email" required value={usuarioDTO.login} onChange={(e) => { usuarioDTO.setLogin(e.target.value); setLoginExiste(false) }} />
                    </Form.Group>

                    <Form.Group className="form-group col-md-3">
                        <Form.Label>Senha</Form.Label>
                        <Form.Control type="password" value={usuarioDTO.senha} onChange={(e) => usuarioDTO.setSenha(e.target.value)} />
                    </Form.Group>
                </div>

                <div class="row">
                    <Form.Group className="form-group col-md-2">
                        <Form.Label>CEP</Form.Label>
                        <Form.Control type="text" as={IMaskInput} mask="00000-000"  value={usuarioDTO.endereco.cep} onChange={(e) => {buscaEndereco(e.target.value)}} />
                    </Form.Group>

                    <Form.Group className="form-group col-md-4">
                        <Form.Label>Logradouro</Form.Label>
                        <Form.Control type="text" value={usuarioDTO.endereco.logradouro} onChange={(e) => {usuarioDTO.endereco.setLogradouro(e.target.value)}} />
                    </Form.Group>

                    <Form.Group className="form-group col-md-1">
                        <Form.Label>Nº</Form.Label>
                        <Form.Control type="text" value={usuarioDTO.endereco.numero} onChange={(e) => {usuarioDTO.endereco.setNumero(e.target.value)}} />
                    </Form.Group>

                    <Form.Group className="form-group col-md-5">
                        <Form.Label>Bairro</Form.Label>
                        <Form.Control type="text" value={usuarioDTO.endereco.bairro} onChange={(e) => {usuarioDTO.endereco.setBairro(e.target.value)}}/>
                    </Form.Group>
                </div>

                <div class="row">

                    <Form.Group className="form-group col-md-4">
                        <Form.Label>Município</Form.Label>
                        <Form.Control type="text"  value={usuarioDTO.endereco.localidade} onChange={(e) => {usuarioDTO.endereco.setLocalidade(e.target.value)}} />
                    </Form.Group>
                    
                    <Form.Group className="form-group col-md-2">
                        <Form.Label>Complemento</Form.Label>
                        <Form.Control type="text" value={usuarioDTO.endereco.complemento} onChange={(e) => {usuarioDTO.endereco.setComplemento(e.target.value)}} />
                    </Form.Group>

                    <Form.Group className="form-group col-md-1">
                        <Form.Label>UF</Form.Label>
                        <Form.Control type="text" value={usuarioDTO.endereco.uf} onChange={(e) => {usuarioDTO.endereco.setUf(e.target.value)}} />
                    </Form.Group>

                    <Form.Group className="form-group col-md-1">
                        <Form.Label>DDD</Form.Label>
                        <Form.Control type="text" value={usuarioDTO.endereco.ddd} disabled />
                    </Form.Group>

                    <Form.Group className="form-group col-md-2">
                        <Form.Label>IBGE</Form.Label>
                        <Form.Control type="text" value={usuarioDTO.endereco.ibge} disabled />
                    </Form.Group>

                    <Form.Group className="form-group col-md-2">
                        <Form.Label>GIA</Form.Label>
                        <Form.Control type="text" value={usuarioDTO.endereco.gia} disabled />
                    </Form.Group>
                </div>

                <div className="row">
                    <div className="mb-3" style={{width:"15%"}}>
                        Perfis:
                        {listaPerfis && (
                            listaPerfis.map((item) => {
                                return(
                                    <Form.Group className="form-group col-md-2" controlId="formBasicCheckbox">
                                        <Form.Check type="checkbox" id={item.id} defaultChecked={usuarioDTO.idsPerfis.indexOf(item.id) >= 0 ? true : false} label={item.nome} value={item.id} onClick={(e) =>{checkPerfis(usuarioDTO.setIdsPerfis, usuarioDTO.idsPerfis, e.target.value)}} />  
                                    </Form.Group>
                                )
                            })
                        )}
                    </div>

                    <div className="mb-3" style={{width:"12%"}}>
                    <Form.Group className="form-group col-md-2">
                        <Form.Label>Usuário ativo:</Form.Label>
                        <Form.Check  type="switch" id="custom-switch" checked={usuarioDTO.ativo} label={usuarioDTO.ativo === true ? "Ativo" : "Inativo"} value={true} onChange={(e) => {usuarioDTO.setAtivo(!usuarioDTO.ativo); console.log(e)}} />
                    </Form.Group>

                    </div>

                    <div className="form-group col-md-2">                        
                        <Form.Group className="mb-3">
                            <Form.Label>telefones:</Form.Label>
                            <Form.Control type="phone" as={IMaskInput} mask="(00) 00000-0000" 
                                // eslint-disable-next-line no-unused-expressions
                                onChange={(e) =>{console.log("QUANT: ", e.target.value.length);e.target.value.length === 15 ? addTell(usuarioDTO.setFone, usuarioDTO.fone, e.target.value):"";e.target.value.length === 15 ? e.target.value="" : ""}} />
                        </Form.Group>
                    </div>
                    <div className="form-group col-md-2" style={{width:"20%"}}>
                        {usuarioDTO.fone > 0 ? <Button variant="danger" size="sm" onClick={() => usuarioDTO.setFone([])} >Resetar</Button> : ""}
                        {usuarioDTO.fone && (
                            usuarioDTO.fone.map((item) => {
                                return(
                                    <p>
                                        {item}
                                    </p>
                                )
                            })
                        )}

                    </div>
                </div>

            </Form>

            <ToastError mensagem={mensagemToast} show={showToastError} setShow={setShowToastError} />
            <ToastSucess mensagem={mensagemToast} show={showToastSucess} setShow={setShowToastSucess} />
            <ToastWornig mensagem={mensagemToast} show={showToastWarning} setShow={setShowToastWarning} />
            <ToastInfo mensagem={mensagemToast} show={showToastInfo} setShow={setShowToastInfo} />
        </div>
    )
}
export default FormularioUsuario