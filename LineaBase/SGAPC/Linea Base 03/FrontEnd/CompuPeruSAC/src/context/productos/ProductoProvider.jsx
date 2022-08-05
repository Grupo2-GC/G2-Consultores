import { createContext, useReducer } from "react";
import { clienteAxios } from "../../config/axios";
import { types } from "../../types";
import ProductoReducer from "./ProductoReducer";


export const ProductoContext = createContext();

export const ProductoProvider = ({children}) => {
    const initialState = {
        colaboradores_producto : [],
        idColaborador_producto: "",
        errorFormulario_producto: "",
        errorForm_producto: null,
        resetForm_producto: false,
        cargando_producto: true,
        totalDatos_producto: 0,
        totalPages_producto: 0,
        actualizarData_producto: false,
    }

    const [state, dispatch] = useReducer(ProductoReducer, initialState);

    const agregarProducto = async (colaborador) => {
        try {
            await clienteAxios.post('/productos',colaborador)
            
            dispatch({
                type: types.REGISTRO_EXITOSO_PRODUCTO,
            }) 
            
        } catch (error) {
            const errores = error.response.data.msg;
            console.log(error.response.data);
            dispatch({
                type: types.REGISTRO_ERROR_PRODUCTO,
                payload: errores
            })
        }
    }

    const obtenerProductos = async (page) => {
        try {
            const resultado = await clienteAxios.get(`/colaborador/listarColaboradores?limit=10&page=${page}`);
            // console.log(resultado.data);
            dispatch({
                type: types.OBTENER_COLABORADORES_PRODUCTO,
                payload: resultado.data
                })
            
        } catch (error) {
            console.log(error.response.data.msg);
            dispatch({
                type: types.OBTENER_ERROR_PRODUCTO,
                payload: error.response.data.msg
            })
        }
    }

    const productoActual = async (colaborador) => {
        try {
            // console.log(colaborador);
            dispatch({
                type: types.COLABORADOR_ACTUAL_PRODUCTO,
                payload: colaborador._id
            })
            
        } catch (error) {
            dispatch({
                type: types.OBTENER_ERROR_PRODUCTO,
                payload: error.response.data.msg
            })
        }
    }

    const actualizarP = async (colaborador) => {
        try {
            dispatch({
                type: types.ACTUALIZAR_INPUT_PRODUCTO,
                payload: colaborador
            })
            
        } catch (error) {
            dispatch({
                type: types.OBTENER_ERROR_PRODUCTO,
                payload: error.response.data.msg
            })
        }
    }
    

    const actualizarProducto = async (colaborador) => {
        try {
            await clienteAxios.put(`colaborador/listarColaborador/${colaborador._id}`,colaborador)
            // console.log(resultado.data);
            dispatch({
                type: types.ACTUALIZAR_COLABORADOR_PRODUCTO,
            })
        } catch (error) {
            dispatch({
                type: types.OBTENER_ERROR_PRODUCTO,
                payload: error.response.data.msg
            })
        }
    }
    
    const eliminarProducto = async (id) => {
        try {
            await clienteAxios.delete(`colaborador/listarColaborador/${id}`)
            // console.log(resultado.data);
            dispatch({
                type: types.ELIMINAR_COLABORADOR_PRODUCTO,
            })
        } catch (error) {
            dispatch({
                type: types.OBTENER_ERROR_PRODUCTO,
                payload: error.response.data.msg
            })
        }
    }

    const resetProductoActual = async () => {
        dispatch({
            type: types.COLABORADOR_ACTUAL_RESET_PRODUCTO
        })
    }

    const resetearErroresProducto = () => {
            dispatch({
                type: types.RESETEAR_ERROR_REGISTRO_PRODUCTO
            })
    }
    const resetLoadingProducto = () => {
        dispatch({
            type: types.RESET_LOADING_PRODUCTO
        })
    }


    return (
        <ProductoContext.Provider value={{
            ...state,
            agregarProducto,
            obtenerProductos,
            productoActual,
            actualizarProducto,
            actualizarP,
            eliminarProducto,
            resetProductoActual,
            resetearErroresProducto,
            resetLoadingProducto
            }} >
            {children}
        </ProductoContext.Provider>
        )
}