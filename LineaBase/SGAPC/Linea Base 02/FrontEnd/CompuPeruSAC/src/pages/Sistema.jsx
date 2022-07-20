import React, { useContext, useEffect, useState } from "react";
import { Link, Outlet } from "react-router-dom";
import {
  BoxLogo,
  BoxSistema,
  BtnMobile,
  ContenedorPages,
  ContenedorSistema,
  Paginas,
  SidebarStyle,
} from "../styles/SistemaStyle";
import Logo from "../assets/logoLogin.png";
import Header from "../components/Header";
import { Boton } from "../styles/HeaderStyle";
import Navegador from "../components/Navegador";
import { AuthContext } from "../context/auth/AuthProvider";

const Sistema = ({ logout }) => {
  const [activar, setActivar] = useState(false);
  const Sidebar = () => {
    setActivar(!activar);
  };
  const {usuario,usuarioAutenticado} = useContext(AuthContext);
  useEffect(() => {
    usuarioAutenticado();
  }, []);
  return (
    <>
    {usuario && (
      <ContenedorSistema>
      <BoxSistema>
        <SidebarStyle activar={activar}>
          <BoxLogo>
            <div>
              <img src={Logo} alt="logo" />
            </div>
            <BtnMobile>
              <Boton color="azul" menu="menu" onClick={Sidebar}>
                <span>
                  <i className="fas fa-bars"></i>
                </span>
              </Boton>
            </BtnMobile>
          </BoxLogo>
          <Navegador Sidebar={Sidebar} />
        </SidebarStyle>
        <Paginas activar={activar}>
          <Header Sidebar={Sidebar} logout={logout} />
          <ContenedorPages>
            <Outlet />
          </ContenedorPages>
        </Paginas>
      </BoxSistema>
    </ContenedorSistema>
    )}
    </>
  );
};

export default Sistema;
