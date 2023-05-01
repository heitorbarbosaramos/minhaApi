export class LoginDTO{
    login: String;
    senha: String;

    setLogin(login: String){
        this.login = login;
    }

    setSenha(senha: String){
        this.senha = senha;
    }
}