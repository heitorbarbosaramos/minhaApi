import axios from "axios";


let baseURL: any;

switch(window.location.origin){
    case "http://localhost:3000":
        baseURL = process.env.REACT_APP_API_BASE_URL_LOCAL;
        break;
}

const Api = axios.create({
    baseURL
});

Api.interceptors.request.use(
    (config) =>{
        const token = localStorage.getItem("token_api");
        if(token && config.headers){
            config.headers.authorization = `Bearer ${token}`;
        }
        return config;
    },
    (erro) => Promise.reject(erro),
)

Api.interceptors.response.use(
    (response) => {
        return response;
    },
    (error) => {
        if (error.response.status === 401) {
            alert("Usuario não logado")
        }
        if (error.response.status === 403) {
            alert("Usuario não autorizado")
        }
        return Promise.reject(error);
    }

);

export default Api