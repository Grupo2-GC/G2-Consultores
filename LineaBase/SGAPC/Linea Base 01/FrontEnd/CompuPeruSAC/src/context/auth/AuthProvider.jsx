import { createContext, useReducer } from "react";
import { clienteAxiosValhalla } from "../../config/axios";
import { Authorization} from "../../config/tokenAuth";
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
        usuarioName: localStorage.getItem('username'),
        rolesUsuario: localStorage.getItem('roles')
    }

    const [state, dispatch] = useReducer(AuthReducer, initialState);

    const iniciarSesion = async (datos) => {

        try {   
            const respuesta = await clienteAxiosValhalla.post('/auth/login',datos)
            console.log(respuesta.data.authorities[0].authority); 
            dispatch({
                type: types.LOGIN_EXITOSO,
                payload: respuesta.data
            })
            //Obtener el usuario 
            // usuarioAutenticado();
        } catch (error) {
            console.log(error.response.data.message)
            dispatch({
                type: types.LOGIN_ERROR,
                payload: error.response.data.message
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

    // const usuarioAutenticado = async () => {
    //     const token = localStorage.getItem('token');
    //     if (token) {
    //         //Funcion para enviar el token por header
    //         Authorization(token)
    //         // console.log(token);
    //     }

    //     try {
    //         const respuesta = await clienteAxiosValhalla.get('/account/api/detail');
    //         // console.log(respuesta);
    //         dispatch({
    //             type: types.OBTENER_USUARIO,
    //             payload: respuesta.data
    //         })
    //     } catch (error) {
    //         console.log(error.response);
    //         dispatch({
    //             type: types.LOGIN_ERROR
    //         })
    //     }
    // }


    return (
        <AuthContext.Provider value={{
            ...state,
            iniciarSesion,
            cerrarSesion,
            ResetError,
            // usuarioAutenticado
            }} >
            {children}
        </AuthContext.Provider>
        )
}