import { types } from "../../types";

export default (state, action) => {

    switch (action.type) {
        case types.LOGIN_EXITOSO:
            localStorage.setItem('token',action.payload.token.tokenDeAcceso);
            localStorage.setItem('username',action.payload.username);
            localStorage.setItem('roles',action.payload.authorities[0].authority); 
            return{
                ...state,
                autenticado: true,
                token: action.payload.token.tokenDeAcceso,
                usuarioName: action.payload.username,
                rolesUsuario: action.payload.authorities[0].authority
            }
        case types.LOGIN_ERROR:
            localStorage.removeItem('token');
            return{
                ...state,
                token: null, 
                autenticado: false,
                mensaje: action.payload,
                errorLogin: true,
                usuario: null,
                usuarioName: "",
            }
        case types.CERRAR_SESION:
            localStorage.removeItem('token');
            localStorage.removeItem('roles');
            localStorage.removeItem('username');
            return{
                ...state,
                token: null, 
                autenticado: false,
                usuario: null,
                mensaje: null,
                usuarioName: "No registrado",
            }
        case types.RESET__ERROR:
            return {
                ...state,
                errorLogin: false,
                mensaje: null,
            }
        case types.OBTENER_USUARIO: 
            return {
                ...state,
                usuario: action.payload,
                autenticado: true,
            }
    
        default:
            return state;
    }
}