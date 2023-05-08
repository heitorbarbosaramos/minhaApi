import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import './Login.css';
import { Card, Spinner } from 'react-bootstrap';
import { useContext, useState } from 'react';
import { AuthContext } from '../../Services/Auth.jsx';
import DadosSistema from '../../componentes/DadosSistema/DadosSistema';


const Login = () => {

    const { login, recoveryPassword, recuperaSenha } = useContext(AuthContext);

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

    return (
        <div className='login'>


            <Card style={{ width: '28rem' }}>

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
        </div>
    )
}
export default Login;