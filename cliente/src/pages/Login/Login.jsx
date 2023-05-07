import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import './Login.css';
import { Card } from 'react-bootstrap';
import { useContext, useState } from 'react';
import { AuthContext } from '../../Services/Auth.jsx';
import DadosSistema from '../../componentes/DadosSistema/DadosSistema';


const Login = () => {

    const { login } = useContext(AuthContext);

    const [email, setEmail] = useState("");
    const [senha, setSenha] = useState("");

    const loginSubmit = (e) => {

        e.preventDefault();

        console.log("REALIZANDO LOGIN", {email, senha})

        login(email, senha); //INTEGRACAO COM O CONTEXTO

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
                    </Form>
                </Card.Body>

            </Card>
        </div>
    )
}
export default Login;