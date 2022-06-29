import axios from 'axios'

export const clienteAxiosValhalla = axios.create({
    baseURL: 'https://sgapc-backend.herokuapp.com'
});