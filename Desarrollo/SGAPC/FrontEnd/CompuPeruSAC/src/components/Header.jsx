import React, { useContext, useState } from 'react'
import { AuthContext } from '../context/auth/AuthProvider';
import {
    ContenedorHeader,
    ContenedorBotones,
    Boton,
    Box1,
    Tooltip,
    BoxIcon,
  } from "../styles/HeaderStyle";

const Header = ({Sidebar, logout }) => {
    const { cerrarSesion,rutaReset,usuarioName,rolesUsuario } = useContext(AuthContext);
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
          <Box1>
            <Boton>
              <span>
                <i className="far fa-bell"></i>
              </span>
              <Tooltip>{`${usuarioName}, ${rolesUsuario}`}</Tooltip>
            </Boton>
          </Box1>
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
