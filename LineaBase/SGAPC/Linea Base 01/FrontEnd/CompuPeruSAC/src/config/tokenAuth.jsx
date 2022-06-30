import { clienteAxiosValhalla } from "./axios";

export const Authorization = token => {
    if (token) {
        clienteAxiosValhalla.defaults.headers.common['Authorization'] =`token ${token}`;
        // console.log(clienteAxiosValhalla.defaults.headers.common['Authorization']);
    }
    else{
        delete clienteAxios.defaults.headers.common['Authorization']
    }
}