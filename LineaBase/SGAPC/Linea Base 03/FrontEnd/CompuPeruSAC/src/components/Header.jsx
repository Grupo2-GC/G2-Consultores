import React, { useContext, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom';
import { AuthContext } from '../context/auth/AuthProvider';
import {
    ContenedorHeader,
    ContenedorBotones,
    Boton,
    Box1,
    Tooltip,
    BoxIcon,
    UserStyle,
  } from "../styles/HeaderStyle";

const Header = ({Sidebar, logout }) => {
    const { cerrarSesion,usuario } = useContext(AuthContext);
    console.log(usuario);
    const {id} = useParams();
    const navigate = useNavigate();
    const Regresar = () => {
      navigate('/sistema/listarproductos');
  }
    const CerrarSesion = () => {
        cerrarSesion();
        logout();
      };
  return (
    <ContenedorHeader>
      <ContenedorBotones>
          <Boton color="azul" menu="menu" onClick={Sidebar}>
          <span>
            <i className="fas fa-bars"></i>
          </span>
        </Boton>
        <BoxIcon>
            <UserStyle>
              {usuario && (
                <span>
                {/* <i className="far fa-bell"></i> */}
                {usuario.usuario.nombre}
              </span>
              )}
              {/* <Tooltip>{`${usuarioName}, ${rolesUsuario}`}</Tooltip> */}
            </UserStyle>
            {id ? <Boton onClick={Regresar}>
            <span><i className="fas fa-arrow-left"></i></span> 
          </Boton>: null}
          <Box1>
            <Boton onClick={CerrarSesion}>
              <span>
                <i className="fas fa-sign-in-alt"></i>
              </span>
              <Tooltip>Cerrar sesíon</Tooltip>
            </Boton>
          </Box1>
        </BoxIcon>
      </ContenedorBotones>
    </ContenedorHeader>
  )
}

export default Header
