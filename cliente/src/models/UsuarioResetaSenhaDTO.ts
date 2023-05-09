export class UsuarioResetaSenhaDTO{
    id: Number;
    timestampRecuperaSenha: Number;
    nome: String;
    login: String;
    senha: String;

    setId(id: Number){
        this.id = id;
    }

    setTimestampRecuperaSenha( timestampRecuperaSenha: Number){
        this.timestampRecuperaSenha = timestampRecuperaSenha;
    }

    setNome(nome: String){
        this.nome = nome;
    }

    setLogin(login: String){
        this.login = login;
    }

    setSenha(senha: String){
        this.senha = senha;
    }
}