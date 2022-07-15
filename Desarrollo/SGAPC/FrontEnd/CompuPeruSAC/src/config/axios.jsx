import axios from 'axios'

export const clienteAxiosValhalla = axios.create({
    baseURL: 'https://sgapc-backend.herokuapp.com/api'
});
export const clienteAxios = axios.create({
    baseURL: 'http://localhost:8080/api'
});