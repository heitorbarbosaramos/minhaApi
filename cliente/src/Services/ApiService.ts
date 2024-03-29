import axios from "axios";
import Cookies from 'js-cookie';

let baseURL: any;
baseURL = process.env.REACT_APP_API_BASE_URL;

const ApiService = axios.create({
  baseURL
});

ApiService.interceptors.request.use(
  (config) => {
    const token = Cookies.get("api-token")
    if (token && config.headers) {
      config.headers.authorization = `Bearer ${token}`;
    }

    return config;
  },
  (erro) => {
    Promise.reject(erro)
  },
)

ApiService.interceptors.response.use(
  (response) => {
 
    console.log(response);
    
    const JSESSIONID = Cookies.get("JSESSIONID");

    if (JSESSIONID) {
      response.headers.Authorization = `JSESSIONID ${JSESSIONID}`
    }

    return response;
  },
)

export default ApiService