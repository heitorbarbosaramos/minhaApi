export class Endereco{
    id: Number;
    cep: String;
    logradouro: String;
    numero: String;
    complemento: String;
    bairro: String;
    localidade: String;
    uf: String;
    ibge: String;
    ddd: String;
    gia: String;

    setId(id: Number){
        this.id = id;
    }

    setCep(cep: String){
        this.cep = cep;
    }

    setLogradouro(logradouro: String){
        this.logradouro = logradouro;
    }

    setNumero(numero: String){
        this.numero = numero;
    }

    setComplemento(complemento: String){
        this.complemento = complemento;
    }

    setBairro(bairro: String){
        this.bairro = bairro;
    }

    setLocalidade(localidade: String){
        this.localidade = localidade;
    }

    setUf(uf: String){
        this.uf = uf;
    }

    setIbge(ibge: String){
        this.ibge = ibge;
    }

    setDDD(ddd: String){
        this.ddd = ddd;
    }

    setGia(gia: String){
        this.gia = gia;
    }
}