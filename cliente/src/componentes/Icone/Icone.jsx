import './Icone.css'
const Icone = (props) =>{
    const cor = props.cor !== undefined ? props.cor : "";
    const mensagem = props.mensagem !== undefined ? props.mensagem : ""; 
    return (
        <div>
            <span className={`material-symbols-outlined icone ${cor}`}> {props.icone} </span> {mensagem}
        </div>
    )
    
}
export default Icone