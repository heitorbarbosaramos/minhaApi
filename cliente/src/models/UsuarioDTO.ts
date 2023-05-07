import { Endereco } from "./Endereco";

export class UsuarioDTO{
    id: number;
    login: String;
    senha: String;
    nome: String;
    ativo: boolean;
    idsPerfis: [];
    fone: [];
    endereco: Endereco;

    setId(id: number){
        this.id = id;
    }

    setLogin(login: String){
        this.login = login;
    }

    setSenha(senha: String){
        this.senha = senha;
    }

    setNome(nome: String){
        this.nome = nome;
    }

    setAtivo(ativo: boolean){
        this.ativo = ativo;
    }

    setPerfis(idsPerfis: []){
        this.idsPerfis = idsPerfis;
    }

    setFone(fone: []){
        this.fone = fone;
    }

    setEndereco(endereco: Endereco){
        this.endereco = endereco;
    }
}