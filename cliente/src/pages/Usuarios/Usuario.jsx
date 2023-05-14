import { useEffect, useState } from "react";
import ApiService from "../../Services/ApiService.ts"
import Card from 'react-bootstrap/Card';
import ListGroup from 'react-bootstrap/ListGroup';

const Usuario = () => {

    const [detalhamento, setDetalhamento] = useState([]);

    useEffect(() => {
        ApiService.get("/rest/usuario/detalhamento", {}).then(response => {
            console.log(response);
            setDetalhamento(response.data);
        }).catch(error => {
            console.error(error);
        })

    }, [])

    console.log("DETALHAMENTO: ", detalhamento);

    return (
        <div className="border">
            Dados Gerais sobre os usuários

            <div style={{display:"flex", flexWrap:"wrap", margin:"10px"}}>
                <Card style={{ width: '10rem', maxHeight:'8rem'}} className="margin_10px">
                    <Card.Header><Card.Title>Total usuários</Card.Title></Card.Header>
                    <Card.Body>

                        <Card.Subtitle className="mb-2 text-muted">Total usuários: {String(detalhamento.quantUsuario)}</Card.Subtitle>

                    </Card.Body>
                    <Card.Footer className="text-muted"></Card.Footer>
                </Card>

                <Card style={{ width: '10rem', maxHeight:'8rem' }} className="margin_10px">
                    <Card.Header><Card.Title>Ativos</Card.Title></Card.Header>
                    <Card.Body>

                        <Card.Subtitle className="mb-2 text-muted">Ativos: {String(detalhamento.quantUsuarioAtivo)}</Card.Subtitle>

                    </Card.Body>
                    <Card.Footer className="text-muted"></Card.Footer>
                </Card>

                <Card style={{ width: '10rem', maxHeight:'8rem' }} className="margin_10px">
                    <Card.Header><Card.Title>Inativos</Card.Title></Card.Header>
                    <Card.Body>

                        <Card.Subtitle className="mb-2 text-muted">Ativos: {String(detalhamento.quantUsuarioInativo)}</Card.Subtitle>

                    </Card.Body>
                    <Card.Footer className="text-muted"></Card.Footer>
                </Card>

                <Card style={{ width: '10rem', maxHeight:'8rem' }} className="margin_10px">
                    <Card.Header><Card.Title>Res. senha</Card.Title></Card.Header>
                    <Card.Body>

                        <Card.Subtitle className="mb-2 text-muted">reseta: {String(detalhamento.quantUsuarioSenhaResta)}</Card.Subtitle>

                    </Card.Body>
                    <Card.Footer className="text-muted"></Card.Footer>
                </Card>

                <Card style={{ width: '10rem' }} className="margin_10px">
                    <Card.Header><Card.Title>Perfil group</Card.Title></Card.Header>
                    <ListGroup className="list-group-flush">
                        {detalhamento.quantPerfilGroup && (
                            detalhamento.quantPerfilGroup.map((item) => <ListGroup.Item><Card.Subtitle className="mb-2 text-muted">{item}</Card.Subtitle></ListGroup.Item>)
                        )}
                    </ListGroup>
                    <Card.Footer className="text-muted"></Card.Footer>
                </Card>

            </div>
        </div>
    )
}
export default Usuario