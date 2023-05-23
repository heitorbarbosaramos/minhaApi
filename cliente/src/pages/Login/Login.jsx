import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import './Login.css';
import { Card, Image, Spinner } from 'react-bootstrap';
import { useContext, useState } from 'react';
import { AuthContext } from '../../Services/Auth.jsx';
import DadosSistema from '../../componentes/DadosSistema/DadosSistema';


const Login = () => {

    const { login, recoveryPassword, recuperaSenha, loginSocialGoogle } = useContext(AuthContext);

    const [email, setEmail] = useState("");
    const [senha, setSenha] = useState("");
    const [spinner, setSpinner] = useState(false);

    const loginSubmit = (e) => {

        e.preventDefault();

        console.log("REALIZANDO LOGIN", {email, senha})

        login(email, senha); //INTEGRACAO COM O CONTEXTO

    }

    const geraLinkRecuperaSenha = (e) => {

        e.preventDefault();

        setSpinner(true);
        recuperaSenha(email, setSpinner);
    }

    const loginGoogle = (e) => {

        loginSocialGoogle();
    }

    return (
        <div className='login' style={{display:"flex", flexDirection:"row"}}>


            <Card style={{ width: '28rem', height:'360px' }}>

                <Card.Body style={{ padding: 0 }}>

                    <Card.Header as="h5">Login Sistema</Card.Header>
                    <Card.Title>Bem vindo</Card.Title>

                    <Form onSubmit={loginSubmit} style={{ padding: "10px" }}>

                        <DadosSistema />

                        <Form.Group className="mb-3" controlId="formBasicEmail">
                            <Form.Label>Login</Form.Label>
                            <Form.Control type="email" placeholder="Entre com seu login" value={email} onChange={(e) => setEmail(e.target.value)} />
                        </Form.Group>

                        <Form.Group className="mb-3" controlId="formBasicPassword">
                            <Form.Label>Senha</Form.Label>
                            <Form.Control type="password" placeholder="Entre com sua senha" value={senha} onChange={(e) => setSenha(e.target.value)} />
                        </Form.Group>

                        <Button variant="primary" type="submit">
                            Login
                        </Button>
                        {recoveryPassword === true &&(
                            <Button variant="secondary" disable={spinner ? false:true} onClick={geraLinkRecuperaSenha} style={{marginLeft:"10px"}}>
                                Esqueceu a senha?
                                {spinner === true && (
                                    <Spinner size="sm" />
                                )}
                            </Button>
                        )}
                    </Form>
                </Card.Body>

            </Card>
            <Card style={{ width: '15rem', height:'360px'  }}>

                <Card.Body style={{ padding: 0 }}>

                    <Card.Header as="h5">Login social</Card.Header>
                    <p style={{marginTop:"40px", marginBottom:"40px"}}>Use suas redes sociais para fazer login no sistema</p>
                    
                    <Button  variant="outline-secondary" className='loginSocial' onClick={() => loginGoogle()}> Google</Button>
                    <Button  variant="outline-secondary" className='loginSocial'> Facebook</Button>
                    <Button  variant="outline-secondary" className='loginSocial'> GitHub</Button>

                </Card.Body>

            </Card>
        </div>
    )
}
export default Login;