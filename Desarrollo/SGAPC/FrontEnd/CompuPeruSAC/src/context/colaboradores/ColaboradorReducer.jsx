import { types } from "../../types";

export default (state, action) => {
    switch (action.type) {
        case types.REGISTRO_EXITOSO:
            return{
                ...state,
                errorForm:false,
                resetForm: true,
            }
        case types.REGISTRO_ERROR:
            return {
                ...state,
                errorFormulario: action.payload,
                errorForm:true,
                resetForm: false,

            }
        case types.RESETEAR_ERROR_REGISTRO:
            return {
                ...state,
                errorFormulario: [],
                errorForm: null,
                resetForm: false,

            }   
        case types.OBTENER_COLABORADORES:
            return {
                ...state,
                colaboradores: action.payload.docs,
                cargando: false,
                totalDatos: action.payload.totalDocs,
                totalPages: action.payload.totalPages
            } 
        case types.OBTENER_ERROR: 
            return{
                ...state,
                cargando: true,
                colaboradores: [],
                totalDatos: 0,
                totalPages: 0,
            }
        case types.COLABORADOR_ACTUAL:
            return {
                ...state,
                idColaborador: action.payload
            }
        case types.COLABORADOR_ACTUAL_RESET:{
            return {
                ...state,
                colaboradorSeleccionado: [],
                idColaborador: null,
                actualizarData: false,
            }
        }

        case types.RESET_LOADING:
            return {
                ...state,
                cargando: true,
            }
        case types.ACTUALIZAR_INPUT:
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
        case types.ACTUALIZAR_COLABORADOR:
            return {
                ...state,
                actualizarData: true,
                colaboradores: [],
                idColaborador: "",
                cargando: true,
                totalDatos: 0,
                totalPages: 0,
            }
        case types.ELIMINAR_COLABORADOR:
            return {
                ...state,
                actualizarData: true,
                colaboradores: [],
                idColaborador: "",
                cargando: true,
                totalDatos: 0,
                totalPages: 0,
            }
        default:
            return state;
    }
}