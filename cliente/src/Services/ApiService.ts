import axios from "axios";

let baseURL: any;

switch (window.location.origin) {
  case "http://localhost:3000":
    baseURL = process.env.REACT_APP_API_BASE_URL_LOCAL;
    break;
}

const ApiService = axios.create({
  baseURL
});

ApiService.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("myApiToken")
    if (token && config.headers) {
      config.headers.authorization = `Bearer ${token}`;
    }
    return config;
  },
  (erro) => {
    Promise.reject(erro)
  },
)

export default ApiService