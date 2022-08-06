import { types } from "../../types";

export default (state, action) => {
    switch (action.type) {
        case types.REGISTRO_EXITOSO_PRODUCTO:
            return{
                ...state,
                errorForm_producto:false,
                resetForm_producto: true,
            }
        case types.REGISTRO_ERROR_PRODUCTO:
            return {
                ...state,
                errorFormulario_producto: action.payload,
                errorForm_producto:true,
                resetForm_producto: false,

            }
        case types.RESETEAR_ERROR_REGISTRO_PRODUCTO:
            return {
                ...state,
                errorFormulario_producto: [],
                errorForm_producto: null,
                resetForm_producto: false,

            }   
        case types.OBTENER_COLABORADORES_PRODUCTO:
            return {
                ...state,
                colaboradores_producto: action.payload.docs,
                cargando_producto: false,
                totalDatos_producto: action.payload.totalDocs,
                totalPages_producto: action.payload.totalPages
            } 
        case types.OBTENER_ERROR_PRODUCTO: 
            return{
                ...state,
                cargando_producto: true,
                colaboradores_producto: [],
                totalDatos_producto: 0,
                totalPages_producto: 0,
            }
        case types.COLABORADOR_ACTUAL_PRODUCTO:
            return {
                ...state,
                idColaborador_producto: action.payload
            }
        case types.COLABORADOR_ACTUAL_RESET_PRODUCTO:{
            return {
                ...state,
                colaboradorSeleccionado_producto: [],
                idColaborador_producto: null,
                actualizarData_producto: false,
            }
        }

        case types.RESET_LOADING_PRODUCTO:
            return {
                ...state,
                cargando_producto: true,
            }
        case types.ACTUALIZAR_INPUT_PRODUCTO:
            const actualiza = action.payload;
            const update = state.colaboradores.map(item => {
                if (item.id === actualiza.id) {
                    item.first_name === actualiza.first_name,
                    item.last_name === actualiza.last_name
                }
                return item;
            })
            return {
                ...state,
                item: update,
            }
        case types.ACTUALIZAR_COLABORADOR_PRODUCTO:
            return {
                ...state,
                actualizarData_producto: true,
                colaboradores_producto: [],
                idColaborador_producto: "",
                cargando_producto: true,
                totalDatos_producto: 0,
                totalPages_producto: 0,
            }
        case types.ELIMINAR_COLABORADOR_PRODUCTO:
            return {
                ...state,
                actualizarData_producto: true,
                colaboradores_producto: [],
                idColaborador_producto: "",
                cargando_producto: true,
                totalDatos_producto: 0,
                totalPages_producto: 0,
            }
        default:
            return state;
    }
}