import axios from "axios";

let baseURL: any;

switch(window.location.origin){
    case "http://localhost:3000":
        baseURL = process.env.REACT_APP_API_BASE_URL_LOCAL;
        break;
}

const ApiService = axios.create({
    baseURL
});

export default ApiService