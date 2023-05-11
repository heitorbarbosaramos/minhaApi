import { Link, useParams } from 'react-router-dom';
import './Login.css'
import { Button, Card, Form } from 'react-bootstrap';
import ApiService from '../../Services/ApiService.ts';
import { useEffect, useState } from 'react';
import { UsuarioResetaSenhaDTO } from '../../models/UsuarioResetaSenhaDTO.ts';
import ToastError from '../../componentes/Toast/ToastError';
import ToastSucess from '../../componentes/Toast/ToastSucess';
const EsqueciASenha = () =>{

    const { idUsuario } = useParams();
    const { timestamp } = useParams();
    const [usuarioRecuperaSenha] = useState(new UsuarioResetaSenhaDTO());
    const [nome, setNome] = useState("");

    const [toastErroShow, setToastErroShow] = useState(false);
    const [toastSucessShow, setToastSucessShow] = useState(false);
    const [toastMensagem, setToastMensagem] = useState();
    const [senha, setSenha] = useState("");
    const [login, setLogin] = useState("");

    usuarioRecuperaSenha.id = idUsuario;
    usuarioRecuperaSenha.timestampRecuperaSenha = timestamp;

    useEffect(() => {

        ApiService.post("/rest/usuario/recuperasenhaetapa1", usuarioRecuperaSenha).then(response => {
            usuarioRecuperaSenha.id = response.data.id;
            usuarioRecuperaSenha.nome = response.data.nome;
            setNome(response.data.nome);
            setLogin(response.data.login);
            usuarioRecuperaSenha.login = response.data.login;
            usuarioRecuperaSenha.timestampRecuperaSenha = response.data.timestampRecuperaSenha;

            console.log("RECUPERA SENHA ETAPA 1", usuarioRecuperaSenha);
        }).catch(error => {
            console.error("RECUPERA SENHA ETAPA 1: ", error);
            setToastMensagem(error.response.data.mensagem);
            setToastErroShow(true);
        })

    }, [usuarioRecuperaSenha])

    const resetaSenha = (e) => {

        e.preventDefault();

        usuarioRecuperaSenha.senha = senha;
        console.log("RESETA A SENHA:", usuarioRecuperaSenha);

        ApiService.post("/rest/usuario/recuperasenhaetapa2", usuarioRecuperaSenha).then(response => {
            console.log("RESETA SENHA: ", response);
            setToastSucessShow(true);
            setToastMensagem("Senha alterada.")
        }).catch(error => {
            console.error(error);
            setToastErroShow(true);
            setToastMensagem("Erro ao alterar a senha")
        })
    }

    return(
        <div className='login'>

            <Card style={{ width: '28rem'}}>

                <Card.Header as="h5">Recupera senha do Sistema</Card.Header>
                <Card.Title>Bem vindo a cental de recuperação de senha</Card.Title>

                <Card.Body>

                {toastMensagem !== undefined && (
                    <Card.Text className='cor_erro'>
                        <p>
                            {String(toastMensagem)}
                        </p>
                        <Link to="/" style={{marginLeft:"42%"}}>
                            <Button variant="primary" type="submit">
                                Login
                            </Button>
                        </Link>
                    </Card.Text>
                )}

                {toastMensagem === undefined &&(
                    <>
                        <Card.Text>
                            Prezado(a) <i>{nome}</i>, foi solicitado uma troca de senha para o login <i>{login}</i>, favor preencha os campos abaixo para proseguir.
                        </Card.Text>

                        <Form onSubmit={resetaSenha} style={{ padding: "10px" }}>

                            <Form.Group className="mb-3" controlId="formBasicPassword">
                                <Form.Label>Senha</Form.Label>
                                <Form.Control type="password" placeholder="Entre com sua senha" value={senha} onChange={(e) => setSenha(e.target.value)} required/>
                            </Form.Group>

                            <Button variant="primary" type="submit"> 
                                Resetar
                            </Button>

                        </Form>
                    </>
                )}

                </Card.Body>

                
                

            </Card>
            Esqueci a senha
            {String(idUsuario)} - 
            {String(timestamp)}

            <ToastError show={toastErroShow} setShow={setToastErroShow} mensagem={toastMensagem}/>
            <ToastSucess show={toastSucessShow} setShow={setToastSucessShow} mensagem={toastMensagem}/>
        </div>
    )
}
export default EsqueciASenha