import React, { useState } from "react";
import { Link } from "react-router-dom";
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
import Estritorio from "../private/Estritorio";
import Header from "../components/Header";
import { Boton } from "../styles/HeaderStyle";

const Sistema = ({ logout }) => {
  const [activar, setActivar] = useState(false);
  const Sidebar = () => {
    setActivar(!activar);
  };
  return (
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
        </SidebarStyle>
        <Paginas activar={activar}>
          <Header Sidebar={Sidebar} logout={logout} />
          <ContenedorPages>
            <Estritorio />
          </ContenedorPages>
        </Paginas>
      </BoxSistema>
    </ContenedorSistema>
  );
};

export default Sistema;
