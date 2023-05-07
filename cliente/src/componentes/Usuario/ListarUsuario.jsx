/* eslint-disable react-hooks/exhaustive-deps */
import { useEffect, useState } from "react"
import ApiService from "../../Services/ApiService.ts"
import { Button, Form, InputGroup, Table } from "react-bootstrap";
import Icone from "../Icone/Icone.jsx";
import { UsuarioDTO } from "../../models/UsuarioDTO.ts";
import Paginador from "../TablePagination/TablePaginations.jsx";

const ListarUsuario = () => {

    const [dadosUsuario, setDadosUsuario] = useState();
    const [orderBy, setOrderBy] = useState("nome");
    const [direction, setDirection] = useState("ASC");
    const [offset, setOffset] = useState(0);
    const [linesPerPage, setLinesPerPage] = useState([]);

    const [usuarioDto] = useState(new UsuarioDTO(""));

    const [filtroId, setFiltroID] = useState("");
    const [filtroLogin, setFiltroLogin] = useState("");
    const [filtroNome, setFiltroNome] = useState("");
    const [filtroAtivo, setFiltroAtivo] = useState();


    useEffect(() => {

        usuarioDto.setId(filtroId);
        usuarioDto.setLogin(filtroLogin);
        usuarioDto.setNome(filtroNome);
        usuarioDto.setAtivo(filtroAtivo);

        console.log("USUARIO DTO ANTES: ", usuarioDto);

        ApiService.post(`/rest/usuario/ListaFiltro?page=${offset}&linesPerPage=${linesPerPage}&orderBy=${orderBy}&direction=${direction}`, usuarioDto).then(response => {
            
            setDadosUsuario(response.data);
            console.log("DADOS USUARIOS DEPOIS: ", dadosUsuario);
        }).catch(error => {
            console.error(error)
        })
    }, [setDadosUsuario, offset, linesPerPage, orderBy, direction, usuarioDto, filtroId, filtroLogin, filtroNome, filtroAtivo])

    
    console.log(usuarioDto);

    return (
        <div className="border">

            <Table striped size="sm">
                <thead>
                    <tr>
                        <th>
                            <span className="cursor_ponter" onClick={() => {
                                setOrderBy("login");
                                setDirection(direction === "ASC" ? "DESC" : "ASC")
                            }}>Login</span>
                            <InputGroup style={{ width: '20rem' }}>
                                <Form.Control
                                    onChange={(e) => setFiltroLogin(e.target.value)}
                                    placeholder="Login"
                                />
                            </InputGroup>
                        </th>
                        <th>
                            <span className="cursor_ponter" onClick={() => {
                                setOrderBy("nome");
                                setDirection(direction === "ASC" ? "DESC" : "ASC")
                            }}>Nome</span>
                            <InputGroup style={{ width: '20rem' }}>
                                <Form.Control
                                    onChange={(e) => setFiltroNome(e.target.value)}
                                    placeholder="Nome"
                                />
                            </InputGroup>
                        </th>
                        <th>
                            <span className="cursor_ponter" onClick={() => {
                                setOrderBy("ativo");
                                setDirection(direction === "ASC" ? "DESC" : "ASC")
                            }}>Ativo</span>
                            <Form.Select aria-label="Default select example" style={{ width: '8rem' }}
                                onChange={(e) => setFiltroAtivo(e.target.value)}
                            >
                                <option></option>
                                <option value="true">Ativo</option>
                                <option value="false">Desativado</option>
                            </Form.Select>
                        </th>
                        <th></th>
                    </tr>
                </thead>

                {dadosUsuario && (
                    <tbody>
                        {dadosUsuario.content.map((api) => {
                            return (
                                <tr key={api.id}>
                                    <td>{api.login}</td>
                                    <td>{api.nome}</td>
                                    <td>{api.ativo === true ? <Icone icone="done" cor="cor_certo" /> : <Icone icone="block" cor="cor_erro" />}</td>
                                    <td>
                                        <Button variant="primary" size="sm">
                                            <Icone icone="manage_accounts" />
                                        </Button>
                                    </td>
                                </tr>
                            )
                        })}
                    </tbody>
                )}

            </Table>
           
            {dadosUsuario && (
                <Paginador
                    totalPages={dadosUsuario.totalPages}
                    paginaAtual={dadosUsuario.number}
                    setOffset={setOffset}
                    setLinesPerPage={setLinesPerPage}
                />
            )}

        </div>
    )
}
export default ListarUsuario