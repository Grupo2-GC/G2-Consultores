import { createContext, useReducer } from "react";
import { clienteAxios } from "../../config/axios";
import { types } from "../../types";
import ColaboradorReducer from "./ColaboradorReducer";


export const ColaboradorContext = createContext();

export const ColaboradorProvider = ({children}) => {
    const initialState = {
        colaboradores : [],
        idColaborador: "",
        errorFormulario: "",
        errorForm: null,
        resetForm: false,
        cargando: true,
        totalDatos: 0,
        totalPages: 0,
        actualizarData: false,
    }

    const [state, dispatch] = useReducer(ColaboradorReducer, initialState);

    const agregarColaborador = async (colaborador) => {
        try {
            await clienteAxios.post('/colaborador/registrar',colaborador)
            
            dispatch({
                type: types.REGISTRO_EXITOSO,
            }) 
            
        } catch (error) {
            const errores = error.response.data.msg;
            console.log(error.response.data);
            dispatch({
                type: types.REGISTRO_ERROR,
                payload: errores
            })
        }
    }

    const obtenerColaboradores = async (page) => {
        try {
            const resultado = await clienteAxios.get(`/colaborador/listarColaboradores?limit=10&page=${page}`);
            // console.log(resultado.data);
            dispatch({
                type: types.OBTENER_COLABORADORES,
                payload: resultado.data
                })
            
        } catch (error) {
            console.log(error.response.data.msg);
            dispatch({
                type: types.OBTENER_ERROR,
                payload: error.response.data.msg
            })
        }
    }

    const ColaboradorActual = async (colaborador) => {
        try {
            // console.log(colaborador);
            dispatch({
                type: types.COLABORADOR_ACTUAL,
                payload: colaborador._id
            })
            
        } catch (error) {
            dispatch({
                type: types.OBTENER_ERROR,
                payload: error.response.data.msg
            })
        }
    }

    const actualizar = async (colaborador) => {
        try {
            dispatch({
                type: types.ACTUALIZAR_INPUT,
                payload: colaborador
            })
            
        } catch (error) {
            dispatch({
                type: types.OBTENER_ERROR,
                payload: error.response.data.msg
            })
        }
    }
    

    const actualizarColaborador = async (colaborador) => {
        try {
            await clienteAxios.put(`colaborador/listarColaborador/${colaborador._id}`,colaborador)
            // console.log(resultado.data);
            dispatch({
                type: types.ACTUALIZAR_COLABORADOR,
            })
        } catch (error) {
            dispatch({
                type: types.OBTENER_ERROR,
                payload: error.response.data.msg
            })
        }
    }
    
    const eliminarColaborador = async (id) => {
        try {
            await clienteAxios.delete(`colaborador/listarColaborador/${id}`)
            // console.log(resultado.data);
            dispatch({
                type: types.ELIMINAR_COLABORADOR,
            })
        } catch (error) {
            dispatch({
                type: types.OBTENER_ERROR,
                payload: error.response.data.msg
            })
        }
    }

    const resetColaboradorActual = async () => {
        dispatch({
            type: types.COLABORADOR_ACTUAL_RESET
        })
    }

    const resetearErrores = () => {
            dispatch({
                type: types.RESETEAR_ERROR_REGISTRO
            })
    }
    const resetLoading = () => {
        dispatch({
            type: types.RESET_LOADING
        })
    }


    return (
        <ColaboradorContext.Provider value={{
            ...state,
            agregarColaborador,
            obtenerColaboradores,
            ColaboradorActual,
            actualizarColaborador,
            actualizar,
            eliminarColaborador,
            resetColaboradorActual,
            resetearErrores,
            resetLoading
            }} >
            {children}
        </ColaboradorContext.Provider>
        )
}