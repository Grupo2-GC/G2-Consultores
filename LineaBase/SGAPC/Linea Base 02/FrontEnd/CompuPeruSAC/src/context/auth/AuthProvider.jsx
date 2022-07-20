import { createContext, useReducer } from "react";
import { clienteAxios, clienteAxiosValhalla } from "../../config/axios";
import { Authorization, tokenAuth} from "../../config/tokenAuth";
import { types } from "../../types";
import AuthReducer from "./AuthReducer";

export const AuthContext = createContext();

export const AuthProvider = ({children}) => {
    const initialState = {
        token: localStorage.getItem('token'),
        autenticado: null,
        usuario: null,
        mensaje: null,
        cargando: true,
        errorLogin: false,
        // usuarioName: localStorage.getItem('username'),
        // rolesUsuario: localStorage.getItem('roles')
    }

    const [state, dispatch] = useReducer(AuthReducer, initialState);

    const iniciarSesion = async (datos) => {

        try {   
            const respuesta = await clienteAxios.post('/auth/login',datos)
            // console.log(respuesta.data.authorities[0]); 
            dispatch({
                type: types.LOGIN_EXITOSO,
                payload: respuesta.data
            })
            // Obtener el usuario 
            usuarioAutenticado();
        } catch (error) {
            console.log(error.response)
            dispatch({
                type: types.LOGIN_ERROR,
                payload: error.response.data.errores[0].msg
            })
        }
    }

    const cerrarSesion =  () => {
        dispatch({
            type: types.CERRAR_SESION
        })
    }

    const ResetError = () => {
        setTimeout(()=> {
            dispatch({
                type: types.RESET__ERROR,
            })
        },5000)
    }

    const usuarioAutenticado = async () => {
        const token = localStorage.getItem('token');
        if (token) {
            //Funcion para enviar el token por header
            // Authorization(token)
            tokenAuth(token)
            // console.log(token);
        }

        try {
            const respuesta = await clienteAxios.get('/auth/login');
            console.log(respuesta.data);
            dispatch({
                type: types.OBTENER_USUARIO,
                payload: respuesta.data
            })
        } catch (error) {
            console.log(error.response);
            dispatch({
                type: types.LOGIN_ERROR
            })
        }
    }


    return (
        <AuthContext.Provider value={{
            ...state,
            iniciarSesion,
            cerrarSesion,
            ResetError,
            usuarioAutenticado
            }} >
            {children}
        </AuthContext.Provider>
        )
}