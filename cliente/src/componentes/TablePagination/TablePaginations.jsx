import { Form } from 'react-bootstrap';
import Pagination from 'react-bootstrap/Pagination';

function Paginador({ totalPages, paginaAtual, setOffset, setLinesPerPage }) {

    console.log("TOTAL PAGE ", totalPages);
    
    const paginacao = [];
    const pages = totalPages;
    const nextPage = paginaAtual + 1;

    for (let index = 0; index < totalPages; index++) {
        paginacao[index] = index;
    }
    function onPageChange(page) {
        setOffset(page - 1);
    }

    function onLinesPerPageChange(lines) {
        setLinesPerPage(lines.target.value);
    }

    return (
        <div style={{ display: "flex" }}>
            <Form.Select className='mb-3' style={{width: "110px"}} onChange={(e) => onLinesPerPageChange(e)}>
                <option key={1} value={3}>3 Itens</option>
                <option key={2} value={5}>5 Itens</option>
                <option key={3} selected value={10}>10 Itens</option>
                <option key={4} value={15}>15 Itens</option>
                <option key={5} value={20}>20 Itens</option>

            </Form.Select>
            <Pagination>
                <Pagination.First onClick={() =>{ onPageChange(1)}}/>
                <Pagination.Prev onClick={() =>{ onPageChange(paginaAtual)}}/>
                
                {Array.from(paginacao).map((_,p) => (
                     <Pagination.Item active={p === paginaAtual ? true : false} onClick={() =>{ onPageChange(p+1)}}>{p+1}</Pagination.Item>
                ))}

                <Pagination.Next onClick={() =>{ onPageChange(nextPage+1)}}/>
                <Pagination.Last onClick={() =>{ onPageChange(pages)}} />
            </Pagination>
        </div>
    )
}

export default Paginador;